<%@page import="models.Cartao"%>
<%@page import="daos.DAOCartao"%>
<%@page import="daos.DAOPreco"%>
<%@page import="java.util.List"%>
<%@page import="models.Pokemon"%>
<%@page import="daos.DAOPokemon"%>
<%@page import="models.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <link rel="stylesheet" href="../../styles/style.css">
        <link rel="stylesheet" href="../../styles/flexbox.css">
        <link rel="stylesheet" href="../../styles/navigation.css">
        <link rel="stylesheet" href="../../styles/components.css">
        <link rel="stylesheet" href="../../styles/messages.css">
        <link rel="stylesheet" href="../../styles/loja.css">

        <link href="https://fonts.googleapis.com/icon?family=Material+Icons"
              rel="stylesheet">

        <link rel="shortcut icon" href="../../favicons/loja-pokemon-fav.ico" type="image/x-icon">

        <title>Loja Pokémon</title>
    </head>
    <body>
        <%
            if (session == null || !session.getId().equals((String) session.getAttribute("sisID"))) {
                //                Condição de invasão
                response.sendRedirect("../../messages_pages/no_power.html");
            } else {

                Usuario usuario = (Usuario) session.getAttribute("user");
                if (usuario == null) {
                    response.sendRedirect("../../messages_pages/no_power.html");
                } else {
        %>
        <header class="store-header">
            <div class="flex-row center">
                <h1>Olá, <%=(usuario.getPessoaCPF().getNome())%>!!! Bem-vindo(a) à Loja Pokémon </h1>
            </div>
            <div class="flex-row start">
                <ul class="menu">
                    <li><a href="../home.jsp">Home</a></li>
                    <li><a href="loja.jsp" class="active-index">Loja</a></li>
                    <li><a href="">Pagamentos</a></li>
                    <li><a href="../perfil/perfil.jsp">Perfil</a></li>
                </ul>
            </div>
        </header>
        <!-- Mensagem de Erro -->
        <%
            String errorMessage = (String) session.getAttribute("storeError");

            if (errorMessage != null && !errorMessage.trim().isEmpty()) {
                session.removeAttribute("storeError");
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
        <h1 style="margin-bottom: 20px; margin-left: 10px;">Escolha seu Pokémon</h1>

        <%
            final DAOPokemon daoPokemon = new DAOPokemon();

            List<Pokemon> pokemons = daoPokemon.listAllPokemonsWithEstoque();

            if (pokemons.isEmpty()) {
        %>
        <main class="flex-row center">
            <div class="message-box">
                <div class="flex-row center">
                    <span class="material-icons default-icon">
                        production_quantity_limits
                    </span>
                </div>
                <div class="flex-row center">
                    Infelizmente ainda não temos nenhum Pokémon disponível em estoque
                </div>
            </div>
        </main>
        <%
        } else {
            final DAOPreco daoPreco = new DAOPreco();
        %>

        <main class="poke-grid grid-view start"> 
            <%
                for (Pokemon pokemon : pokemons) {
                    double pokePrice = daoPreco.getPrecoVigenteByPokemon(pokemon.getId()).getValor();
            %>            
            <div class="pokemon-card" onclick="show_pokemon(
                 <%=(pokemon.getId())%>,
                 '<%=(pokemon.getNome())%>',
                 '<%=(pokemon.getTipoPokemonID().getDescricao())%>',
                 <%=(pokemon.getEstoque())%>,
                 <%=(pokePrice)%>,
                            '<%=(pokemon.getImagem())%>'
                            )">
                <div class="flex-row center">
                    <img class="pokemon-img-small" src="../..<%=(pokemon.getImagem())%>" alt="<%=(pokemon.getNome())%>">
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
        <div class="modal"  id="poke-form">
            <form action="" method="post" class="poke-box" accept-charset="utf-8">
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
                            <span id="poke-estoque" class="poke-label"></span>
                        </div>
                        <div class="flex-row start form-row">
                            <span class="poke-price" id="poke-preco"></span>
                        </div>
                        <div class="flex-row space-between form-row">
                            <div class="flex-column">                            
                                <label for="txtQtd" class="poke-label">Quantidade</label>
                                <input type="number" name="txtQtd" id="txtQtd" min="1" max="" required>
                            </div>
                            <button type="button" id="btn-comprar" class="light" onclick="show_pagar()">Comprar</button>
                        </div>
                    </div>                    
                </div>
            </form>
        </div>

        <!-- Pagar MODAL -->
        <div class="modal"  id="pagar-form">
            <form action="../../novaCompraServlet" method="post" class="pagar-box" accept-charset="utf-8">
                <div class="flex-row end"><span class="close-modal">&times;</span></div>
                <div class="flex-row start form-row poke-price">
                    Total: R$ <span id="total-price" class=""></span>                 
                </div>
                <div class="flex-row start">
                    <div class="flex-column">                        
                        <span class="poke-label">Escolha seu cartão</span>
                        <%
                            final DAOCartao daoCartao = new DAOCartao();
                            final List<Cartao> cartoes = daoCartao.listCartoesByUsuario(usuario.getId());

                            if (cartoes.isEmpty()) {
                        %>
                        <div class="flex-row center">
                            <div class="error-message">
                                <span class="closebtn" onclick="this.parentElement.style.display = 'none';">
                                    &times;
                                </span>
                                Você não tem nenhum cartão cadastrado.
                            </div>
                        </div>
                        <%
                            } else {
                        %>
                        <select name="txtCartao" id="txtCartao">
                            <%
                                for (Cartao c : cartoes) {
                            %>
                                <option value="<%=(c.getId())%>"> <%=(c.getNome())%> - R$ <%=(c.getSaldo())%> </option>
                            <%
                                }
                            %>
                        </select>
                        <%
                            }
                        %>

                    </div>
                </div>
                <div class="flex-row end">
                    <button type="submit" class="light" >Pagar</button>
                </div>
                <input type="hidden" name="txtPokeID" id="txtPokeID">
                <input type="hidden" name="txtQtdP" id="txtQtdP">
            </form>
        </div>

        <script src="../../scripts/loja/comprar.js"></script>

        <%
            }
        %>


        <%
                }
            }
        %>
    </body>
</html>