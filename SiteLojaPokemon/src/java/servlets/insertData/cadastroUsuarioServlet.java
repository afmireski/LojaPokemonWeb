/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.insertData;

import daos.DAOEndereco;
import daos.DAOPessoa;
import daos.DAOUsuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Endereco;
import models.Pessoa;
import models.Usuario;

/**
 *
 * @author aacor
 */
@WebServlet(name = "cadastroUsuarioServlet", urlPatterns = {"/cadastroUsuarioServlet"})
public class cadastroUsuarioServlet extends HttpServlet {

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
            out.println("<title>Servlet cadastroUsuarioServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet cadastroUsuarioServlet at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession(false);

        if (session != null && session.getId().equals((String) session.getAttribute("cadID"))) {
            String txtEmail = request.getParameter("txtCadEmail");
            String txtSenha = request.getParameter("txtCadSenha");
            String txtConfSenha = request.getParameter("txtCadConfSenha");
            try {
                DAOUsuario daoUsuario = new DAOUsuario();

                if (daoUsuario.getUsuarioByEmail(txtEmail) == null) {
                    if (txtSenha.equals(txtConfSenha)) {
                        //INSERIR DADOS DE ENDEREÇO
                        Endereco endereco = (Endereco) session.getAttribute("end");

                        final DAOEndereco daoEndereco = new DAOEndereco();
                        daoEndereco.insert(endereco);

                        //INSERIR DADOS DE PESSOA
                        Pessoa pessoa = (Pessoa) session.getAttribute("pes");

                        DAOPessoa daoPessoa = new DAOPessoa();
                        daoPessoa.insert(pessoa);

                        //CRIAR USUÁRIO            
                        Usuario usuario = new Usuario();

                        usuario.setEmail(txtEmail);
                        usuario.setSenha(txtSenha);
                        usuario.setAtivo(true);
                        usuario.setDataCriacao(new Date());
                        usuario.setPessoaCPF(pessoa);

                        daoUsuario.insert(usuario);

                        session.invalidate();

                        response.sendRedirect("login.jsp");
                    } else {
                        session.setAttribute(
                                "cadUserError",
                                "A confirmação de senha não está de acordo com a senha informada!");

                        response.sendRedirect("pages/cadastro/cadastro_usuario.jsp");
                    }
                } else {
                    session.setAttribute(
                            "cadUserError",
                            "Esse endereço de e-mail já se encontra cadastrado no nosso sistema!");

                    response.sendRedirect("pages/cadastro/cadastro_usuario.jsp");

                }
            } catch (Exception e) {
                session.setAttribute(
                        "cadUserError",
                        "Houve uma falha ao tentarmos cadastrar o seu usuário!");

                response.sendRedirect("pages/cadastro/cadastro_usuario.jsp");
            }
        } else {
            response.sendRedirect("messages_pages/jump_step.html");
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
//        Não precisa alterar
        return "Short description";
    }// </editor-fold>

}
