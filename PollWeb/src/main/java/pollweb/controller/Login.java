/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pollweb.controller;

import framework.data.DataException;
import framework.data.dao.PollDataLayer;
import framework.data.proxy.ResponsibleUserProxy;
import framework.result.FailureResult;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import framework.security.SecurityLayer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pollweb.data.impl.ResponsibleUserImpl;
import pollweb.data.model.ResponsibleUser;

/**
 *
 * @author achissimo
 */
public class Login extends PollBaseController {

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
            if (request.getAttribute("error").equals("poll_detail_controller")) {
                TemplateResult res = new TemplateResult(getServletContext());
                this.ref = request.getHeader("referer");
                request.setAttribute("page_title", "Login");
                request.setAttribute("login_error", "errore nel poll detail controller");
                res.activate("login.ftl.html", request, response);
            }


            if (request.getAttribute("login_failed").equals("missing_data")) {
            
            TemplateResult res = new TemplateResult(getServletContext());
            this.ref = request.getHeader("referer");
            request.setAttribute("page_title", "Login");
            request.setAttribute("login_error", "Campo mancante!");
            res.activate("login.ftl.html", request, response);   
            
        } 
            
        if(request.getAttribute("login_failed").equals("wrong_data")) {
            
            TemplateResult res = new TemplateResult(getServletContext());
            this.ref = request.getHeader("referer");
            request.setAttribute("page_title", "Login");
            request.setAttribute("login_error", "Username o password errati!");
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
            
            if(request.getParameter("login")!=null){
                action_login(request, response);
            }
            else {
            action_default(request, response);
            }

        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);

        } catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);

        } catch (DataException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    private void action_login(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateManagerException, DataException {
        String username = request.getParameter("email");
        String password = request.getParameter("password");
        ResponsibleUserImpl ru = new ResponsibleUserImpl();
        ru.setEmail(username);
        ru.setPwd(password);
      //  ru.setKey(id);

        if (!username.isEmpty() && !password.isEmpty()) {

        if(((PollDataLayer)request.getAttribute("datalayer")).getResponsibleUserDAO().checkAdmin(ru)){

            SecurityLayer.createSession(request, username);
            response.sendRedirect("admin");

            } else {
                if(((PollDataLayer)request.getAttribute("datalayer")).getResponsibleUserDAO().checkResponsible(ru)){
                    SecurityLayer.createSession(request, username);
                    response.sendRedirect("ResponsiblePage");
                } else {
                    request.setAttribute("login_failed", "wrong_data");
                    action_error(request, response);
                }
            }

        } else {
            request.setAttribute("login_failed", "missing_data");
            action_error(request, response);
        }
    }

 
    @Override
    public String getServletInfo() {
        return "Main login servlet";
    }// </editor-fold>
}
