/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.insertData;

import daos.DAOPessoa;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Endereco;
import models.Pessoa;

/**
 *
 * @author AFMireski
 */
@WebServlet(name = "prosseguePessoaServlet", urlPatterns = {"/prosseguePessoaServlet"})
public class prosseguePessoaServlet extends HttpServlet {

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
            out.println("<title>Servlet prosseguePessoaServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet prosseguePessoaServlet at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession(false);

        if (session != null && session.getId().equals((String) session.getAttribute("cadID"))) {
            try {
                Endereco endereco = (Endereco) session.getAttribute("end");

                final String txtCPF = request.getParameter("txtCPF");
                final String txtNome = request.getParameter("txtNome");
                final Date txtDataNascimento = new SimpleDateFormat("yyyy-MM-dd").parse((String) request.getParameter("txtDataNascimento"));
                final String txtSexo = request.getParameter("txtSexo");

                final DAOPessoa daoPessoa = new DAOPessoa();

                if (daoPessoa.get(txtCPF) == null) {
                    Pessoa pessoa = new Pessoa();
                    pessoa.setCpf(txtCPF);
                    pessoa.setNome(txtNome);
                    pessoa.setDataNascimento(txtDataNascimento);
                    pessoa.setSexo(txtSexo.substring(0, 1));
                    pessoa.setSexoDescricao(txtSexo);
                    pessoa.setEndereco(endereco);

                    session.setAttribute("pes", pessoa);

                    response.sendRedirect("pages/cadastro/cadastro_usuario.jsp");
                } else {
                    session.setAttribute(
                            "cadPesError",
                            "Esse CPF já se encontra cadastrado em nosso sistema, verifique os seus dados!");

                    response.sendRedirect("pages/cadastro/cadastro_pessoa.jsp");
                }
            } catch (Exception ex) {
                session.setAttribute(
                        "cadPesError",
                        "Houve uma falhar ao validarmos seus dados pessoais, verifique suas informações!");

                response.sendRedirect("pages/cadastro/cadastro_pessoa.jsp");
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
        return "Short description";
    }// </editor-fold>

}
