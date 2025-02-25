package pollweb.controller;

import framework.data.DataException;
import framework.data.dao.PollDataLayer;
import framework.result.FailureResult;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import framework.security.SecurityLayer;
import pollweb.data.model.Poll;
import pollweb.data.model.Question;
import pollweb.data.model.ResponsibleUser;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Integer.parseInt;

public class PollDetailController extends PollBaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {

        try {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("page_title", "Login");
            request.setAttribute("login_error", "not ok polldetacontroller");
            res.activate("login.ftl.html", request, response);
        } catch(TemplateManagerException ex){

        }

    }

    private void action_error(HttpServletRequest request, HttpServletResponse response, String who) {

        try {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("page_title", "Login");
            if(who.equals("Data_error"))
            request.setAttribute("login_error", request.getAttribute("message"));
            res.activate("login.ftl.html", request, response);
        } catch(TemplateManagerException ex){

        }

    }
    private void action_error(HttpServletRequest request, HttpServletResponse response, boolean error) {


            request.setAttribute("page_title", "expection");

            if (request.getAttribute("exception") != null) {
                (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
            } else {
                (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
            }


    }

    private void action_default(HttpServletRequest request, HttpServletResponse response, int k) throws ServletException, IOException, TemplateManagerException {
        try {
            request.setAttribute("page_title", "Admin");
            request.setAttribute("poll", ((PollDataLayer)request.getAttribute("datalayer")).getPollDAO().getPollById(k));
          int  idU = (((PollDataLayer) request.getAttribute("datalayer")).getPollDAO().getPollById(k).getRespUser().getKey());
            if ((idU != Integer.parseInt(request.getSession().getAttribute("userid").toString()))) {
                request.setAttribute("message", "it is not your poll ");
                action_error(request, response, false);
            }
            request.setAttribute("questions", ((PollDataLayer)request.getAttribute("datalayer")).getQuestionDAO().getQuestionsByPollId(k));
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("poll_detail.ftl.html", request, response);
        } catch (TemplateManagerException ex) {

        } catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }

    private void action_deactivate(HttpServletRequest request, HttpServletResponse response, int k ) throws ServletException, IOException, TemplateManagerException {
        try {
            Poll to_deactivate = ((PollDataLayer)request.getAttribute("datalayer")).getPollDAO().getPollById(k);
            ((PollDataLayer)request.getAttribute("datalayer")).getPollDAO().setDeactivated(to_deactivate);
            action_default(request, response, k);
        } catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }

    private void action_activate(HttpServletRequest request, HttpServletResponse response, int k) throws ServletException, IOException, TemplateManagerException {
        try {
            Poll to_deactivate = ((PollDataLayer)request.getAttribute("datalayer")).getPollDAO().getPollById(k);
            ((PollDataLayer)request.getAttribute("datalayer")).getPollDAO().setActivated(to_deactivate);
            action_default(request, response, k);
        } catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }

    private void action_question(HttpServletRequest request, HttpServletResponse response,int k, int q) throws ServletException, IOException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("page_title", "Admin");
            request.setAttribute("poll", ((PollDataLayer)request.getAttribute("datalayer")).getPollDAO().getPollById(k));
            request.setAttribute("s_question", ((PollDataLayer)request.getAttribute("datalayer")).getQuestionDAO().getQuestionById(q));
            res.activate("poll_detail.ftl.html", request, response);
        } catch (TemplateManagerException ex) {

        } catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response, "Data_error");
        } catch (Exception e) {
            e.printStackTrace();
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
                res.activate("questions.ftl.html", request, response, false);
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

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        int k;
        int q;
        try {

            k = SecurityLayer.checkNumeric(request.getParameter("k"));
            if (request.getParameter("question") != null) {
                q = parseInt(request.getParameter("question"));

                    if (request.getParameter("deleteQuestion") != null) action_deleteQuestion(request, response, k, q);

              else   action_question(request, response, k, q);
            }
            else {if (k > 0 && request.getParameter("purpose") == null) {
                action_default(request, response, k);
            } else {
                if (request.getParameter("purpose").equals("deactivate")) {
                    action_deactivate(request, response, k);
                } else {
                    if (request.getParameter("purpose").equals("activate")) {
                        action_activate(request, response, k);
                    }

                    else {
                        action_error(request, response);
                    }
                }
            }
        }
        } catch (NumberFormatException ex) {
            request.setAttribute("message", ex);
            action_error(request, response, "Data_error");
        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);

        } catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);

        }
    }
}
