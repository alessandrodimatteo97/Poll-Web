package pollweb.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.sun.xml.internal.fastinfoset.util.CharArray;
import framework.data.DataException;
import framework.data.dao.PollDataLayer;
import framework.data.proxy.AnswerProxy;
import framework.data.proxy.QuestionProxy;
import framework.result.FailureResult;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import framework.security.SecurityLayer;
import static framework.security.SecurityLayer.checkNumeric;
import freemarker.template.utility.NumberUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.NumberUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import pollweb.data.impl.AnswerImpl;
import pollweb.data.model.Answer;
import pollweb.data.model.Partecipant;
import pollweb.data.model.Question;

/**
 *
 * @author achissimo
 */
public class PollController extends PollBaseController {

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
           ServletContext sc = getServletContext();
            request.setAttribute("page_title", "Poll name");
            pollweb.data.model.Poll p = ((PollDataLayer) request.getAttribute("datalayer")).getPollDAO().getPollById(n);
           
            if(p.isActivated()){

           String type = ((PollDataLayer) request.getAttribute("datalayer")).getPollDAO().getPollById(n).getType();   
          
           request.setAttribute("poll" ,((PollDataLayer)request.getAttribute("datalayer")).getPollDAO().getPollById(n));
           
           
          if(type.matches("open")){
            request.setAttribute("questions", ((PollDataLayer)request.getAttribute("datalayer")).getQuestionDAO().getQuestionsByPollId(n));
            res.activate("poll.ftl.html", request, response);
          }else{
               res.activate("login_poll.ftl.html",request,response);
           
           }}//res.activate("error.ftl.html", request, response);
            }catch (DataException ex) {
           Logger.getLogger(PollController.class.getName()).log(Level.SEVERE, null, ex);
       }
    }

    

        private void action_answer(HttpServletRequest request, HttpServletResponse response, int n) throws TemplateManagerException, ParseException {
            try{
                TemplateResult res = new TemplateResult(getServletContext());
                 request.setAttribute("page_title", "Confirm Page");
              ServletContext sc = getServletContext();
               List<Question> question = ((PollDataLayer)request.getAttribute("datalayer")).getQuestionDAO().getQuestionsByPollId(n);
               
                
                
          for(Question q : question){
                 ArrayList<String> answer = new ArrayList<>();
                  String str=request.getParameter(Integer.toString(q.getKey()));
                 
                  if(q.getTypeP().equalsIgnoreCase( "short text")){
                     if(q.getObbligated() && str.isEmpty() ){
                         request.setAttribute("message", "devi rispondere alle domande obbligatorie");
                         action_error(request, response);
                     }
                     if(str.length()<= 65) answer.add(str);
                     else sc.log("TROPPO LUNGO");
                 
                  }else if(q.getTypeP().equalsIgnoreCase( "long text")){
                     if(q.getObbligated() && str.isEmpty() ) sc.log("ERRORE è richiesta");
                   answer.add(request.getParameter(Integer.toString(q.getKey())));
                 
                  }else if (q.getTypeP().equalsIgnoreCase("numeric")){
                      if(q.getObbligated() && str.isEmpty() ) sc.log("ERRORE è richiesta");
                      if(NumberUtils.isDigits(str)) answer.add(request.getParameter(Integer.toString(q.getKey())));
                    //res.activate("error.ftl.html", request, response);
                
                  }else if(q.getTypeP().equalsIgnoreCase("date")){
                      if(q.getObbligated() && str.isEmpty() ) sc.log("ERRORE è richiesta");
                      String test = new String();
                     if (str.length()== 10) {
                      for (int i=0; i< str.length(); i++){
                          if (Character.isDigit(str.charAt(i))) test = test+str.charAt(i);
                      }
                      if(test.length()== 8){
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = formatter.parse(str);
                        if(!formatter.format(date).equals(str)){
                            sc.log("DATA NON NELLO GIUSTO FORMATO");
                        }else{
                        sc.log(date.toString());
                        answer.add(str);
                        } 
                      }else sc.log("ERRORE ci sono pochi numeri");
                     }else sc.log("ERRORE DATA non corretta");
                
                  }else if (q.getTypeP().equalsIgnoreCase("single choice")){
                     if(q.getObbligated() && str.isEmpty() ) sc.log("ERRORE");
                     if(!q.getObbligated() && str.equals("-nessuna selezionata-")) answer.add(str);
                     String respo=new String();
                     String letter = new String();
                     for(int i=0;i<str.length();i++){
                         if(Character.isAlphabetic(str.charAt(i))){
                             letter = letter+str.charAt(i);
                            if(q.getPossibleAnswer().has(letter)) {
                              respo=  letter + " " + q.getPossibleAnswer().getString(letter);
                               break;
                            }sc.log("cazzi");
                         }else sc.log("c'è qualcosa che non va");
                     }
                   if(respo.equals(str)){ answer.add(str);
                   }else sc.log("cazzissimi");
                   
              }else if(q.getTypeP().equalsIgnoreCase( "multiple choice")){
                 
                  String[] a = request.getParameterValues(Integer.toString(q.getKey()));
                  
                  // if(a.length<1 && q.getObbligated() ) sc.log("ERRORE");                     
                    //sc.log(String.valueOf(a.length));
                 for (String s : a){
                      String respo=new String();
                     String letter = new String();
                     for(int i=0;i<s.length();i++){
                         if(Character.isAlphabetic(s.charAt(i))){
                             letter = letter+s.charAt(i);
                            if(q.getPossibleAnswer().has(letter)) {
                              respo=  letter + " " + q.getPossibleAnswer().getString(letter);
                              sc.log(respo) ;
                              break;
                            }sc.log("cazzi");
                         }else sc.log("c'è qualcosa che non va");
                     }
                   if(respo.equals(s)){ answer.add(s);
                   }else sc.log("cazzissimi"); 
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
        
        
    private void action_confirm(HttpServletRequest request, HttpServletResponse response, int n) throws DataException, ParseException {
      try{
               TemplateResult res = new TemplateResult(getServletContext());
                // request.setAttribute("page_title", "Confirm Page");
              ServletContext sc = getServletContext();
               List<Question> question = ((PollDataLayer)request.getAttribute("datalayer")).getQuestionDAO().getQuestionsByPollId(n);
               
                
               ArrayList<Answer> answer = new ArrayList<>(); 
               Partecipant p = ((PollDataLayer)request.getAttribute("datalayer")).getPartecipantDAO().getUserById(5);
          for(Question q : question){
              if(q!= null){
                 Answer a = ((PollDataLayer)request.getAttribute("datalayer")).getAnswerDAO().createAnswer();
                  String str=request.getParameter(Integer.toString(q.getKey()));
                 sc.log(String.valueOf(q.getKey()));
                  if(q.getTypeP().equalsIgnoreCase( "short text")){
                     if(q.getObbligated() && str.isEmpty() ) sc.log("ERRORE è richiesta");
                     if(str.length()<= 65) {
                         sc.log(String.valueOf(q.getKey()));
                  
                         a.setQuestion(q);
                         a.setPartecipant(p);
                         JSONObject obj = new JSONObject();
                         obj.put("1", str);
                         a.setTextA(obj);
                         
                  }
                     else sc.log("TROPPO LUNGO");
                 
                  }else if(q.getTypeP().equalsIgnoreCase( "long text")){
                     if(q.getObbligated() && str.isEmpty() ) sc.log("ERRORE è richiesta");
                         a.setQuestion(q);
                         a.setPartecipant(p);
                         JSONObject obj = new JSONObject();
                         obj.put("1", str);
                         a.setTextA(obj);
                 
                  }else if (q.getTypeP().equalsIgnoreCase("numeric")){
                      if(q.getObbligated() && str.isEmpty() ) sc.log("ERRORE è richiesta");
                      if(NumberUtils.isDigits(str)) {
                           a.setQuestion(q);
                         a.setPartecipant(p);
                         JSONObject obj = new JSONObject();
                         obj.put("1", str);
                         a.setTextA(obj);
                      }
                          
                    //res.activate("error.ftl.html", request, response);
                
                  }else if(q.getTypeP().equalsIgnoreCase("date")){
                      if(q.getObbligated() && str.isEmpty() ) sc.log("ERRORE è richiesta");
                      String test = new String();
                     if (str.length()== 10) {
                      for (int i=0; i< str.length(); i++){
                          if (Character.isDigit(str.charAt(i))) test = test+str.charAt(i);
                      }
                      if(test.length()== 8){
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = formatter.parse(str);
                        if(!formatter.format(date).equals(str)){
                            sc.log("DATA NON NELLO GIUSTO FORMATO");
                        }else{
                         a.setQuestion(q);
                         a.setPartecipant(p);
                         JSONObject obj = new JSONObject();
                         obj.put("1", str);
                         a.setTextA(obj);
                        } 
                      }else sc.log("ERRORE ci sono pochi numeri");
                     }else sc.log("ERRORE DATA non corretta");
                
                  }else if (q.getTypeP().equalsIgnoreCase("single choice")){
                     if(q.getObbligated() && str.isEmpty() ) sc.log("ERRORE");
                     if(!q.getObbligated() && str.equals("-nessuna selezionata-")) ;
                     String respo=new String();
                     String letter = new String();
                     for(int i=0;i<str.length();i++){
                         if(Character.isAlphabetic(str.charAt(i))){
                             letter = letter+str.charAt(i);
                            if(q.getPossibleAnswer().has(letter)) {
                              respo=  letter + " " + q.getPossibleAnswer().getString(letter);
                               break;
                            }sc.log("cazzi");
                         }else sc.log("c'è qualcosa che non va");
                     }
                   if(respo.equals(str)){ 
                        a.setQuestion(q);
                         a.setPartecipant(p);
                         JSONObject obj = new JSONObject();
                         obj.put(letter, q.getPossibleAnswer().getString(letter));
                         a.setTextA(obj);
                   }else sc.log("cazzissimi");
                   
              }else if(q.getTypeP().equalsIgnoreCase( "multiple choice")){
                 
                  
                  String[] multi = request.getParameterValues(Integer.toString(q.getKey()));
                  a.setQuestion(q);
                         a.setPartecipant(p);
                         JSONObject obj = new JSONObject();
                 // if(multi.length<1 && q.getObbligated() ) sc.log("ERRORE");                     
                    //sc.log(String.valueOf(a.length));
                 for (String s : multi){
                      String respo=new String();
                     String letter = new String();
                     for(int i=0;i<s.length();i++){
                         if(Character.isAlphabetic(s.charAt(i))){
                             letter = letter+s.charAt(i);
                            if(q.getPossibleAnswer().has(letter)) {
                              respo=  letter + " " + q.getPossibleAnswer().getString(letter);
                              sc.log(respo) ;
                              break;
                            }sc.log("cazzi");
                         }else sc.log("c'è qualcosa che non va");
                     }
                   if(respo.equals(s)){ 
                       obj.put(letter, q.getPossibleAnswer().getString(letter));
                   }else sc.log("cazzissimi"); 
                 
                 }
                 a.setTextA(obj);
                  
              } answer.add(a);
               

          }
                        for (Answer a : answer){
                  sc.log(a.getQuestion().toString() + a.getTextA().toString());
              }
            /*request.setAttribute("questions",question);
            request.setAttribute("poll" ,((PollDataLayer)request.getAttribute("datalayer")).getPollDAO().getPollById(n));
             res.activate("confirmPoll.ftl.html", request, response);*/
            }
            }
                catch(DataException ex){
                request.setAttribute("message", ex);
                action_error(request, response);
            }
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
            n = SecurityLayer.checkNumeric(request.getParameter("n"));
             if(request.getParameter("showResume")!= null){
                 action_answer(request, response,n);
             }else if (request.getParameter("confirm")!= null){
                action_confirm(request,response, n);
            }else action_default(request, response, n);
             
        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);

        } catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);

        } catch (DataException ex) {
          request.setAttribute("mesage", ex);
          this.action_error(request, response);
       } catch (ParseException ex) {
          this.action_error(request, response);
       }
  
    }
        
    @Override
    public String getServletInfo() {
        return "Main Newspaper servlet";
    }// </editor-fold>

}
