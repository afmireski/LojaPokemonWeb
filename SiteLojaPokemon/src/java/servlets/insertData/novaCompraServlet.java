/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.insertData;

import daos.DAOCartao;
import daos.DAOPedido;
import daos.DAOPedidoHasPokemon;
import daos.DAOPokemon;
import daos.DAOPreco;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Cartao;
import models.Pedido;
import models.PedidoHasPokemon;
import models.PedidoHasPokemonPK;
import models.Pokemon;
import models.Usuario;

/**
 *
 * @author AFMireski
 */
@WebServlet(name = "novaCompraServlet", urlPatterns = {"/novaCompraServlet"})
public class novaCompraServlet extends HttpServlet {

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
            out.println("<title>Servlet novaCompraServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet novaCompraServlet at " + request.getContextPath() + "</h1>");
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
        final HttpSession session = request.getSession(false);

        if (session != null && session.getId().equals((String) session.getAttribute("sisID"))) {
            Usuario usuario = (Usuario) session.getAttribute("user");

            final DAOCartao daoCartao = new DAOCartao();
            final DAOPedido daoPedido = new DAOPedido();
            final DAOPedidoHasPokemon daoPedidoHasPokemon = new DAOPedidoHasPokemon();
            final DAOPokemon daoPokemon = new DAOPokemon();
            final DAOPreco daoPreco = new DAOPreco();

            try {
                final String txtCartao = request.getParameter("txtCartao");
                final Integer txtPokeID = Integer.valueOf(request.getParameter("txtPokeID"));
                final Integer txtQtdP = Integer.valueOf(request.getParameter("txtQtdP"));

                final Cartao cartao = daoCartao.get(txtCartao);

                final Pokemon pokemon = daoPokemon.get(txtPokeID);
                
                final double valor_unitario = daoPreco.getPrecoVigenteByPokemon(pokemon.getId()).getValor();
                final double total = txtQtdP * valor_unitario;

                if (total <= cartao.getSaldo()) {
                    Pedido pedido = new Pedido();

                    pedido.setDataPedido(new Date());
                    pedido.setUsuarioID(usuario);
                    pedido.setCartaoID(cartao);
                    
                    daoPedido.insert(pedido);  
                    
                    Integer pedidoID = daoPedido.getLastPedidoID();

                    final PedidoHasPokemon pedidoHasPokemon = new PedidoHasPokemon(
                            new PedidoHasPokemonPK(pedidoID, pokemon.getId()),
                            txtQtdP,
                            valor_unitario
                    );
                                           
                    daoPedidoHasPokemon.insert(pedidoHasPokemon);
                    
                    request.setAttribute("pedidoID", pedidoID);
                    
                    RequestDispatcher rd = request.getRequestDispatcher("emitirReciboServlet");
                    
                    rd.forward(request, response);
                } else {
                    session.setAttribute(
                            "storeError", 
                            "Aparentemente você não tem saldo suficiente disponível "
                                    + "no seu cartão para concretizar essa compra, "
                                    + "tente escolher um outro."
                    );
                    
                    response.sendRedirect("pages/loja/loja.jsp");
                }
            } catch (Exception ex) {
                session.setAttribute(
                            "storeError", 
                            "Ocorreu uma falha ao tentarmos finalizar sua compra, "
                                    + "tente novamente mais tarde."
                    );
                    
                    response.sendRedirect("pages/loja/loja.jsp");
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
