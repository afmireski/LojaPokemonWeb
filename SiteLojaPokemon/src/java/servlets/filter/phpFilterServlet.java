/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.filter;

import enums.LojaOrderBy;
import enums.PhpOrderBy;
import enums.SearchLojaFilter;
import enums.SearchPhpFilter;
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
@WebServlet(name = "phpFilterServlet", urlPatterns = {"/phpFilterServlet"})
public class phpFilterServlet extends HttpServlet {

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
            out.println("<title>Servlet lojaFilterServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet lojaFilterServlet at " + request.getContextPath() + "</h1>");
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
        final HttpSession session = request.getSession(false);

        if (session != null && session.getId().equals((String) session.getAttribute("sisID"))) {
            try {
                final String search = (String) request.getParameter("txtSearch");
                final Integer order = Integer.valueOf(request.getParameter("txtOrder"));

                SearchPhpFilter filter = search == null || search.trim().isEmpty()
                        ? SearchPhpFilter.NONE : SearchPhpFilter.HAS_SEARCH;
                PhpOrderBy orderBy;

                switch (order) {
                    case 1:
                        orderBy = PhpOrderBy.MAIOR_QUANTIDADE;
                        break;
                    case 2:
                        orderBy = PhpOrderBy.MENOR_QUANTIDADE;
                        break;
                    case 3:
                        orderBy = PhpOrderBy.MAIOR_V_U;
                        break;
                    case 4:
                        orderBy = PhpOrderBy.MENOR_V_U;
                        break;
                    case 5:
                        orderBy = PhpOrderBy.TIPO_POKEMON;
                        break;
                    case 6:
                        orderBy = PhpOrderBy.A_Z;
                        break;
                    case 7:
                        orderBy = PhpOrderBy.Z_A;
                        break;
                    case 8:
                        orderBy = PhpOrderBy.PEDIDOS_RECENTES;
                        break;
                    case 9:
                        orderBy = PhpOrderBy.PEDIDOS_ANTIGOS;
                        break;
                    case 0:
                    default:
                        orderBy = PhpOrderBy.NONE;
                }

                session.setAttribute("phpFilter", filter);
                session.setAttribute("phpOrder", orderBy);
                session.setAttribute("phpSearch", search);

                response.sendRedirect("pages/home.jsp");
            } catch (Exception e) {
                session.setAttribute(
                        "homeError",
                        "Houve uma falha ao tentar efetuar a sua pesquisa, tente"
                        + " novamente mais tarde."
                );

                response.sendRedirect("pages/home.jsp");
            }

        } else {
            response.sendRedirect("messages_pages/unknown.html");
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
        response.sendRedirect("messages_pages/unknown.html");
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
