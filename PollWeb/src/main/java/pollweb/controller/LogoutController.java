/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pollweb.controller;

import framework.data.DataException;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import framework.security.SecurityLayer;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author davide
 */
public class LogoutController extends PollBaseController {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    String ref;
    
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        
        try {
            if (request.getAttribute("error").equals("session_error")) 
            {
                TemplateResult res = new TemplateResult(getServletContext());
                this.ref = request.getHeader("referer");
                request.setAttribute("page_title", "Login");
                request.setAttribute("login_error", "Errore nella sessione, effettua prima il login");
                res.activate("login.ftl.html", request, response);    
            } 
            
            else {
                
                TemplateResult res = new TemplateResult(getServletContext());
                this.ref = request.getHeader("referer");
                request.setAttribute("page_title", "Login");
                request.setAttribute("login_error", "Errore sconosciuto!!!");
                res.activate("login.ftl.html", request, response);   
                
            }
        } catch(TemplateManagerException ex){
            
        }
                
    }
    

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
            TemplateResult res = new TemplateResult(getServletContext());
            this.ref = request.getHeader("referer");
            request.setAttribute("page_title", "Login");
            
            res.activate("login.ftl.html", request, response);
    }
    

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
                        
            if(SecurityLayer.isValid(request)) {
                action_logout(request, response);
            } else {
                request.setAttribute("error", "session_error");
                action_error(request, response);
            }
    }  catch (IOException ex) {
            Logger.getLogger(LogoutController.class.getName()).log(Level.SEVERE, null, ex);
        }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    
 
    
}

    private void action_logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
            SecurityLayer.disposeSession(request);
            response.sendRedirect("Home");
    }

@Override
    public String getServletInfo() {
        return "Main login servlet";
    }
}