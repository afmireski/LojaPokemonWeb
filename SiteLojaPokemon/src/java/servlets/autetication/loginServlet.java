/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.autetication;

import daos.DAOUsuario;
import enums.LojaOrderBy;
import enums.PhpOrderBy;
import enums.SearchLojaFilter;
import enums.SearchPhpFilter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Usuario;

/**
 *
 * @author AFMireski
 */
@WebServlet(name = "loginServlet", urlPatterns = {"/loginServlet"})
public class loginServlet extends HttpServlet {

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
            out.println("<title>Servlet loginServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet loginServlet at " + request.getContextPath() + "</h1>");
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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        final String email = request.getParameter("txtEmail");
        final String password = request.getParameter("txtSenha");

        final DAOUsuario daoUsuario = new DAOUsuario();

        final Usuario usuario = daoUsuario.getUsuarioByEmail(email);

        if (usuario != null && usuario.getAtivo()) {
            if (usuario.getSenha().equals(password)) {
                HttpSession session = request.getSession();

                session.setAttribute("user", usuario);
                session.setAttribute("sisID", session.getId());
                session.setAttribute("lojaFilter", SearchLojaFilter.NONE);
                session.setAttribute("lojaOrder", LojaOrderBy.NONE);
                session.setAttribute("phpFilter", SearchPhpFilter.NONE);
                session.setAttribute("phpOrder", PhpOrderBy.NONE);

                response.sendRedirect("pages/home.jsp");
            } else {
                request.setAttribute("loginError", "Senha incorreta, tente novamente!");
                RequestDispatcher rd = request.getRequestDispatcher("login.jsp");

                rd.forward(request, response);
            }
        } else {
            request.setAttribute("loginError", "Usu??rio n??o encontrado, verifique o seu endere??o de e-mail!");
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");

            rd.forward(request, response);
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
