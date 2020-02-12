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

import pollweb.data.impl.PartecipantImpl;
import pollweb.data.impl.ResponsibleUserImpl;
import pollweb.data.model.Partecipant;
import pollweb.data.model.ResponsibleUser;

public class LoginReserved extends PollBaseController {

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        TemplateResult res = new TemplateResult(getServletContext());
        request.setAttribute("page_title", "Login");
        
        res.activate("login_poll.ftl.html", request, response);
    }

    private void action_login (HttpServletRequest request, HttpServletResponse response, int poll_id) throws IOException, TemplateManagerException, DataException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Partecipant partecipant = new PartecipantImpl();
        partecipant.setEmail(email);
        partecipant.setPwd(password);
        request.setAttribute("poll_id", poll_id);
        if(email.isEmpty() || password.isEmpty()) {
            request.setAttribute("login_error", "missing_data");
            action_error(request, response);
        }
        if(((PollDataLayer)request.getAttribute("datalayer")).getPartecipantDAO().loginPartecipant(partecipant, poll_id)){
            SecurityLayer.createSession(request, email, poll_id);
            response.sendRedirect("Poll");
        } else {
            request.setAttribute("login_error", "something_wrong");
            action_error(request, response);
        }
    }

    private void action_error(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("Home");
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException  {
        try {

            if(request.getParameter("email") != null && request.getParameter("password") != null){
                int poll_id = SecurityLayer.checkNumeric(request.getParameter("poll_id"));
                action_login(request, response, poll_id);
            }
            else {
                action_default(request, response);
            }

        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            try {
                action_error(request, response);
            } catch (IOException ex1) {
                Logger.getLogger(LoginReserved.class.getName()).log(Level.SEVERE, null, ex1);
            }

        } catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            try {
                action_error(request, response);
            } catch (IOException ex1) {
                Logger.getLogger(LoginReserved.class.getName()).log(Level.SEVERE, null, ex1);
            }

        } catch (DataException e) {
            e.printStackTrace();
        }
    }
}
