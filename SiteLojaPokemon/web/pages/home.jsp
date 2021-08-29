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
        Usuario usuario = (Usuario) session.getAttribute("user");
        final String email = request.getParameter("txtEmail");
        String password = request.getParameter("txtSenha");
        if ((email == null || password == null) && usuario == null) { 
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
            if (usuario == null) {
                final DAOUsuario daoUsuario = new DAOUsuario(); 
                usuario = daoUsuario.getUsuarioByEmail(email);
            } else {
                password = usuario.getSenha();
            }
            if (usuario != null && usuario.getAtivo()) {
                if (usuario.getSenha().equals(password)) {
                    session.setAttribute("user", usuario);
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
                    <li><a href="perfil/perfil.jsp">Perfil</a></li>
                </ul>
            </div>
        </header>
        <% 
                } else { 
                    request.setAttribute("loginError", "Senha incorreta, tente novamente!");
                    RequestDispatcher rd = request.getRequestDispatcher("../login.jsp");

                    rd.include(request, response);
                }
            } else {                 
                request.setAttribute("loginError", "Usuário não encontrado, verifique o seu endereço de e-mail!");
                RequestDispatcher rd = request.getRequestDispatcher("../login.jsp");

                rd.include(request, response);                
            }
        }
        %>




    </body>

</html>