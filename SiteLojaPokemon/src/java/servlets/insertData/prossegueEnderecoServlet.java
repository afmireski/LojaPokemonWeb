/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.insertData;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Endereco;
import models.EnderecoPK;

/**
 *
 * @author AFMireski
 */
@WebServlet(name = "prossegueEnderecoServlet", urlPatterns = {"/prossegueEnderecoServlet"})
public class prossegueEnderecoServlet extends HttpServlet {

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
            out.println("<title>Servlet prossegueEnderecoServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet prossegueEnderecoServlet at " + request.getContextPath() + "</h1>");
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
        try {
            final String[] estados = {"ACRE", "ALAGOAS", "AMAPÁ", "AMAZONAS", "BAHIA", "CEARÁ",
                "ESPÍRITO SANTO", "GOÍAS", "MARANHÃO", "MATO GROSSO", "MATO GROSSO DO SUL",
                "MINAS GERAIS", "PARÁ", "PARAÍBA", "PARANÁ", "PERNAMBUCO", "PIAUÍ", "RIO DE JANEIRO",
                "RIO GRANDE DO NORTE", "RIO GRANDE DO SUL", "RONDÔNIA", "RORAIMA", "SANTA CATARINA",
                "SÃO PAULO", "SERGIPE", "TOCANTINS", "DISTRITO FEDERAL"};

            final String txtCEP = request.getParameter("txtCEP");
            final int txtNCasa = Integer.valueOf(request.getParameter("txtNCasa"));
            final String txtDesc = request.getParameter("txtDesc");
            final int txtEstado = Integer.valueOf(request.getParameter("txtEstado"));
            final String txtCidade = request.getParameter("txtCidade");

            Endereco endereco = new Endereco();
            endereco.setEnderecoPK(new EnderecoPK(txtCEP, txtNCasa));
            endereco.setCidade(txtCidade);
            endereco.setUf(txtEstado);
            endereco.setUfDescricao(estados[txtEstado - 1]);
            endereco.setNome(txtDesc);

            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(600);
            session.setAttribute("end", endereco);
            session.setAttribute("cadID", session.getAttribute("cadID"));

            response.sendRedirect("pages/cadastro/cadastro_pessoa.jsp");
        } catch (Exception ex) {
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
