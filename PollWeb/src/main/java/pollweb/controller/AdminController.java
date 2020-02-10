/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pollweb.controller;

import framework.data.DataException;
import framework.data.dao.PollDataLayer;
import framework.result.FailureResult;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import framework.security.SecurityLayer;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pollweb.data.model.ResponsibleUser;
import pollweb.data.model.Poll;

/**
 *
 * @author davide
 */
public class AdminController extends PollBaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (request.getAttribute("exception").equals("invalid_session")) {
                request.setAttribute("login_error", "Session errore");
                response.sendRedirect("Login");
            } else {
                (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
            }
        } catch (IOException ex) {
            request.setAttribute("exception", ex);
        }
    }
  
   
    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        try{
        TemplateResult res = new TemplateResult(getServletContext());
        request.setAttribute("page_title", "Admin");
        request.setAttribute("users", ((PollDataLayer)request.getAttribute("datalayer")).getResponsibleUserDAO().getResponsibleUsersNotAccepted());



        int userKey = ((PollDataLayer)request.getAttribute("datalayer")).getResponsibleUserDAO().getResponsibleUser((String)request.getSession(false).getAttribute("token")).getKey();


        request.setAttribute("polls", ((PollDataLayer)request.getAttribute("datalayer")).getPollDAO().getPollsByUserId(userKey));

        request.setAttribute("closed_polls", ((PollDataLayer)request.getAttribute("datalayer")).getPollDAO().getPollsAlreadyActivatedAndClosedByUserId(userKey));
        res.activate("adminPanel.ftl.html", request, response);

        } catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        } 
    }
    
    private void action_update(HttpServletRequest request, HttpServletResponse response) throws  ServletException, TemplateManagerException {
        TemplateResult res = new TemplateResult(getServletContext());
        request.setAttribute("page_title", "Admin");
        String [] array = request.getParameterValues("checkbox");

        request.setAttribute("results", array);
        try {
        for(int i = 0; i<array.length; i++) {
            
            ResponsibleUser rs = ((PollDataLayer)request.getAttribute("datalayer")).getResponsibleUserDAO().getResponsibleUser(parseInt(array[i]));
            
            request.setAttribute("userLogged", rs);
            Boolean result = ((PollDataLayer)request.getAttribute("datalayer")).getResponsibleUserDAO().setAccepted(rs); 
            ServletContext context = getServletContext( );
            if(result) {
                context.log("si");
            } else {
                context.log("no");
            }

         } 
        action_default(request, response);


        } catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        } catch (IOException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        } 

        
    
    }
    private void action_deactivate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("page_title", "Admin");

            ResponsibleUser rs = ((PollDataLayer)request.getAttribute("datalayer")).getResponsibleUserDAO().getResponsibleUser((String)request.getSession(false).getAttribute("token"));

            String id_poll = request.getParameter("poll_id");

            Poll pollRs = ((PollDataLayer) request.getAttribute("datalayer")).getPollDAO().getPollById(parseInt(id_poll));
            ((PollDataLayer) request.getAttribute("datalayer")).getPollDAO().setDeactivated(pollRs);
            request.setAttribute("polls", ((PollDataLayer)request.getAttribute("datalayer")).getPollDAO().getPollsByUserId(rs.getKey()));
            request.setAttribute("closed_polls", ((PollDataLayer)request.getAttribute("datalayer")).getPollDAO().getPollsAlreadyActivatedAndClosedByUserId(rs.getKey()));
            res.activate("mypolls_adminpanel.ftl.html", request, response, false);
        } catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }

    private void action_activate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("page_title", "Admin");

            ResponsibleUser rs = ((PollDataLayer)request.getAttribute("datalayer")).getResponsibleUserDAO().getResponsibleUser((String)request.getSession(false).getAttribute("token"));
            String id_poll = request.getParameter("poll_id");

            Poll pollRs = ((PollDataLayer) request.getAttribute("datalayer")).getPollDAO().getPollById(parseInt(id_poll));
            ((PollDataLayer) request.getAttribute("datalayer")).getPollDAO().setActivated(pollRs);
            request.setAttribute("polls", ((PollDataLayer)request.getAttribute("datalayer")).getPollDAO().getPollsByUserId(rs.getKey()));
            request.setAttribute("closed_polls", ((PollDataLayer)request.getAttribute("datalayer")).getPollDAO().getPollsAlreadyActivatedAndClosedByUserId(rs.getKey()));

            res.activate("mypolls_adminpanel.ftl.html", request, response, false);

        } catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
       
        try {
            if(request.getParameter("to_do") != null) {
                if(request.getParameter("to_do").equals("activate")) {
                    action_activate(request, response);
                }
                if (request.getParameter("to_do").equals("deactivate"))
                {
                    action_deactivate(request,response);
                }
            } else {

                if (request.getParameterValues("checkbox") != null) {
                    action_update(request, response);
                } else {
                    if (request.getSession(false) == null) {
                        request.setAttribute("exception", "invalid_session");
                        action_error(request, response); //CASO SESSIONE NON VALIDA
                    } else {
                        action_default(request, response);
                    }
                }
            }
                
            } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);

            } catch (TemplateManagerException ex) {
                request.setAttribute("exception", ex);
                action_error(request, response);

            } catch (DataException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }


}
