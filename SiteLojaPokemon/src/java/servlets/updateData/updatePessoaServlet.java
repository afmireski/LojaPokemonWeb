/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.updateData;

import daos.DAOPessoa;
import daos.DAOUsuario;
import helpers.Capitalize;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Pessoa;
import models.Usuario;

/**
 *
 * @author AFMireski
 */
@WebServlet(name = "updatePessoaServlet", urlPatterns = {"/updatePessoaServlet"})
public class updatePessoaServlet extends HttpServlet {

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
            out.println("<title>Servlet updatePessoaServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet updatePessoaServlet at " + request.getContextPath() + "</h1>");
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
        
        final HttpSession session = request.getSession();
        try {

            final String txtNome = request.getParameter("txtNome");
            final String txtSexo = request.getParameter("txtSexo");
            final Date txtDataNascimento = new SimpleDateFormat("yyyy-MM-dd").parse((String) request.getParameter("txtDataNascimento"));            

            final Usuario usuario = (Usuario) session.getAttribute("user");

            final Capitalize cap = new Capitalize();

            Pessoa newPessoa = new Pessoa(
                    usuario.getPessoaCPF().getCpf(),
                    cap.removerAcentos(txtNome),
                    txtDataNascimento,
                    txtSexo.substring(0, 1),
                    txtSexo
            );

            newPessoa.setEndereco(usuario.getPessoaCPF().getEndereco());

            final DAOPessoa daoPessoa = new DAOPessoa();

            daoPessoa.update(newPessoa);

            DAOUsuario daoUsuario = new DAOUsuario();

            session.setAttribute("user", daoUsuario.get(usuario.getId()));

            response.sendRedirect("pages/home.jsp");
        } catch (Exception e) {
            session.setAttribute(
                    "updateError",
                    "Ocorreu uma falha ao tentar atualizar seus dados pessoais, "
                    + "verifique os dados inseridos e tente novamente.");

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
