<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <link rel="stylesheet" href="styles/style.css">
    <link rel="stylesheet" href="styles/flexbox.css">
    <link rel="stylesheet" href="styles/components.css">
    <link rel="shortcut icon" href="favicons/loja-pokemon-fav.ico" type="image/x-icon">

    <title>Loja Pokémon</title>

    <style>
        body {
            text-align: center;
        }
    </style>

    <script>
        function disableBack() {
            window.history.forward()
        }
        setTimeout(disableBack(), 0);
        window.onunload = function () {
            null         
        }
    </script>
</head>

<body class="out-sys">
    <h1>Bem-vindo à Loja Pokémon</h1>
    
    <%
        String errorMessage = (String) request.getAttribute("loginError");
        
        if (errorMessage != null && !errorMessage.trim().isEmpty()) {
            %>
            <div class="flex-row center">
                <div class="error-message">
                    <span class="closebtn" onclick="this.parentElement.style.display='none';">
                        &times;
                    </span>
                    <%=(errorMessage)%>
                </div>
            </div>
            <%
        }
    %>

    <form action="loginServlet" method="post" class="login-box">        
        <div class="flex-column start form-row">
            <label for="txtEmail">E-mail</label>
            <input type="email" name="txtEmail" id="txtEmail" class="giant">
        </div>
        <div class="flex-column start form-row">
            <label for="txtSenha">Senha</label>
            <input type="password" name="txtSenha" id="txtSenha" class="giant">
        </div>
        <div class="flex-row space-between">
            <button type="button" class="light" id="cad" name="cad">Cadastrar-se</button>
            <button type="submit" class="dark" id="log" name="log">Login</button>
        </div>
    </form>

    <script src="scripts/login/login.js"></script>    
</body>

</html>
