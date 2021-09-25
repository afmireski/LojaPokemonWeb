/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.filter;

import enums.LojaOrderBy;
import enums.SearchLojaFilter;
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
@WebServlet(name = "lojaFilterServlet", urlPatterns = {"/lojaFilterServlet"})
public class lojaFilterServlet extends HttpServlet {

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

                SearchLojaFilter filter = search == null || search.trim().isEmpty()
                        ? SearchLojaFilter.NONE : SearchLojaFilter.HAS_SEARCH;
                LojaOrderBy orderBy;

                switch (order) {
                    case 1:
                        orderBy = LojaOrderBy.MAIOR_ESTOQUE;
                        break;
                    case 2:
                        orderBy = LojaOrderBy.MENOR_ESTOQUE;
                        break;
                    case 3:
                        orderBy = LojaOrderBy.TIPO_POKEMON;
                        break;
                    case 4:
                        orderBy = LojaOrderBy.A_Z;
                        break;
                    case 5:
                        orderBy = LojaOrderBy.Z_A;
                        break;
                    case 0:
                    default:
                        orderBy = LojaOrderBy.NONE;
                }

                session.setAttribute("lojaFilter", filter);
                session.setAttribute("lojaOrder", orderBy);
                session.setAttribute("lojaSearch", search);

                response.sendRedirect("pages/loja/loja.jsp");
            } catch (Exception e) {
                session.setAttribute(
                        "storeError",
                        "Houve uma falha ao tentar efetuar a sua pesquisa, tente"
                                + " novamente mais tarde."
                );

                response.sendRedirect("pages/loja/loja.jsp");
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
