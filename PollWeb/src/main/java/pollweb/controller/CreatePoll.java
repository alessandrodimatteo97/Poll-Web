/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pollweb.controller;

import framework.data.DataException;
import framework.data.dao.PollDataLayer;
import pollweb.data.model.Poll;
import framework.result.FailureResult;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import framework.security.SecurityLayer;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pollweb.data.model.ResponsibleUser;
/**     
 *
 * @author achissimo
 */
public class CreatePoll extends PollBaseController {

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
              request.setAttribute("page_title", "expection");

        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_update(HttpServletRequest request, HttpServletResponse response, int poll_key) throws TemplateManagerException {
        try {
            Poll poll;
            if (poll_key > 0) {
                poll = ((PollDataLayer)request.getAttribute("datalayer")).getPollDAO().getPollById(poll_key);

            } else {
                poll = ((PollDataLayer)request.getAttribute("datalayer")).getPollDAO().createPoll();
            }
            if (poll != null ){
                ResponsibleUser ru = ((PollDataLayer)request.getAttribute("datalayer")).getResponsibleUserDAO().getResponsibleUser(Integer.parseInt(request.getSession().getAttribute("userid").toString()));
                // da modificare bene
                 ServletContext context = getServletContext( );
                 context.log(ru.getNameR());
                if (ru != null && request.getParameter("title")!=null && !request.getParameter("title").isEmpty() &&  request.getParameter("closerText")!=null && !request.getParameter("closerText").isEmpty() && request.getParameter("url")!=null && !request.getParameter("url").isEmpty() && request.getParameter("type")!=null && !request.getParameter("type").isEmpty()) {
                    poll.setTitle(request.getParameter("title"));
                    poll.setRespUser(ru);
                    poll.setApertureText(request.getParameter("apertureText"));
                    poll.setCloserText(request.getParameter("closerText"));
                    poll.setUrl("localhost:8080/PollWeb/"+request.getParameter("url"));
                    poll.setType(request.getParameter("type"));
                    
                    ((PollDataLayer)request.getAttribute("datalayer")).getPollDAO().storePoll(poll);
                action_write(request, response, poll.getKey());
                } else {
                    request.setAttribute("message", "Cannot update poll: insufficient parameters ");
                    action_error(request, response);
                }
            } else {
                request.setAttribute("message", "Cannot update poll: undefined");
                action_error(request, response);

            }
        } catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }

    public void action_write(HttpServletRequest request, HttpServletResponse response, int poll_key) throws TemplateManagerException{
        TemplateResult res = new TemplateResult(getServletContext());

        try{
            
        
        if(poll_key>0){
           
          Poll poll = ((PollDataLayer)request.getAttribute("datalayer")).getPollDAO().getPollById(poll_key);
          poll.setUrl(poll.getUrl().replaceAll("localhost:8080/PollWeb/", ""));
          

         if(poll!= null){
              request.setAttribute("poll", poll);
              int numberQuestion = ((PollDataLayer) request.getAttribute("datalayer")).getQuestionDAO().getQuestionNumber(poll_key);
              request.setAttribute("numberQuestion", numberQuestion);
              res.activate("createPoll.ftl.html", request, response);
              
          }
          
          else{
            request.setAttribute("message", "Undefined poll");

              this.action_error(request, response);
          }
        
         }
        else {
            Poll poll = ((PollDataLayer) request.getAttribute("datalayer")).getPollDAO().createPoll();
            request.setAttribute("poll", poll);
            res.activate("createPoll.ftl.html", request, response);
        }
        }
        catch(DataException ex){
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
             action_error(request, response);
        }
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
     request.setAttribute("page_title", "Create Poll");
       int poll_key;

        try {

                if(SecurityLayer.isValid(request)) {
                    if (request.getParameter("k") != null) {
                        poll_key = SecurityLayer.checkNumeric(request.getParameter("k"));
                            if (poll_key!=0){
                                action_checkPoll(request, response, poll_key);
                            }

                                    if (request.getParameter("update") != null) {
                                    action_update(request, response, poll_key);
                                         }
                                    else {
                                        action_write(request, response, poll_key);
                                    }



                    }
                    else {
                        request.setAttribute("message", "there is no poll");
                    }
                }
                else {
            response.sendRedirect("Login");
                }
            } catch (NumberFormatException ex) {
            request.setAttribute("message", "invalid number");
            action_error(request, response);
        } catch (TemplateManagerException | IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }

    private void action_checkPoll(HttpServletRequest request, HttpServletResponse response, int poll_key) {
        int idU;
        try {
            idU = (((PollDataLayer) request.getAttribute("datalayer")).getPollDAO().getPollById(poll_key).getRespUser().getKey());
            if ((idU != Integer.parseInt(request.getSession().getAttribute("userid").toString()))) {
                request.setAttribute("message", "it is not your poll asshole");
                action_error(request, response);
            }
        }
        catch (DataException e){
            request.setAttribute("message", "it is not your poll, asshole");
            action_error(request, response);
        }
    }

}