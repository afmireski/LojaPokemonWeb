/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.updateData;

import daos.DAOEndereco;
import daos.DAOUsuario;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Endereco;
import models.Usuario;

/**
 *
 * @author AFMireski
 */
@WebServlet(name = "updateEnderecoServlet", urlPatterns = {"/updateEnderecoServlet"})
public class updateEnderecoServlet extends HttpServlet {

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
            out.println("<title>Servlet updateEnderecoServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet updateEnderecoServlet at " + request.getContextPath() + "</h1>");
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

        final String[] estados = {"ACRE", "ALAGOAS", "AMAPÁ", "AMAZONAS", "BAHIA", "CEARÁ",
            "ESPÍRITO SANTO", "GOÍAS", "MARANHÃO", "MATO GROSSO", "MATO GROSSO DO SUL",
            "MINAS GERAIS", "PARÁ", "PARAÍBA", "PARANÁ", "PERNAMBUCO", "PIAUÍ", "RIO DE JANEIRO",
            "RIO GRANDE DO NORTE", "RIO GRANDE DO SUL", "RONDÔNIA", "RORAIMA", "SANTA CATARINA",
            "SÃO PAULO", "SERGIPE", "TOCANTINS", "DISTRITO FEDERAL"};

        final String enderecoDescricao = request.getParameter("txtDesc");
        final String enderecoCidade = request.getParameter("txtCidade");
        final Integer enderecoEstado = Integer.valueOf(request.getParameter("txtEstado"));

        final HttpSession session = request.getSession();

        final Usuario usuario = (Usuario) session.getAttribute("user");

        final Endereco newEndereco = new Endereco(
                usuario.getPessoaCPF().getEndereco().getEnderecoPK(),
                enderecoDescricao,
                enderecoCidade,
                enderecoEstado,
                estados[enderecoEstado - 1]);

        final DAOEndereco daoEndereco = new DAOEndereco();

        try {
            daoEndereco.update(newEndereco);

            DAOUsuario daoUsuario = new DAOUsuario();

            session.setAttribute("user", daoUsuario.get(usuario.getId()));

            response.sendRedirect("pages/home.jsp");

        } catch (Exception e) {
            session.setAttribute(
                    "updateError",
                    "Ocorreu uma falha ao tentar atualizar seu endereço, "
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
