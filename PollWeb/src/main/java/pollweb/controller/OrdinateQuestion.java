package pollweb.controller;

import framework.data.DataException;
import framework.data.dao.PollDataLayer;
import framework.result.FailureResult;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import framework.security.SecurityLayer;
import pollweb.data.model.Poll;
import pollweb.data.model.Question;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class OrdinateQuestion extends PollBaseController {


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
        request.setAttribute("page_title", "Ordinate Question");

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
                    action_error(request, response);
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

    private void action_update(HttpServletRequest request, HttpServletResponse response, int poll_key) {
       try {
           //TemplateResult result = new TemplateResult(getServletContext());
           Poll poll = ((PollDataLayer) request.getAttribute("datalayer")).getPollDAO().getPollById(poll_key);
           Question question;
          // request.setAttribute("poll", poll);

          // request.setAttribute("questions", ((PollDataLayer) request.getAttribute("datalayer")).getQuestionDAO().getQuestionsByPollId(poll_key));
        String[] q = request.getParameterValues("question");

           for (int i=0; i< q.length ; i++){
             question = ((PollDataLayer) request.getAttribute("datalayer")).getQuestionDAO().getQuestionById(SecurityLayer.checkNumeric(q[i]));
             question.setNumber(i+1);
              ((PollDataLayer) request.getAttribute("datalayer")).getQuestionDAO().store(question);
           }
        //   result.activate();
          // action_write(request, response, poll_key);
           response.sendRedirect("Poll_Detail?k="+poll.getKey());
        }
       catch (Exception ex){
           request.setAttribute("message", ex);
           action_error(request, response);
       }
    }

    private void action_write(HttpServletRequest request, HttpServletResponse response, int poll_key) throws TemplateManagerException {
        try {


            TemplateResult res = new TemplateResult(getServletContext());
            Poll poll = ((PollDataLayer) request.getAttribute("datalayer")).getPollDAO().getPollById(poll_key);
            if (poll != null) {
                request.setAttribute("questions", ((PollDataLayer) request.getAttribute("datalayer")).getQuestionDAO().getQuestionsByPollId(poll_key));
                request.setAttribute("poll", poll);
                res.activate("ordinateQuestion.ftl.html", request, response);
            }
            else {
                request.setAttribute("message", "poll not exists");
                action_error(request, response);
            }
        }
        catch(DataException ex){
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }

    private void action_checkPoll(HttpServletRequest request, HttpServletResponse response, int poll_key) {
        int idU;
        try {
            idU = (((PollDataLayer) request.getAttribute("datalayer")).getPollDAO().getPollById(poll_key).getRespUser().getKey());
            if ((idU != Integer.parseInt(request.getSession().getAttribute("userid").toString()))) {
                request.setAttribute("message", "it is not your poll");
                action_error(request, response);
            }
        }
        catch (DataException e){
            request.setAttribute("message", "it is not your poll, asshole");
            action_error(request, response);
        }
    }

}
