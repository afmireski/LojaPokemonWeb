/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.updateData;

import daos.DAOUsuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
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
@WebServlet(name = "updateUsuarioServlet", urlPatterns = {"/updateUsuarioServlet"})
public class updateUsuarioServlet extends HttpServlet {

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
            out.println("<title>Servlet updateUsuarioServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet updateUsuarioServlet at " + request.getContextPath() + "</h1>");
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
        final String txtEmail = request.getParameter("txtEmail");
        final String txtVSenha = request.getParameter("txtVSenha");
        final String txtNovaSenha = request.getParameter("txtNovaSenha");
        final String txtConfirmaNovaSenha = request.getParameter("txtConfirmaNovaSenha");

        final HttpSession session = request.getSession();

        final Usuario usuario = (Usuario) session.getAttribute("user");

        if (txtVSenha.equals(usuario.getSenha())) {

            if (txtNovaSenha.equals(txtConfirmaNovaSenha) || (txtNovaSenha.isEmpty() && txtConfirmaNovaSenha.isEmpty())) {
                final Usuario newUsuario = new Usuario();

                newUsuario.setId(usuario.getId());
                newUsuario.setEmail(txtEmail);
                newUsuario.setAtivo(true);
                newUsuario.setDataCriacao(new Date());
                newUsuario.setSenha(txtNovaSenha.isEmpty() ? usuario.getSenha() : txtNovaSenha);
                newUsuario.setPessoaCPF(usuario.getPessoaCPF());

                final DAOUsuario daoUsuario = new DAOUsuario();

                try {
                    daoUsuario.update(newUsuario);

                    session.setAttribute("user", daoUsuario.get(newUsuario.getId()));

                    response.sendRedirect("pages/home.jsp");

                } catch (Exception e) {
                    session.setAttribute(
                            "updateError",
                            "Ocorreu uma falha ao tentar atualizar seu usuário, "
                            + "verifique os dados inseridos e tente novamente.");

                    response.sendRedirect("pages/perfil/perfil.jsp");
                }
            } else {
                session.setAttribute(
                        "updateError",
                        "A confirmação de senha não está de acordo com a nova senha informada.");

                response.sendRedirect("pages/perfil/perfil.jsp");
            }

        } else {
            session.setAttribute(
                    "updateError",
                    "A senha informada para a verificação está incorreta.");

            response.sendRedirect("pages/perfil/perfil.jsp");
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
