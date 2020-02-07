package pollweb.controller;

import framework.data.DataException;
import framework.data.dao.PollDataLayer;
import framework.result.FailureResult;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import framework.security.SecurityLayer;
import pollweb.data.model.Poll;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SummaryPollController extends PollBaseController {

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
        int question_key;

        try {

           if (SecurityLayer.isValid(request)) {
               if (request.getParameter("k") != null) {
                   poll_key = SecurityLayer.checkNumeric(request.getParameter("k"));
                   if ((poll_key != 0)) {
                       idU = (((PollDataLayer) request.getAttribute("datalayer")).getPollDAO().getPollById(poll_key).getRespUser().getKey());
                       if ((idU != Integer.parseInt(request.getSession().getAttribute("userid").toString()))) {
                           request.setAttribute("message", "it is not your poll asshole");
                           action_error(request, response);
                       }else if (request.getParameter("qk") != null) {
                           question_key = SecurityLayer.checkNumeric(request.getParameter("qk"));

                           if (request.getParameter("deleteQuestion") != null)
                               action_deleteQuestion(request, response, poll_key, question_key);
                       }
                       else {
                           action_summary_poll(request, response, poll_key);
                       }
                   }

               }


           }
           else {
               response.sendRedirect("Login");
           }
       }
        catch (NumberFormatException ex) {
            request.setAttribute("message", "invalid number");
            action_error(request, response);
        } catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        } catch (DataException e) {
          request.setAttribute("message", e);
          action_error(request, response);
       } catch (IOException e) {
           request.setAttribute("message", e);
           action_error(request, response);
        }
    }

    private void action_summary_poll(HttpServletRequest request, HttpServletResponse response, int poll_key) throws TemplateManagerException {
        try {


            TemplateResult res = new TemplateResult(getServletContext());
            Poll poll = ((PollDataLayer) request.getAttribute("datalayer")).getPollDAO().getPollById(poll_key);
            if (poll != null) {
                request.setAttribute("questions", ((PollDataLayer) request.getAttribute("datalayer")).getQuestionDAO().getQuestionsByPollId(poll_key));
                request.setAttribute("poll", poll);
                res.activate("pollSummary.ftl.html", request, response);
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


    private void action_deleteQuestion(HttpServletRequest request, HttpServletResponse response, int poll_key, int question_key) throws TemplateManagerException {
       try {
        ((PollDataLayer) request.getAttribute("datalayer")).getQuestionDAO().deleteQuestion(question_key);
        TemplateResult res = new TemplateResult(getServletContext());
        Poll poll = ((PollDataLayer) request.getAttribute("datalayer")).getPollDAO().getPollById(poll_key);
        if (poll != null) {
            request.setAttribute("questions", ((PollDataLayer) request.getAttribute("datalayer")).getQuestionDAO().getQuestionsByPollId(poll_key));
            request.setAttribute("poll", poll);
            res.activate("questionSummary.ftl.html", request, response, false);
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



}
