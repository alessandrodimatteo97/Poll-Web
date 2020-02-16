package pollweb.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import framework.data.DataException;
import framework.data.dao.PollDataLayer;
import framework.data.proxy.QuestionProxy;
import framework.result.FailureResult;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import framework.security.SecurityLayer;
import static framework.security.SecurityLayer.checkNumeric;
import freemarker.template.utility.NumberUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pollweb.data.model.Question;

/**
 *
 * @author achissimo
 */
public class Poll extends PollBaseController {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
   private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_open_poll(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException, DataException {
        Object poll = request.getSession(false).getAttribute("which_poll");
        int poll_id = SecurityLayer.checkNumeric(poll.toString());
        try {
            TemplateResult res = new TemplateResult((getServletContext()));
            request.getSession(false).getAttribute("which_poll");
            request.setAttribute("page_title", "Poll Reserved Name");
            request.setAttribute("poll" ,((PollDataLayer)request.getAttribute("datalayer")).getPollDAO().getPollById(poll_id));
            request.setAttribute("questions", ((PollDataLayer)request.getAttribute("datalayer")).getQuestionDAO().getQuestionsByPollId(poll_id));
            res.activate("poll.ftl.html", request, response);
        } catch (DataException ex) {
            Logger.getLogger(Poll.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
        private void action_default(HttpServletRequest request, HttpServletResponse response ,int n) throws IOException, ServletException, TemplateManagerException, DataException {
            try{
                TemplateResult res = new TemplateResult(getServletContext());
            ServletContext sc = getServletContext();
            request.setAttribute("page_title", "Poll name");
            pollweb.data.model.Poll p = ((PollDataLayer) request.getAttribute("datalayer")).getPollDAO().getPollById(n);
            sc.log(String.valueOf(p.isActivated()));
            if(p.isActivated()){

           String type = ((PollDataLayer) request.getAttribute("datalayer")).getPollDAO().getPollById(n).getType();

           request.setAttribute("poll" ,((PollDataLayer)request.getAttribute("datalayer")).getPollDAO().getPollById(n));


          if(type.matches("open")){
            request.setAttribute("questions", ((PollDataLayer)request.getAttribute("datalayer")).getQuestionDAO().getQuestionsByPollId(n));
            res.activate("poll.ftl.html", request, response);
          }else{
              request.setAttribute("poll_id", n);

              res.activate("login_poll.ftl.html", request, response);

           }}//res.activate("error.ftl.html", request, response);
            }catch (DataException ex) {
           Logger.getLogger(Poll.class.getName()).log(Level.SEVERE, null, ex);
       }
    }



        private void action_answer(HttpServletRequest request, HttpServletResponse response, int n) throws TemplateManagerException {
            try{
                TemplateResult res = new TemplateResult(getServletContext());
                 request.setAttribute("page_title", "Confirm Page");
              ServletContext sc = getServletContext();
               List<Question> question = ((PollDataLayer)request.getAttribute("datalayer")).getQuestionDAO().getQuestionsByPollId(n);



          for(Question q : question){

                 ArrayList<String> answer = new ArrayList<>();
                 if(!q.getTypeP().equalsIgnoreCase( "multiple choice")){

               /*if(!q.getTypeP().equalsIgnoreCase("numeric")&&!q.getTypeP().equalsIgnoreCase("single choice")){
                   answer.add(request.getParameter(Integer.toString(q.getKey())));
               }else if (q.getTypeP().equalsIgnoreCase("numeric")){
                   String str = request.getParameter(Integer.toString(q.getKey()));
                    if(NumberUtils.isDigits(str)){
                        answer.add(request.getParameter(Integer.toString(q.getKey())));
                    }//res.activate("error.ftl.html", request, response);
               }else if (q.getTypeP().equalsIgnoreCase("single choice")){
              JSONObject json = q.getPossibleAnswer();
              String str = json.toString();
                  sc.log(str);

            }*/




                answer.add(request.getParameter(Integer.toString(q.getKey())));

              }else{
                  String[] a = request.getParameterValues(Integer.toString(q.getKey()));


                  for (String s : a){
                      sc.log(s  + q.getKey());
                      answer.add(s);



                  }

              }

               q.setAnswer(answer);
          }
            request.setAttribute("questions",question);
            request.setAttribute("poll" ,((PollDataLayer)request.getAttribute("datalayer")).getPollDAO().getPollById(n));
             res.activate("confirmPoll.ftl.html", request, response);
            }catch(DataException ex){
                request.setAttribute("message", ex);
                action_error(request, response);
            }

   }


    private void action_confirm(HttpServletRequest request, HttpServletResponse response, int n) throws DataException {
       List<Question> question = ((PollDataLayer)request.getAttribute("datalayer")).getQuestionDAO().getQuestionsByPollId(n);

    }
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */


    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
            int n=0;

        try {
            if(request.getParameterMap().containsKey("n")) {
            n = SecurityLayer.checkNumeric(request.getParameter("n"));
             if(request.getParameter("showResume")!= null){
                 action_answer(request, response,n);
             }else if (request.getParameter("confirm")!= null) {
                 action_confirm(request, response, n);
             }
                action_default(request, response, n);
            }else {
                action_open_poll(request, response);
            }
        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);

        } catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);

        } catch (DataException ex) {
           Logger.getLogger(Poll.class.getName()).log(Level.SEVERE, null, ex);
       }

    }

    @Override
    public String getServletInfo() {
        return "Main Newspaper servlet";
    }// </editor-fold>

}
