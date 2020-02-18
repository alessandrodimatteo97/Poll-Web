/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pollweb.controller;

import framework.data.DataException;
import framework.data.dao.PollDataLayer;
import framework.result.FailureResult;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import framework.security.SecurityLayer;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.inject.New;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pollweb.data.model.Partecipant;
import pollweb.data.model.ResponsibleUser;
import pollweb.data.model.Poll;
import pollweb.data.model.ResponsibleUser;

/**
 *
 * @author davide
 */
public class AdminController extends PollBaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (request.getAttribute("exception").equals("invalid_session")) {
                request.setAttribute("login_error", "Session errore");
                response.sendRedirect("Login");
            } else {
                (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
            }
        } catch (IOException ex) {
            request.setAttribute("exception", ex);
        }
    }
  
   
    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        try{
        TemplateResult res = new TemplateResult(getServletContext());

        request.setAttribute("page_title", "Admin");

        int userKey = ((PollDataLayer)request.getAttribute("datalayer")).getResponsibleUserDAO().getResponsibleUser((String)request.getSession(false).getAttribute("token")).getKey();

        ResponsibleUser user_logged = ((PollDataLayer)request.getAttribute("datalayer")).getResponsibleUserDAO().getResponsibleUser(userKey);

        boolean is_admin = ((PollDataLayer)request.getAttribute("datalayer")).getResponsibleUserDAO().checkAdmin(user_logged);
        request.setAttribute("is_admin", is_admin);

        if(((PollDataLayer)request.getAttribute("datalayer")).getResponsibleUserDAO().checkAdmin(user_logged)){
            request.setAttribute("users", ((PollDataLayer)request.getAttribute("datalayer")).getResponsibleUserDAO().getResponsibleUsersNotAccepted());
        }

        //request.setAttribute("polls", ((PollDataLayer)request.getAttribute("datalayer")).getPollDAO().getPollsByUserId(userKey));

        //request.setAttribute("closed_polls", ((PollDataLayer)request.getAttribute("datalayer")).getPollDAO().getPollsAlreadyActivatedAndClosedByUserId(userKey));
        res.activate("adminPanel.ftl.html", request, response);

        } catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        } 
    }
    
    private void action_update(HttpServletRequest request, HttpServletResponse response) throws  ServletException, TemplateManagerException {
        TemplateResult res = new TemplateResult(getServletContext());
        request.setAttribute("page_title", "Admin");
        String [] array = request.getParameterValues("checkbox");

        //request.setAttribute("results", array);
        try {
            for (String s : array) {

                ResponsibleUser ru = ((PollDataLayer) request.getAttribute("datalayer")).getResponsibleUserDAO().getResponsibleUser(SecurityLayer.checkNumeric(s));

                request.setAttribute("userSelected", s);
                
                ((PollDataLayer) request.getAttribute("datalayer")).getResponsibleUserDAO().setAccepted(SecurityLayer.checkNumeric(s));
                
                SendEmail(ru);

            }
            
        action_default(request, response);


        } catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        } catch (IOException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        } 

        
    
    }
    private void action_deactivate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("page_title", "Admin");

            ResponsibleUser rs = ((PollDataLayer)request.getAttribute("datalayer")).getResponsibleUserDAO().getResponsibleUser((String)request.getSession(false).getAttribute("token"));

            String id_poll = request.getParameter("poll_id");

            Poll pollRs = ((PollDataLayer) request.getAttribute("datalayer")).getPollDAO().getPollById(SecurityLayer.checkNumeric(id_poll));
            ((PollDataLayer) request.getAttribute("datalayer")).getPollDAO().setDeactivated(pollRs);
            request.setAttribute("polls", ((PollDataLayer)request.getAttribute("datalayer")).getPollDAO().getPollsByUserId(rs.getKey()));
            // request.setAttribute("closed_polls", ((PollDataLayer)request.getAttribute("datalayer")).getPollDAO().getPollsAlreadyActivatedAndClosedByUserId(rs.getKey()));
            res.activate("mypolls_adminpanel.ftl.html", request, response, false);
        } catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }

    private void action_activate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            
            request.setAttribute("page_title", "Admin");

            ResponsibleUser rs = ((PollDataLayer)request.getAttribute("datalayer")).getResponsibleUserDAO().getResponsibleUser((String)request.getSession(false).getAttribute("token"));
            
            String id_poll = request.getParameter("poll_id");
            
            Poll pollRs = ((PollDataLayer) request.getAttribute("datalayer")).getPollDAO().getPollById(SecurityLayer.checkNumeric(id_poll));
            


            ((PollDataLayer) request.getAttribute("datalayer")).getPollDAO().setActivated(pollRs);
            
            List<Partecipant> participants = new ArrayList<Partecipant>();

            participants.addAll(((PollDataLayer) request.getAttribute("datalayer")).getPartecipantDAO().getPartecipantsByPollId(SecurityLayer.checkNumeric(id_poll)));

            for (Partecipant p : participants) {
                SendEmailParticipant(p, pollRs.getUrl());
            }

            request.setAttribute("polls", ((PollDataLayer)request.getAttribute("datalayer")).getPollDAO().getPollsByUserId(rs.getKey()));

            // request.setAttribute("closed_polls", ((PollDataLayer)request.getAttribute("datalayer")).getPollDAO().getPollsAlreadyActivatedAndClosedByUserId(rs.getKey()));

            res.activate("mypolls_adminpanel.ftl.html", request, response, false);
            
            

        } catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }
    
    public void SendEmail(ResponsibleUser ru) throws ServletException {
        String body = ru.getNameR()+
                " ,congratulazioni sei diventato un responsabile.";

        SecurityLayer.sendEmail(getServletContext().getInitParameter("user"), getServletContext().getInitParameter("pass"), ru.getEmail(), "Sei diventato un responsabile", body);

    }
    
    public void SendEmailParticipant(Partecipant p, String url) throws ServletException {
        String body = p.getNameP() +
                " il sondaggio a cui sei stato invitato è stato attivato, clicca qui per accedere: " + url;

        SecurityLayer.sendEmail(getServletContext().getInitParameter("user"), getServletContext().getInitParameter("pass"), p.getEmail(), "Sondaggio attivato", body);

    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
       
        try {
            
            if(request.getParameter("to_do") != null) {
                if(request.getParameter("to_do").equals("activate")) {
                    action_activate(request, response);
                }
                if (request.getParameter("to_do").equals("deactivate"))
                {
                    action_deactivate(request,response);
                }
            } else {

                if (request.getParameterValues("checkbox") != null) {
                    action_update(request, response);
                } else {
                    if (request.getSession(false) == null) {
                        request.setAttribute("exception", "invalid_session");
                        action_error(request, response); //CASO SESSIONE NON VALIDA
                    } 
                    else if(request.getParameter("sec") != null) {
                        switch(request.getParameter("sec")) 
                        {   
                            case "mine":
                                action_my_polls(request, response);
                                break;

                            case "closed":
                                action_my_closed_polls(request, response);
                                break;

                            case "info":
                                action_information(request, response);
                                break;

                            default: action_default(request, response);
                        }
                    } else {
                        action_default(request, response);
                    }
                }
            }
                
            } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);

            } catch (TemplateManagerException ex) {
                request.setAttribute("exception", ex);
                action_error(request, response);

            } catch (DataException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);            
            }
    }

    private void action_my_polls(HttpServletRequest request, HttpServletResponse response) {
        try{
        TemplateResult res = new TemplateResult(getServletContext());
        
        request.setAttribute("page_title", "Admin");
        if(request.getSession().getAttribute("which_poll") != null || request.getSession().getAttribute("username") == null) {
            request.setAttribute("exception", "invalid_session");
            action_error(request, response);
        } else {
        int userKey = ((PollDataLayer)request.getAttribute("datalayer")).getResponsibleUserDAO().getResponsibleUser((String)request.getSession(false).getAttribute("token")).getKey();

        ResponsibleUser user_logged = ((PollDataLayer)request.getAttribute("datalayer")).getResponsibleUserDAO().getResponsibleUser(userKey);

        boolean is_admin = ((PollDataLayer)request.getAttribute("datalayer")).getResponsibleUserDAO().checkAdmin(user_logged);
        request.setAttribute("is_admin", is_admin);



        request.setAttribute("polls", ((PollDataLayer)request.getAttribute("datalayer")).getPollDAO().getPollsByUserId(userKey));
        
        res.activate("adminPanel.ftl.html", request, response);

        
        }} catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        } catch (TemplateManagerException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);} 
}

    private void action_my_closed_polls(HttpServletRequest request, HttpServletResponse response) {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            
            request.setAttribute("page_title", "Admin");

            ResponsibleUser rs = ((PollDataLayer)request.getAttribute("datalayer")).getResponsibleUserDAO().getResponsibleUser((String)request.getSession(false).getAttribute("token"));
            int userKey = ((PollDataLayer)request.getAttribute("datalayer")).getResponsibleUserDAO().getResponsibleUser((String)request.getSession(false).getAttribute("token")).getKey();


            boolean is_admin = ((PollDataLayer)request.getAttribute("datalayer")).getResponsibleUserDAO().checkAdmin(rs);
            request.setAttribute("is_admin", is_admin);
            request.setAttribute("closed_polls", ((PollDataLayer)request.getAttribute("datalayer")).getPollDAO().getPollsAlreadyActivatedAndClosedByUserId(rs.getKey()));

            res.activate("adminPanel.ftl.html", request, response);

        } catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        } catch (TemplateManagerException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void action_information(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
