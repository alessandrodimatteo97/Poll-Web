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
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pollweb.data.model.ResponsibleUser;

/**
 *
 * @author davide
 */
public class AdminController extends PollBaseController {
    
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
            if (request.getAttribute("exception") != null) {
                (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
            } else {
                (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
            }
        }
  
   
    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        TemplateResult res = new TemplateResult(getServletContext());
        request.setAttribute("page_title", "Admin");
        request.setAttribute("users", ((PollDataLayer)request.getAttribute("datalayer")).getResponsibleUserDAO().getResponsibleUsersNotAccepted());
        res.activate("adminPanel.ftl.html", request, response); 
        }
    
    private void action_update(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        TemplateResult res = new TemplateResult(getServletContext());
        request.setAttribute("page_title", "Admin");
        String [] array = request.getParameterValues("checkbox");
        request.setAttribute("results", array);
        ((PollDataLayer)request.getAttribute("datalayer")).getResponsibleUserDAO().setAccepted(5);
        request.setAttribute("users", ((PollDataLayer)request.getAttribute("datalayer")).getResponsibleUserDAO().getResponsibleUsersNotAccepted());

        res.activate("adminPanel.ftl.html", request, response);
    }
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        int selectedResp;
       
        try {
            if(request.getParameterValues("checkbox") != null) {
                action_update(request, response);
            } else {
                action_default(request, response);
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
