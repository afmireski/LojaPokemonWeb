/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.updateData;

import daos.DAOCartao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Cartao;
import models.Usuario;

/**
 *
 * @author aacor
 */
@WebServlet(name = "updateCartaoServlet", urlPatterns = {"/updateCartaoServlet"})
public class updateCartaoServlet extends HttpServlet {

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
            out.println("<title>Servlet updateCartaoServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet updateCartaoServlet at " + request.getContextPath() + "</h1>");
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
        if (session != null && session.getId().equals(session.getAttribute("sisID"))) {
            try {
                String txtNum = request.getParameter("txtNumEd");
                Double txtSaldo = Double.valueOf(request.getParameter("txtSaldoEd"));
                String txtNome = request.getParameter("txtNomeEd");

                DAOCartao daoCartao = new DAOCartao();
                Cartao cartao = daoCartao.get(txtNum);

                cartao.setNome(txtNome);
                cartao.setSaldo(txtSaldo);
                cartao.setDataCadastro(new Date());
                cartao.setUsuarioID((Usuario) session.getAttribute("user"));

                daoCartao.insert(cartao);
                response.sendRedirect("pages/cartoes/cartoes.jsp");

            } catch (Exception e) {
                session.setAttribute(
                        "cartaoError",
                        "Houve uma falha ao tentarmos atualizar o seu cartão!");

                response.sendRedirect("pages/cartoes/cartoes.jsp");
            }
        } else {
            response.sendRedirect("messages_pages/no_power.html");
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
