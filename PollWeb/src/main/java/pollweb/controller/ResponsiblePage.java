package pollweb.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import framework.data.dao.PollDataLayer;
import framework.result.FailureResult;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import framework.security.SecurityLayer;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pollweb.controller.PollBaseController;

/**
 *
 * @author achissimo
 */
public class ResponsiblePage extends PollBaseController {

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

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
           TemplateResult res = new TemplateResult(getServletContext());
            
            request.setAttribute("page_title", "ResponsiblePage");
           
            res.activate("ResponsiblePage.ftl.html", request, response);
           
        
    } 

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        try {
            HttpSession s = SecurityLayer.checkSession(request);
            if (s != null) {
                action_default(request, response);
            } else {
                //se la pagina non � accessibile come utente anonimo...
                //if this page cannot be accessed as anonymous user...
                //
                //1) ridirigiamo a quella di login
                //1) redirect to the login page               
                action_loginredirect(request, response);
                //
                //2) oppure dichiariamo che � richiesta la login, ma lasciamo all'utente la scelta
                //2) or declare that a login is required and let the user choose
                //action_loginrequest(request, response);
                //
                //3) o generiamo un errore
                //3) or output an error message
                //action_accessdenied(request, response);
            }
        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        } catch (TemplateManagerException ex) {
            Logger.getLogger(ResponsiblePage.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    private void action_login(HttpServletRequest request, HttpServletResponse response, String email, String password) {
//            Boolean login = ((PollDataLayer) request.getAttribute("datalayer")).getResponsibleUserDAO().checkResponsible(user);

    }

    private void action_loginredirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("Login");
    }
}
