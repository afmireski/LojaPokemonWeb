<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">


        <link rel="stylesheet" href="../../styles/style.css">
        <link rel="stylesheet" href="../../styles/flexbox.css">
        <link rel="stylesheet" href="../../styles/components.css">


        <title>Loja Pokémon</title>

        <style>
            body {
                text-align: center;
            }
        </style>
        
    </head>

    <body class="out-sys">
        <h1>Bem-vindo à Loja Pokémon</h1>

        <%
            String errorMessage = (String) session.getAttribute("cadUserError");

            if (errorMessage != null && !errorMessage.trim().isEmpty()) {
                session.removeAttribute("cadUserError");
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

        <form action="../../cadastroUsuarioServlet" method="post" class="login-box">        
            <div class="flex-column start form-row">
                <label for="txtCadEmail">E-mail</label>
                <input type="email" name="txtCadEmail" id="txtCadEmail" class="giant" required>
            </div>
            <div class="flex-column start form-row">
                <label for="txtCadSenha">Senha</label>
                <input type="password" name="txtCadSenha" id="txtCadSenha" class="giant" required>
            </div>
            <div class="flex-column start form-row">
                <label for="txtCadConfSenha">Confirmar senha</label>
                <input type="password" name="txtCadConfSenha" id="txtCadConfSenha" class="giant" required>
            </div>
            <div class="flex-row space-between">
                <button type="submit" class="light" id="cad_user" name="cad_user">Cadastrar-se</button>
            </div>
        </form>     
    </body>

</html>
