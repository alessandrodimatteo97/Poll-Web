/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pollweb.controller;

import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author achissimo
 */
public class NewServletProva extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    /*
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        System.out.println(request.getParameter("s"));
        if(request.getParameter("si")!=null) FrigoPieno(response);
        if(request.getParameter("no")!=null) FrigoVuoto(response);

            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. *
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet NewServletProva</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Servlet NewServletProva at " + request.getContextPath() + "</h1>");
                out.println("<form method=\"get\" action=\"NewServletProva\">");
            out.println("<p>IL FRIGORIFERO è PIENO");
            out.println("<input type=\"submit\" name=\"si\" value=\"SI!\"/>");
            out.println("<input type=\"submit\" name=\"no\" value=\"NO!\"/>");

            out.println("</p>");
            out.println("</form>");
                out.println("</body>");
                out.println("</html>");
            }
         
    } 
*/
    // proviamo l'utilizzo di freemarker...
        protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            if(request.getParameter("no")!=null) FrigoPieno(response);
            if(request.getParameter("si")!=null) FrigoVuoto(response);

        try {
            Map data = new HashMap();
                        
            //disabilitiamo il template di outline (che è specificato tra i context parameters)
            //disable the outline template (otherwise the TemplateResult uses the template specified in the context parameters)
            data.put("outline_tpl", "");
            
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("index.ftl.html", data, response);
        } catch (TemplateManagerException ex) {
            throw new ServletException(ex);
        }
    }
        
        
        
    protected void FrigoPieno(HttpServletResponse response) throws IOException{
                try (PrintWriter out = response.getWriter()) {
                             out.println("<h1>ALLORA PUOI METTERE ALTRA ROBA</h1>");       
   
                }
    }
     private void FrigoVuoto(HttpServletResponse response) throws IOException {
       try (PrintWriter out = response.getWriter()) {
           
                             out.println("<h1>ALLORA NON PUOI METTERE NIENT'ALTRO</h1>");       
   
                }
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
    @Override // il primo metodo che viene chiamato è doGet oppure doPost,
// dopodiché esso chiama il metodo process request, che ha a funzione di smistare verso gli altri metodo a seconda di quello che gli viene passato
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.print("ciao");
        processRequest(request, response);
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
        
        processRequest(request, response);
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
