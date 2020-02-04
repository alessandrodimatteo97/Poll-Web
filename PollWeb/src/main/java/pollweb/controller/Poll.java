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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    private void action_default(HttpServletRequest request, HttpServletResponse response ,int n) throws IOException, ServletException, TemplateManagerException, DataException {
            try{TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("page_title", "Poll name");
           String type = ((PollDataLayer) request.getAttribute("datalayer")).getPollDAO().getPollById(n).getType();
          // if(type == "open"){
            request.setAttribute("questions", ((PollDataLayer)request.getAttribute("datalayer")).getQuestionDAO().getQuestionsByPollId(n));
            res.activate("poll.ftl.html", request, response);
          /* }else{
               res.activate("login.ftl.html",request,response);
           }*/
            }  catch (DataException ex) {
           Logger.getLogger(Poll.class.getName()).log(Level.SEVERE, null, ex);
       }
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
            int n=0;
        try {
            n = SecurityLayer.checkNumeric(request.getParameter("n"));
            action_default(request, response ,n);
            request.setAttribute("ok", false);
             if(request.getParameter("confirm")!= null){
                this.action_answer(request, response);
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

        private void action_answer(HttpServletRequest request, HttpServletResponse response){
      
   }
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Main Newspaper servlet";
    }// </editor-fold>
}
