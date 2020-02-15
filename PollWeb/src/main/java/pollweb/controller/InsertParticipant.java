package pollweb.controller;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import framework.data.DataException;
import framework.data.dao.PollDataLayer;
import framework.result.FailureResult;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import framework.security.SecurityLayer;
import pollweb.data.model.Partecipant;
import pollweb.data.model.Poll;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.util.*;

public class InsertParticipant extends PollBaseController {


    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("page_title", "expection");

        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        request.setAttribute("page_title", "Create Poll");
        int poll_key;
        int idU;
        try {

            if (SecurityLayer.isValid(request)) {
                if (request.getParameter("k") != null) {
                    poll_key = SecurityLayer.checkNumeric(request.getParameter("k"));
                    if ((poll_key != 0)) {
                        idU = (((PollDataLayer) request.getAttribute("datalayer")).getPollDAO().getPollById(poll_key).getRespUser().getKey());
                        if ((idU != Integer.parseInt(request.getSession().getAttribute("userid").toString()))) {
                            request.setAttribute("message", "it is not your poll");
                            action_error(request, response);
                        }














                        if (request.getParameter("addParticipants") != null) {
                            action_updateParticipants(request, response, poll_key, idU);
                        } else if (request.getParameter("deleteParticipant") != null) {
                            action_deleteParticipant(request, response, poll_key, idU);
                        }
                        else if (request.getParameter("insert")!=null) {
                            action_insertCsvParticipant(request, response, poll_key, idU);
                        }
                           else {
                               action_participants(request, response, poll_key, idU);
                        }


                    }
                }

            } else {
                response.sendRedirect("Login");
            }
        } catch (DataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateManagerException e) {
            e.printStackTrace();
        }


    }

        private void action_participants(HttpServletRequest request, HttpServletResponse response, int poll_key, int idU) throws TemplateManagerException {
        try {
            ServletContext sc = getServletContext();
            Poll poll = ((PollDataLayer) request.getAttribute("datalayer")).getPollDAO().getPollById(poll_key);

            //   sc.log(String.valueOf(poll_key));
            //  sc.log(String.valueOf(((PollDataLayer) request.getAttribute("datalayer")).getPartecipantDAO().getPartecipantsByPollId(poll_key).size()));
            request.setAttribute("poll", poll);
            request.setAttribute("pollParticipants", ((PollDataLayer) request.getAttribute("datalayer")).getPartecipantDAO().getPartecipantsByPollId(poll_key));
            TemplateResult rs = new TemplateResult(getServletContext());
            rs.activate("pollParticipants.ftl.html", request, response);
        } catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }


    private void action_updateParticipants(HttpServletRequest request, HttpServletResponse response, int poll_key, int idU) throws IOException, ServletException, TemplateManagerException, DataException {
        try {
            Partecipant p;
            Poll poll =  ((PollDataLayer) request.getAttribute("datalayer")).getPollDAO().getPollById(poll_key);

            ArrayList<String> e = new ArrayList<String>(Arrays.asList(request.getParameterValues("email")));
            ArrayList<String> e1 = new ArrayList<String>(Arrays.asList(request.getParameterValues("password")));
            ArrayList<String> e2 = new ArrayList<String>(Arrays.asList(request.getParameterValues("name")));
            Iterator<String> iterator1 = e.iterator();
            Iterator<String> iterator2 = e1.iterator();
            Iterator<String> iterator3 = e2.iterator();
            String em, pa, na;
            while (iterator1.hasNext() && iterator2.hasNext() && iterator3.hasNext()) {
                em = iterator1.next();
                pa = iterator2.next();
                na = iterator3.next();
                if ((!em.matches("[ ]+") && !em.isEmpty()) && (!pa.matches("[ ]+") && !pa.isEmpty()) && (!na.matches("[ ]+") && !na.isEmpty())) {
                    p = ((PollDataLayer) request.getAttribute("datalayer")).getPartecipantDAO().createPartecipant();
                    p.setEmail(em);
                    p.setPwd(pa);
                    p.setNameP(na);
                    ((PollDataLayer) request.getAttribute("datalayer")).getPartecipantDAO().storePartecipant(p, poll_key);
                    if (SendEmail(poll, p)){ request.setAttribute("emailSended", true); } else request.setAttribute("emailSended", false);
                } else {
                    request.setAttribute("message", "you have to insert at least one user");
                    action_error(request, response);
                }
            }


            action_participants(request, response, poll_key, idU);
        } catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }


    private void action_deleteParticipant(HttpServletRequest request, HttpServletResponse response, int pollKey, int idU) {
        try {
            String[] delete = request.getParameterValues("delete");
            Partecipant p;
            for (String d : delete) {
                p = ((PollDataLayer) request.getAttribute("datalayer")).getPartecipantDAO().getUserById(Integer.parseInt(d));

                if (!(((PollDataLayer) request.getAttribute("datalayer")).getPartecipantDAO().deleteParticipantToPoll(p, pollKey))) {
                    request.setAttribute("message", "error, not present in poll");
                    action_error(request, response);
                }
            }
            action_participants(request, response, pollKey, idU);
        } catch (DataException | TemplateManagerException ex) {
            request.setAttribute("message", ex);
            action_error(request, response);
        }


    }

    private void action_insertCsvParticipant(HttpServletRequest request, HttpServletResponse response, int poll_key, int idU) throws IOException, ServletException, DataException {
        ServletContext sc = getServletContext();
        Poll poll = ((PollDataLayer)request.getAttribute("datalayer")).getPollDAO().getPollById(poll_key);
        //if the request is empty or is not multipart encoded, calling getPart() would rease a ServletException!
        try {
            //if the request is empty or is not multipart encoded, calling getPart() would rease a ServletException!
            if (request.getContentType() != null && request.getContentType().startsWith("multipart/form-data")) {

                //we could also get the other form fields as parts. However, getParameter works in this case, and it is easier to use!
                Part p = request.getPart("f1");
                //getPart returns null if the part does not exist
                if (p != null) {

                    String line = "";
                    String splitBy = ";";
                    try
                    {
                        InputStreamReader isReader = new InputStreamReader(p.getInputStream());
                        BufferedReader br = new BufferedReader(isReader);
                        while ((line = br.readLine()) != null)   //returns a Boolean value
                        {
                            String[] participant = line.split(splitBy);    // use comma as separator

                            if (participant[0] != null &&  !participant[0].isEmpty() && participant[1] != null && !participant[1].isEmpty() &&  participant[1].contains("@") && participant[2] != null &&  !participant[2].isEmpty()){
                              Partecipant  pa = ((PollDataLayer) request.getAttribute("datalayer")).getPartecipantDAO().createPartecipant();
                             pa.setNameP(participant[0]);

                              pa.setEmail(participant[1]);
                              pa.setPwd(participant[2]);
                                ((PollDataLayer) request.getAttribute("datalayer")).getPartecipantDAO().storePartecipant(pa, poll_key );
                               if (SendEmail(poll, pa)){ request.setAttribute("emailSended", true); } else request.setAttribute("emailSended", false);

                            }


                            sc.log("parrticipant [First Name=" + participant[0] + " email="+participant[1]+ ", password=" + participant[1] + "]");
                        }
                        action_participants(request, response, poll_key, idU);
                    }
                    catch (IOException e)
                    {
                       request.setAttribute("message", e);
                       action_error(request, response);
                    } catch (DataException e) {
                        e.printStackTrace();
                    } catch (TemplateManagerException e) {
                        e.printStackTrace();
                    }
                }
                }



        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }


   public boolean SendEmail(Poll poll, Partecipant p) throws ServletException {
        String body = p.getNameP()+
                " sei stato aggiunto in un nuovo sondaggio riservato. L'url per accedere è: "+
                poll.getUrl()+
                " con la mail: "+
                p.getEmail()+ " e password: "+
                p.getPwd();

       return SecurityLayer.sendEmail(getServletContext().getInitParameter("user"), getServletContext().getInitParameter("pass"), p.getEmail(), "Sei stato aggiunto in un poll riservato", body);

    }
}



