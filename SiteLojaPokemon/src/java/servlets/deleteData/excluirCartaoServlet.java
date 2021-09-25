/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.deleteData;

import daos.DAOCartao;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Cartao;

/**
 *
 * @author aacor
 */
@WebServlet(name = "excluirCartaoServlet", urlPatterns = {"/excluirCartaoServlet"})
public class excluirCartaoServlet extends HttpServlet {

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
            out.println("<title>Servlet excluirCartaoServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet excluirCartaoServlet at " + request.getContextPath() + "</h1>");
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

        if (session != null && session.getId().equals((String) session.getAttribute("sisID"))) {
            try {
                String cartaoId = request.getParameter("txtId"); 
                
                DAOCartao daoCartao = new DAOCartao();
                Cartao cartao = daoCartao.get(cartaoId);
                daoCartao.delete(cartao);
                
                response.sendRedirect("pages/cartoes/cartoes.jsp");
                
            } catch (Exception e) {
                session.setAttribute(
                        "cartaoError", 
                        "Ocorreu uma falha ao tentarmos excluir seu cartão, "
                                + "tente novamente mais tarde.");
                
                response.sendRedirect("pages/cartoes/cartoes.jsp");
            }
        } else {
            response.sendRedirect("messages_pages/unknown.html");
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
