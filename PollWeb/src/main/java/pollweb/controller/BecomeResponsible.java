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
        
        try {
            request.setAttribute("ok", false);
            if(request.getParameter("register")!= null){
                this.action_register(request, response);
            }
        //    if(prova ==)
            
            else{
            action_default(request, response);
            }

        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);

        } catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);

        }
    }
    
    private void action_register(HttpServletRequest request, HttpServletResponse response){
       String firstName = request.getParameter("firstName");
     ServletContext context = getServletContext( );
       context.log(firstName);
     String surname = request.getParameter("surname");
     String fiscalCode = request.getParameter("fiscalCode");
     String email = request.getParameter("email");
     String pwd = request.getParameter("pwd");
     
     ResponsibleUser ru = new ResponsibleUserImpl();
     
     ru.setNameR(firstName);
     ru.setSurnameR(surname);
     ru.setFiscalCode(fiscalCode);
     ru.setEmail(email);
     ru.setPwd(pwd);
     
        try {
// if(((PollDataLayer)request.getAttribute("datalayer")).getResponsibleUserDAO().checkResponsible(ru)){

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
        return "Main Newspaper servlet";
    }// </editor-fold>

}
