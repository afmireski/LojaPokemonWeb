<%@page import="models.Usuario"%>
<%@page import="models.Cartao"%>
<%@page import="java.util.List"%>
<%@page import="daos.DAOCartao"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
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
        <link rel="stylesheet" href="../../styles/cartoes.css">

        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

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
                    <li><a href="../loja/loja.jsp">Loja</a></li>
                    <li><a href="cartoes.jsp" class="active-index">Cartões</a></li>
                    <li><a href="../perfil/perfil.jsp">Perfil</a></li>
                </ul>
            </div>
        </header>

        <!-- Mensagem de Erro -->
        <%
            String errorMessage = (String) session.getAttribute("cartaoError");

            if (errorMessage != null && !errorMessage.trim().isEmpty()) {
                session.removeAttribute("cartaoError");
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
        <main class="flex-row">
            <section class="flex-column uniforme-space grid-view start">
                <%
                 DAOCartao daoCartao = new DAOCartao();
                 List<Cartao> cartoes = daoCartao.listCartoesByUsuario(usuario.getId());
                 for (Cartao cartao : cartoes) {
                    %>
                      <div class="cartao-card flex-column">
                          <div class="flex-row start">
                              <h1>
                                  <%=(cartao.getNome())%>
                              </h1>
                            </div>   
                          <div class="flex-row start">
                               R$ <%=(cartao.getSaldo())%>
                          </div>               
                          <div class="flex-row end">
                              <button class="light" onclick="show_editar_cartao_modal('<%=(cartao.getId())%>', <%=(cartao.getSaldo())%>, '<%=(cartao.getNome())%>')"><span style="font-size: 16px;" class=" material-icons">edit</span></button>
                              <button class="delete-button" onclick="show_excluir_cartao_modal('<%=(cartao.getId())%>')"><span style="font-size: 16px;" class=" material-icons">delete</span></button>
                          </div>        
                      </div>  
                    <%
                     }
                %>
            </section>
            <section class="flex-column uniforme-space end">
                <form action="../../cadastroCartaoServlet" method="POST" class="flex-column cartao-box end">
                    <h1>Cadastrar Novo Cartão</h1>       
                    <div class="flex-row form-row">
                        <div class="flex-column">
                            <label for="txtNum">Número: </label>
                            <input type="text" name="txtNum" id="txtNum" minlength="16" maxlength="16" required>
                        </div>                        
                    </div>  
                    <div class="flex-row form-row">
                        <div class="flex-column">
                            <label for="txtSaldo">Saldo: </label>
                            <input type="text" name="txtSaldo" id="txtSaldo" required min="1">
                        </div>
                    </div>
                    <div class="flex-row form-row">
                        <div class="flex-column">
                            <label for="txtNome">Nome: </label>
                            <input type="text" name="txtNome" id="txtNome" required>
                        </div>                       
                    </div>  
                    <div class="flex-row form-row end">
                        <button type="submit" class="dark">Cadastrar</button>
                    </div>                    
                </form>
            </section>
            <form action="../../excluirCartaoServlet" method="POST">
                <input type="hidden" name="txtId" id="txtId">
                <div class="modal" id="excluir-cartao-modal">            
                    <div class="excluir-cartao-box">
                        <div class="flex-row end">
                            <span class="close-modal">&times;</span>
                        </div>
                        <div class="flex-row center">
                            <h1 class="excluir-title">Excluir cartão</h1>
                        </div>
                        <div class="flex-row center">
                            <p>Deseja mesmo excluir esse cartão?</p>
                        </div>
                        <div class="flex-row space-between">
                            <button type="button" class="no-delete-button" id="btn-cancel">
                                Cancelar
                            </button>
                            <button type="submit" class="delete-button" id="btn-excluir-cartao">
                                Excluir
                            </button>
                        </div>
                    </div>
                </div>
            </form>


            <div class="modal" id="editar-cartao-modal">
                <form action="../../updateCartaoServlet" method="POST" class="flex-column cartao-box">
                    <div class="flex-row end"><span class="close-modal">&times;</span></div>
                    <input type="hidden" name="txtNumEd" id="txtNumEd">
                    <div class="flex-row form-row">
                        <div class="flex-column">
                            <label for="txtSaldoEd">Saldo: </label>
                            <input type="text" name="txtSaldoEd" id="txtSaldoEd" required min="1">
                        </div>
                    </div>
                    <div class="flex-row form-row">
                        <div class="flex-column">
                            <label for="txtNomeEd">Nome: </label>
                            <input type="text" name="txtNomeEd" id="txtNomeEd" required>
                        </div>                       
                    </div>  
                    <div class="flex-row form-row end">
                        <button type="submit" class="dark">Atualizar</button>
                    </div>                    
                </form>
            </div>
            
        </main>    
        
        <script src="../../scripts/cartoes/gerenciarCartoes.js"></script>
     
        <%
                }
            }
        %>


    </body>

</html>