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
        <link rel="stylesheet" href="../../styles/perfil.css">

        <link href="https://fonts.googleapis.com/icon?family=Material+Icons"
              rel="stylesheet">

        <title>Loja Pokémon</title>
    </head>
    <body>

        <%
            Usuario usuario = (Usuario) session.getAttribute("user");

            if (usuario == null) {
        %>
        <div class="message-box">       
            <div class="flex-row center">
                <span class="material-icons error-icon">
                    privacy_tip
                </span>
            </div>
            <div class="flex-row center">
                Você não tem poder aqui! Saía de meus domínios ou sofrerá as consequências!
            </div>
        </div>
        <%
        } else {
        %>
        <header class="store-header">
            <div class="flex-row center">            
                <h1>Olá, <%=(usuario.getPessoaCPF().getNome())%>!!! Bem-vindo(a) ao seu perfil </h1>
            </div>
            <div class="flex-row start">
                <ul class="menu">
                    <li><a href="../home.jsp">Home</a></li>
                    <li><a href="">Loja</a></li>
                    <li><a href="">Pagamentos</a></li>
                    <li><a href="perfil.jsp" class="active-index">Perfil</a></li>
                </ul>
            </div>
        </header>

        <!-- Corpo -->
        <div class="perfil-box">
            <legend>Endereço</legend>
            <fieldset>
                <div class="flex-row form-row">
                    <div class="flex-column small-space">
                        <label for="pCEP">CEP</label>
                        <p id="pCEP">
                            <%=(usuario.getPessoaCPF().getEndereco().getEnderecoPK().getCep())%>
                        </p>
                    </div>
                    <div class="flex-column large-space">
                        <label for="pDesc">Descrição</label>
                        <p id="pDesc">
                            <%=(usuario.getPessoaCPF().getEndereco().getNome())%>                        
                        </p>
                    </div>
                </div>

                <div class="flex-row form-row">
                    <div class="flex-column large-space">
                        <label for="pCidade">Cidade</label>
                        <p id="pCidade">                        
                            <%=(usuario.getPessoaCPF().getEndereco().getCidade())%>
                        </p>
                    </div>
                    <div class="flex-column medium-space">
                        <label for="pEstado">Estado</label>                    
                        <p id="pEstado">                        
                            <%=(usuario.getPessoaCPF().getEndereco().getUfDescricao())%>
                        </p>
                    </div>
                    <div class="flex-column uniforme-space">
                        <label for="pNCasa">Nº Casa</label>
                        <p id="pNCasa">
                            <%=(usuario.getPessoaCPF().getEndereco().getEnderecoPK().getNCasa())%>
                        </p>
                    </div>
                </div>
                <div class="flex-row form-row end">                
                    <button type="button" class="dark" id="edit-end" name="edit-end">Editar</button>
                </div>
            </fieldset>

            <div class="tile" style=" color: red;" onclick="logout()">
                <span class="material-icons" style="margin-right: 5px;">
                    logout
                </span>
                <div style="display: flex; align-self: center;">Logout</div>
            </div>
        </div>

        <script>
            function logout() {
                document.location.href = "../../logoutServlet"
            }
        </script>
        <%
            }
        %>
    </body>
</html>
