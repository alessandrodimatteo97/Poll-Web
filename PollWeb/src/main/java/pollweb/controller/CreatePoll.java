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
        private void action_question(HttpServletRequest request, HttpServletResponse response, int poll_key) throws IOException, ServletException, TemplateManagerException {
           try {
               
          
            TemplateResult res = new TemplateResult(getServletContext());
           // List<Question> q = new ArrayList<Question>();
          //  Question quest = ((PollDataLayer) request.getAttribute("datalater")).getQuestionDAO().createQuestion()
           Poll poll = ((PollDataLayer)request.getAttribute("datalayer")).getPollDAO().getPollById(poll_key);
            request.setAttribute("poll", poll);
       //       request.setAttribute("finish", false);

            res.activate("createQuestion.ftl.html", request, response);
             }
           catch(DataException ex){
             request.setAttribute("message", "Data access exception: " + ex.getMessage());

               action_error(request, response);
           }
        }
        
    
    private void action_update(HttpServletRequest request, HttpServletResponse response, int poll_key) throws IOException, ServletException, TemplateManagerException {
        try {
           TemplateResult res = new TemplateResult(getServletContext());
            Poll poll;
            if (poll_key > 0) {
                poll = ((PollDataLayer)request.getAttribute("datalayer")).getPollDAO().getPollById(poll_key);
            //    request.setAttribute("", poll);
                //tion_write(request, response, poll_key);
            } else {
                poll = ((PollDataLayer)request.getAttribute("datalayer")).getPollDAO().createPoll();
            }
            if (poll != null ){
                ResponsibleUser ru = ((PollDataLayer)request.getAttribute("datalayer")).getResponsibleUserDAO().getResponsibleUser(4); // da modficare in base a chi è loggato quindi scrivere codice che prende il token salvato in memoria
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
                    //delega il resto del processo all'azione write
                    //delegates the rest of the process to the write action 
                    //action_question(request, response, poll.getKey()); // deve delegare l'azione all'inserimento delle domande 
                action_write(request, response, poll.getKey());
                } else {
                    request.setAttribute("message", "Cannot update poll: undefined ");
                    action_error(request, response);
                }
            } else {
                request.setAttribute("message", "Cannot update poll: insufficient parameters");
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
                
        try {
              
           
           
                poll_key = SecurityLayer.checkNumeric(request.getParameter("k"));
                
                if (request.getParameter("update") != null) {
                    action_update(request, response, poll_key);
                } 
                else if (request.getParameter("nextQuestion")!= null){
                    action_aggiungistacazzodidomanda(request, response, poll_key);
                }
                else if (request.getParameter("Finish")!=null){
                    action_summaryPoll(request, response, poll_key);
                }
                else if(request.getParameter("addQuestions")!=null){
                    action_question(request, response, poll_key);
                }
                else {
                    action_write(request, response, poll_key);
                }
        } catch (NumberFormatException ex) {
            request.setAttribute("message", "invalid number");
            action_error(request, response);
        } catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);    }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private void action_aggiungistacazzodidomanda(HttpServletRequest request, HttpServletResponse response, int poll_key) throws TemplateManagerException {
          try{
               ServletContext context = getServletContext( );
               context.log("qui ci vado coglione");
        TemplateResult res = new TemplateResult(getServletContext());
        Question question = ((PollDataLayer)request.getAttribute("datalayer")).getQuestionDAO().createQuestion();
           Poll poll = ((PollDataLayer)request.getAttribute("datalayer")).getPollDAO().getPollById(poll_key);
            question.setPoll(poll);
            question.setTextq("proviamoci");
            question.setTypeP("single choice");
            question.setNote("rispondi ");
            ((PollDataLayer)request.getAttribute("datalayer")).getQuestionDAO().store(question);
            request.setAttribute("poll", poll);
            request.setAttribute("finish", true);
            res.activate("createQuestion.ftl.html", request, response);
            }
          catch(DataException ex){
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
             action_error(request, response);
        }
            
    }

    private void action_summaryPoll(HttpServletRequest request, HttpServletResponse response, int poll_key) throws TemplateManagerException {
       try {
           
      
        TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("questions", ((PollDataLayer)request.getAttribute("datalayer")).getQuestionDAO().getQuestionsByPollId(poll_key));
    
            res.activate("pollSummary.ftl.html", request, response);
             }
       catch(DataException ex){
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
             action_error(request, response);
       }
    }

   
    
     
}

/* action_default per settare la pagina dei pull getPollByResp,
   metodo per creare/modicare domande 
   pagina intermedia nel momento in cui l'utente vuole modificare le domande... 



Se

HttpSession s = SecurityLayer.checkSession(request);
                    ServletContext context = getServletContext( );
                    if(s!= null) context.log("stai in sessione coglione");
                    else {
                        context.log("non stai in sessione coglione");
                    }
*/




/*
Fare controllo all'inizio su parametro q, che indica la question, quindi carico la question per modificarla;
Alla fine carico la pagina riassuntiva con il poll con tutte le domande e risposte e anche il poll(,
in modo tale da poter modificare alla fine se non ci va bene qualche valore...

Metodo nel dao che controlla se ci sono domande... appena aggiunto, fare il controllo all'inizio...
*/