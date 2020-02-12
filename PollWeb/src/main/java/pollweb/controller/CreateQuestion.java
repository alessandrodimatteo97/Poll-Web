package pollweb.controller;

import framework.data.DataException;
import framework.data.dao.PollDataLayer;
import framework.result.FailureResult;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import framework.security.SecurityLayer;
import org.json.JSONObject;
import pollweb.data.model.Poll;
import pollweb.data.model.Question;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateQuestion extends PollBaseController {

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
        int question_key;
        int idU = 0;
        try {

            if(SecurityLayer.isValid(request)) {
                if (request.getParameter("k") != null) {
                    poll_key = SecurityLayer.checkNumeric(request.getParameter("k"));
                    if((poll_key!=0) ) {
                        idU =  (((PollDataLayer) request.getAttribute("datalayer")).getPollDAO().getPollById(poll_key).getRespUser().getKey());
                        if ((idU != Integer.parseInt(request.getSession().getAttribute("userid").toString()))){
                            request.setAttribute("message", "it is not your poll asshole");
                            action_error(request, response);
                        }}


                     if (request.getParameter("qk") != null) {
                        question_key = SecurityLayer.checkNumeric(request.getParameter("qk"));
                        if (request.getParameter("addQuestion") != null) {
                            action_question(request, response, poll_key, 0); // mi fa creare una nuova domanda
                        }
                    else if (request.getParameter("nextQuestion")!=null) action_updateQuestion(request, response,poll_key, question_key);
                        else {
                            action_question(request, response, poll_key, question_key); // mi fa

                        }
                    }

                } else {
                    request.setAttribute("message", "error");
                    action_error(request, response);
                }
            }
            else {
                response.sendRedirect("Login");
            }
        } catch (NumberFormatException ex) {
            request.setAttribute("message", "invalid number");
            action_error(request, response);
        } catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);    } catch (DataException e) {
            e.printStackTrace();
        }
    }




    private void action_updateQuestion(HttpServletRequest request, HttpServletResponse response, int poll_key , int question_key) throws TemplateManagerException, IOException, ServletException {
        try{
            Question question;
            pollweb.data.model.Poll poll = ((PollDataLayer)request.getAttribute("datalayer")).getPollDAO().getPollById(poll_key);

            if (question_key > 0){
                question = ((PollDataLayer) request.getAttribute("datalayer")).getQuestionDAO().getQuestionById(question_key);
            }
            else {
                question = ((PollDataLayer) request.getAttribute("datalayer")).getQuestionDAO().createQuestion();
            }
            if(question != null && request.getParameter("Type")!=null && !request.getParameter("Type").isEmpty() && request.getParameter("title")!=null && !request.getParameter("title").isEmpty() && request.getParameter("note")!=null && !request.getParameter("note").isEmpty() && request.getParameter("obbligated")!=null && !request.getParameter("obbligated").isEmpty()  ){
                if((request.getParameter("Type").equals("single choice") || request.getParameter("Type").equals("multiple choice")) && request.getParameterValues("fieldName").length>0 ) {
                    //  servletContext.log(String.valueOf(request.getParameterValues("fieldName").length));
                    if(request.getParameterValues("fieldName")!=null) {
                        String[] array = request.getParameterValues("fieldName");
                        JSONObject jsonObject = new JSONObject();
                        int index = 0;
                        for (String i : array) {
                            if (!i.matches("[ ]+") && !i.isEmpty()) jsonObject.put(String.valueOf(++index), i);
                        }
                        if (!jsonObject.isEmpty()) question.setPossibleAnswer(jsonObject);
                        else {
                            request.setAttribute("message", "unable to save question, type question is single or multiple choice but possible answers are empty");
                            action_error(request, response);
                        }
                    }
                    else {
                        request.setAttribute("message", "type single/multiple choise but there are no possible questions");
                        action_error(request, response);
                    }

                }

                else question.setPossibleAnswer(null);

                question.setPoll(poll); // stiamo qui
                question.setTextq(request.getParameter("title"));
                question.setTypeP(request.getParameter("Type"));
                question.setNote(request.getParameter("note"));
                ((PollDataLayer)request.getAttribute("datalayer")).getQuestionDAO().store(question);
                request.setAttribute("poll", poll);
                request.setAttribute("finish", true);
                action_question(request, response, poll.getKey(), question.getKey());
            }
            else {
                request.setAttribute("message", "unable to store question, insufficent parameter");
                action_error(request, response);
            }
        }
        catch(DataException ex){
            request.setAttribute("message", "Data access unable: " + ex.getMessage());
            action_error(request, response);
        }

    }
    private void action_question(HttpServletRequest request, HttpServletResponse response, int poll_key, int question_key) throws IOException, ServletException, TemplateManagerException {
        TemplateResult res = new TemplateResult(getServletContext());

        try {
            if(((PollDataLayer) request.getAttribute("datalayer")).getQuestionDAO().checkQuestionPoll(poll_key, question_key)) { // devo anche inserire le domande
                if (question_key > 0) {
                    Question question = ((PollDataLayer) request.getAttribute("datalayer")).getQuestionDAO().getQuestionById(question_key);
                    Poll poll = ((PollDataLayer) request.getAttribute("datalayer")).getPollDAO().getPollById(poll_key);
                    request.setAttribute("question", question);
                    request.setAttribute("poll", poll);
                    request.setAttribute("finish", true);
                    // res.activate("createQuestion.ftl.html", request, response);
                } else {

                    Poll poll = ((PollDataLayer) request.getAttribute("datalayer")).getPollDAO().getPollById(poll_key);
                    Question question = ((PollDataLayer) request.getAttribute("datalayer")).getQuestionDAO().createQuestion();
                    request.setAttribute("poll", poll);
                    request.setAttribute("question", question);
                }
                res.activate("createQuestion.ftl.html", request, response);
            }
            else {
                request.setAttribute("message", "question is not included in the poll");
                action_error(request, response);
            }

        }

        catch(DataException ex){
            request.setAttribute("message", "Data access exception: " + ex.getMessage());

            action_error(request, response);
        }
    }


}
