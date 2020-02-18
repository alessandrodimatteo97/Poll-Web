/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pollweb.controller;

import framework.data.DataException;
import framework.data.dao.PollDataLayer;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import framework.security.SecurityLayer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author davide
 */
@WebServlet(name = "ShowAnswer", urlPatterns = {"/ShowAnswer"})
public class ShowAnswer extends PollBaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        HttpSession session_online = SecurityLayer.checkSession(request);
        String token = session_online.getAttribute("token").toString();   
        ServletContext sc = getServletContext();
        sc.log(token);
        try {
            if(!request.getParameter("q").isEmpty()) {
                int question_key = SecurityLayer.checkNumeric(request.getParameter("q"));
                    if(
                        ((PollDataLayer)request.getAttribute("datalayer")).getQuestionDAO().checkQuestionUser(question_key, token)
                        /*|| QUESTION APERTA FA PARTE DELLA DOMANDA*/ )
                    {
                        action_show_answers(request, response, question_key);
                    } else {
                  //      response.setStatus(401);
                        action_error(request, response);
                    }
            } else {
                action_error(request, response);
            }
        } catch (DataException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ShowAnswer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TemplateManagerException ex) {
            Logger.getLogger(ShowAnswer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void action_show_answers(HttpServletRequest request, HttpServletResponse response, int question_key) throws IOException, TemplateManagerException, DataException {
        TemplateResult res = new TemplateResult(getServletContext());
        
        request.setAttribute("question", ((PollDataLayer)request.getAttribute("datalayer")).getQuestionDAO().getQuestionById(question_key));
        
        request.setAttribute("answers", ((PollDataLayer)request.getAttribute("datalayer")).getAnswerDAO().getAnswersByQuestionId(question_key));

        request.setAttribute("page_title", "admin");
            
        res.activate("answerOfQuestion.ftl.html", request, response);
        
    }

    private void action_error(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        throw new ServletException("This question is not yours! Change number of the question.");    
    }

}
