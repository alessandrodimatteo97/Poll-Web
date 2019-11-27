/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pollweb.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import framework.result.DataModelFiller;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
//import org.json.JSONObject;
import org.json.JSONObject;
import pollweb.data.impl.*;
/**
 *
 * @author achissimo
 */
import pollweb.data.model.Answer;
public class LoginPage extends HttpServlet {
  JSONObject json = new JSONObject();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, TemplateManagerException {
            Map data = new HashMap();
          //  AnswerImpl a = new AnswerImpl();
        //  AnswerImpl a = new AnswerImpl();
        //    a.setTextA(new JSONObject("ciao:ciao"));
         //   System.out.println(a.getTextA().toString());
            //disabilitiamo il template di outline (che è specificato tra i context parameters)
            //disable the outline template (otherwise the TemplateResult uses the template specified in the context parameters)
            json.append("ciao", " JSONObject json = new JSONObject();");
         // a.setTextA(new JSONObject(data));
           //   a.getTextA().toString();
           // JSONObject j = new JSONObject();
           // j.put("name", "jon doe");
          //  System.out.println(j.toString());
          
            PollImpl p = new PollImpl();
            p.setActivated(true);
            System.out.print("ciao");
            ServletContext context = getServletContext( );
            context.log(json.toString());
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("LoginPage.ftl.html", request, response);   
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (TemplateManagerException ex) {
            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (TemplateManagerException ex) {
            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
