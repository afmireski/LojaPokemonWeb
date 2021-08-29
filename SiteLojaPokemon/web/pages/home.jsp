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

        <link href="https://fonts.googleapis.com/icon?family=Material+Icons"
        rel="stylesheet">

        <title>Loja Pokémon</title>
    </head>

    <body>
        <%
        final String email = request.getParameter("txtEmail");
        final String password = request.getParameter("txtSenha");
        if (email == null || password == null) { 
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
            final DAOUsuario daoUsuario = new DAOUsuario();
            final Usuario usuario = daoUsuario.getUsuarioByEmail(email);
            if (usuario != null && usuario.getAtivo()) {
                if (usuario.getSenha().equals(password)) {
            %>
        <header class="store-header">
            <div class="flex-row center">
                <h1>Olá, <%=(usuario.getPessoaCPF().getNome())%>!!! Bem-vindo(a) à Loja Pokémon </h1>
            </div>
            <div class="flex-row start">
                <ul class="menu">
                    <li><a href="home.html" class="active-index">Home</a></li>
                    <li><a href="">Loja</a></li>
                    <li><a href="">Pagamentos</a></li>
                    <li><a href="perfil/perfil.html">Perfil</a></li>
                </ul>
            </div>
        </header>
        <% 
                } else { 
        %>
                    <h2>Senha Incorreta></h2>
        <% 
                }
            } else { 
        %>
            <h2>Você não tem poder aqui. Saía de meus domínios ou sofrerá as consequências</h2>
        <% 
            }
        }
        %>




    </body>

</html>