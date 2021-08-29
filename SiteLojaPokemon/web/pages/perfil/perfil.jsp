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

    <title>Loja Pokémon</title>
    
</head>
<body>
    
    <%
       final Usuario usuario = (Usuario) session.getAttribute("user");
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
</body>
</html>
