<%@page import="models.Novidades"%>
<%@page import="daos.DAONovidades"%>
<%@page import="models.Pedido"%>
<%@page import="models.Pokemon"%>
<%@page import="daos.DAOPedido"%>
<%@page import="daos.DAOPokemon"%>
<%@page import="java.util.List"%>
<%@page import="models.PedidoHasPokemon"%>
<%@page import="daos.DAOPedidoHasPokemon"%>
<%@page import="daos.DAOUsuario" %>
<%@page import="models.Usuario" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <link rel="stylesheet" href="../styles/style.css">
        <link rel="stylesheet" href="../styles/flexbox.css">
        <link rel="stylesheet" href="../styles/navigation.css">
        <link rel="stylesheet" href="../styles/components.css">
        <link rel="stylesheet" href="../styles/home.css">
        <link rel="stylesheet" href="../styles/messages.css">

        <link href="https://fonts.googleapis.com/icon?family=Material+Icons"
              rel="stylesheet">
              
        <link rel="shortcut icon" href="../favicons/loja-pokemon-fav.ico" type="image/x-icon">

        <title>Loja Pokémon</title>
    </head>

    <body>
        <%
            if (session == null || !session.getId().equals((String) session.getAttribute("sisID"))) {
//                Condição de invasão
                response.sendRedirect("../messages_pages/no_power.html");
            } else {
                Usuario usuario = (Usuario) session.getAttribute("user");
                if (usuario == null) {
                    response.sendRedirect("../messages_pages/no_power.html");
                } else {
        %>
        <header class="store-header">
            <div class="flex-row center">
                <h1>Olá, <%=(usuario.getPessoaCPF().getNome())%>!!! Bem-vindo(a) à Loja Pokémon </h1>
            </div>
            <div class="flex-row start">
                <ul class="menu">
                    <li><a href="home.jsp" class="active-index">Home</a></li>
                    <li><a href="loja/loja.jsp">Loja</a></li>
                    <li><a href="cartoes/cartoes.jsp">Cartões</a></li>
                    <li><a href="perfil/perfil.jsp">Perfil</a></li>
                </ul>
            </div>
        </header>

        <!-- Mensagem de Erro -->
        <%
            String errorMessage = (String) session.getAttribute("homeError");

            if (errorMessage != null && !errorMessage.trim().isEmpty()) {
                session.removeAttribute("homeError");
        %>
        <div class="flex-row center">
            <div class="error-message">
                <span class="closebtn" onclick="this.parentElement.style.display = 'none';">
                    &times;
                </span>
                <%=(errorMessage)%>
            </div>
        </div>
        <%
            }
        %>

        <%
            final DAONovidades daoNovidades = new DAONovidades();
            final Novidades novidade = daoNovidades.getUltimaNovidade();
            if (novidade != null) {
        %>
        <div class="flex-column novidades-box">
            <h1>
                <%=(novidade.getTitulo())%>
            </h1>
            <div class="flex-row start">
                <%=(novidade.getDescricao())%>
            </div>
        </div>
        <%

            }
        %>        

        <h1 style="margin-bottom: 20px; margin-left: 10px;">Seus Pokémons</h1>
        <%
            final DAOPedidoHasPokemon daoPhp = new DAOPedidoHasPokemon();
            List<PedidoHasPokemon> phps = daoPhp.listPedidoHasPokemonsByUser(usuario.getId());

            if (phps.isEmpty()) {
        %>
        <main class="flex-row center">
            <div class="message-box">
                <div class="flex-row center">
                    <span class="material-icons default-icon">
                        production_quantity_limits
                    </span>
                </div>
                <div class="flex-row center">
                    Você ainda não comprou nenhum Pokemon
                </div>
            </div>            
        </main>
        <%
        } else {
        %>  
        <main class="poke-grid grid-view start">
            <%
                DAOPokemon daoPokemom = new DAOPokemon();
                for (PedidoHasPokemon php : phps) {
                    Pokemon pokemon = daoPokemom.get(php.getPedidoHasPokemonPK().getPokemonID());
            %>
            <div class="pokemon-card" onclick="show_detalhes_pedido(
                 <%=(php.getPedidoHasPokemonPK().getPokemonID())%>,
                 <%=(php.getPedidoHasPokemonPK().getPedidoID())%>,
                            '<%=(pokemon.getNome())%>',
                            '<%=(pokemon.getTipoPokemonID().getDescricao())%>',
                 <%=(php.getQuantidade())%>,
                 <%=(php.getValorUnitario())%>,
                            '<%=(pokemon.getImagem())%>',
                            )">
                <div class="flex-row center">
                    <img class="pokemon-img-small" src="../<%=(pokemon.getImagem())%>" alt="<%=(pokemon.getNome())%>">
                </div>
                <div class="flex-column start">
                    <h1 class="pokemon-title" id="card-title"><%=(pokemon.getNome())%></h1>
                    <span><%=(pokemon.getTipoPokemonID().getDescricao())%></span>
                </div>
            </div>
            <%
                }

            %>
        </main>

        <!-- Compra MODAL -->
        <div class="modal"  id="pedido-form">
            <form action="../excluirPedidoHasPokemonServlet" method="post" class="poke-box" accept-charset="utf-8">
                <div class="flex-row end"><span class="close-modal">&times;</span></div>
                <div class="flex-row">
                    <div class="flex-column uniforme-space">
                        <img class="pokemon-img" id="poke-image" src="" alt="">
                    </div>
                    <div class="flex-column small-space">
                        <div class="flex-row start form-row">
                            <h1 id="poke-nome"></h1>
                        </div>
                        <div class="flex-row space-between form-row">
                            <span id="poke-tipo" class="poke-label"></span>
                            <span class="poke-label">Quantidade: <span id="poke-quantidade" class="poke-label"></span></span>
                        </div>
                        <div class="flex-row start form-row">
                            <span class="poke-price">Total: R$ <span id="poke-total"></span></span>
                        </div>
                        <div class="flex-row start form-row">
                            <span class="poke-price">Valor Unitário: R$ <span id="poke-preco"></span></span>
                        </div>
                        <div class="flex-row end form-row">                            
                            <button type="button" id="btn-excluir-pedido" class="delete-button" onclick="">Excluir</button>
                        </div>
                    </div>  
                    <input type="hidden" name="txtPokeID" id="txtPokeID">                  
                    <input type="hidden" name="txtPedidoID" id="txtPedidoID">                  
                </div>

                <!-- MODAL EXCLUIR CONTA -->
                <div class="modal" id="excluir-pedido-modal">            
                    <div class="excluir-pedido-box">
                        <div class="flex-row end">
                            <span class="close-modal">&times;</span>
                        </div>
                        <div class="flex-row center">
                            <h1 class="excluir-title">Excluir pedido</h1>
                        </div>
                        <div class="flex-row center">
                            <p>Deseja mesmo excluir esse pedido?</p>
                        </div>
                        <div class="flex-row space-between">
                            <button type="button" class="no-delete-button" id="btn-cancel">
                                Cancelar
                            </button>
                            <button type="submit" class="delete-button" id="btn-excluir-pedido">
                                Excluir
                            </button>
                        </div>
                    </div>
                </div>
            </form>
        </div>

        <script src="../scripts/home/gerenciarPedidos.js"></script>

        <%            }
        %>
        <%
                }
            }
        %>




    </body>

</html>