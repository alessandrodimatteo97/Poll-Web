/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pollweb.controller;

import framework.data.DataException;
import framework.data.dao.PollDataLayer;
import framework.data.dao.ResponsibleUserDAO;
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
import javax.servlet.http.HttpSession;
import pollweb.data.impl.ResponsibleUserImpl;
import pollweb.data.model.ResponsibleUser;

/**
 *
 * @author achissimo
 */
public class BecomeResponsible extends PollBaseController {

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
            request.setAttribute("page_title", "BecomeResponsible");

            res.activate("becomeResponsible.ftl.html", request, response);
       
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        HttpSession session_logged = SecurityLayer.checkSession(request);
        
        try {
            if (session_logged != null) {
                if (((PollDataLayer) request.getAttribute("datalayer")).getResponsibleUserDAO().getResponsibleUser(session_logged.getAttribute("token").toString()).getAccepted()) {
                    response.sendRedirect("admin");
                }
            }
            request.setAttribute("ok", false);
     
            if(request.getParameter("register")!= null){
                this.action_register(request, response);
            }

            else{
            action_default(request, response);
            }

        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);

        } catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);

        } catch (DataException ex) {
            Logger.getLogger(BecomeResponsible.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void action_register(HttpServletRequest request, HttpServletResponse response){
       String firstName = request.getParameter("firstName");
       if(firstName == null){
       }
     ServletContext context = getServletContext( );
       context.log(firstName);
     String surname = request.getParameter("surname");
      if(surname == null){
       }
     String fiscalCode = request.getParameter("fiscalCode");
      if(fiscalCode == null){
       }
     String email = request.getParameter("email");
      if(email == null){
       }
     String pwd = request.getParameter("pwd");
      if(pwd == null){
       }
     
     ResponsibleUser ru = new ResponsibleUserImpl();
     
     ru.setNameR(firstName);
     ru.setSurnameR(surname);
     ru.setFiscalCode(fiscalCode);
     ru.setEmail(email);
     ru.setPwd(pwd);
     
        try {

            ((PollDataLayer)request.getAttribute("datalayer")).getResponsibleUserDAO().storeResponsibleUser(ru);
              TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("page_title", "BecomeResponsible");
            request.setAttribute("ok", true);
            res.activate("becomeResponsible.ftl.html", request, response);
        } catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        } catch (TemplateManagerException ex) {
            Logger.getLogger(BecomeResponsible.class.getName()).log(Level.SEVERE, null, ex);
        }
   }


    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Main Poll servlet";
    }// </editor-fold>

}
