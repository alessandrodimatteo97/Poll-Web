/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pollweb.controller;

import framework.data.DataException;
import framework.data.dao.PollDataLayer;
import org.json.JSONObject;
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
import pollweb.data.model.Question;
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

// TODO il responsabile può accedere solo ai suoi sondaggi, quindi fare controllo sondaggio RU
// TODO prendere il vero responsabile in sessione, e metterlo dentro il poll...
    private void action_update(HttpServletRequest request, HttpServletResponse response, int poll_key) throws IOException, ServletException, TemplateManagerException {
        try {
            Poll poll;
            if (poll_key > 0) {
                poll = ((PollDataLayer)request.getAttribute("datalayer")).getPollDAO().getPollById(poll_key);

            } else {
                poll = ((PollDataLayer)request.getAttribute("datalayer")).getPollDAO().createPoll();
            }
            if (poll != null ){
                ResponsibleUser ru = ((PollDataLayer)request.getAttribute("datalayer")).getResponsibleUserDAO().getResponsibleUser(4);
                // da modificare bene
                 ServletContext context = getServletContext( );
                 context.log(ru.getNameR());
                if (ru != null && request.getParameter("title")!=null && !request.getParameter("title").isEmpty() &&  request.getParameter("closerText")!=null && !request.getParameter("closerText").isEmpty() && request.getParameter("url")!=null && !request.getParameter("url").isEmpty() && request.getParameter("type")!=null && !request.getParameter("type").isEmpty()) {
                    poll.setTitle(request.getParameter("title"));
                    poll.setRespUser(ru);
                    poll.setApertureText(request.getParameter("apertureText"));
                    poll.setCloserText(request.getParameter("closerText"));
                    poll.setUrl(request.getParameter("url"));
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
         int question_key;
        try {


           if(request.getParameter("k")!=null){
                poll_key = SecurityLayer.checkNumeric(request.getParameter("k"));
                
                if (request.getParameter("update") != null) {
                    action_update(request, response, poll_key);
                } 
                else if (request.getParameter("nextQuestion")!= null){
                    // inserisco
                    question_key = SecurityLayer.checkNumeric(request.getParameter("qk"));
                    action_updateQuestion(request, response, poll_key, question_key);
                }
                else if (request.getParameter("Finish")!=null){
                    action_summary_poll(request, response, poll_key);
                }
                else if( request.getParameter("qk")!=null){
                    question_key = SecurityLayer.checkNumeric(request.getParameter("qk"));
                    if(request.getParameter("addQuestion")!=null) action_question(request, response, poll_key, 0); // mi fa creare una nuova domanda
                    else if (request.getParameter("deleteQuestion")!=null) action_deleteQuestion(request, response, poll_key, question_key);
                    else {
                      action_question(request, response, poll_key, question_key); // mi fa

                    }
                }
                else { // create Poll
                    action_write(request, response, poll_key);
                }
           }
           else {
               //TODO far vedere i poll creati dal responsabile in sessione
               action_default(request, response);
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

    private void action_deleteQuestion(HttpServletRequest request, HttpServletResponse response, int poll_key, int question_key) throws DataException, TemplateManagerException {
      ((PollDataLayer) request.getAttribute("datalayer")).getQuestionDAO().deleteQuestion(question_key);
        this.action_summary_poll(request, response, poll_key);
    }


    private void action_updateQuestion(HttpServletRequest request, HttpServletResponse response, int poll_key , int question_key) throws TemplateManagerException, IOException, ServletException {
          try{
              Question question;
              Poll poll = ((PollDataLayer)request.getAttribute("datalayer")).getPollDAO().getPollById(poll_key);

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
                          if (i.matches("[a-zA-Z]") && !i.isEmpty()) jsonObject.put(String.valueOf(++index), i);
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
    
    
    
    
    
    
// TODO action_default per settare la pagina dei poll getPollByResp
    private void action_default(HttpServletRequest request, HttpServletResponse response) {

    }

   
    
     
}

/* 



Se

HttpSession s = SecurityLayer.checkSession(request);
                    ServletContext context = getServletContext( );
                    if(s!= null) context.log("stai in sessione coglione");
                    else {
                        context.log("non stai in sessione coglione");
                    }
*/





