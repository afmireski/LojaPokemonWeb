/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.utilities;

import helpers.Recibo;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author AFMireski
 */
@WebServlet(name = "emitirReciboServlet", urlPatterns = {"/emitirReciboServlet"})
public class emitirReciboServlet extends HttpServlet {

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet emitirReciboServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet emitirReciboServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("messages_pages/unknown.html");
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
        final HttpSession session = request.getSession(false);

        if (session != null && session.getId().equals((String) session.getAttribute("sisID"))) {
            try {
                Integer pedidoID = (Integer) request.getAttribute("pedidoID");

                Recibo.emitirRecibo(pedidoID, getServletContext().getRealPath("/recibos"));

                response.sendRedirect("pages/home.jsp");
            } catch (FileNotFoundException ex) {
                session.setAttribute(
                        "reciboError",
                        "Sua compra foi efetivada, porém houve um problema ao gerarmos seu recibo,"
                        + " por favor, entre em contato com nosso suporte."
                );

                response.sendRedirect("pages/home.jsp");
            } catch (Exception ex) {
                session.setAttribute(
                        "reciboError",
                        "Sua compra foi efetivada, porém houve um problema ao enviarmos seu recibo,"
                        + " por favor, entre em contato com nosso suporte.\n"
                        + ex.getMessage()
                );

                response.sendRedirect("pages/home.jsp");
            }

        } else {
            response.sendRedirect("messages_pages/unknown.html");
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
