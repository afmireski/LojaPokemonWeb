<%@page import="models.EnderecoPK"%>
<%@page import="models.Endereco"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Loja Pokémon</title>

        <link rel="stylesheet" href="../../styles/style.css">
        <link rel="stylesheet" href="../../styles/flexbox.css">
        <link rel="stylesheet" href="../../styles/components.css">

        <link rel="shortcut icon" href="../../favicons/loja-pokemon-fav.ico" type="image/x-icon">

    </head>
    <body>
        <%
            if (session == null || !session.getId().equals((String) session.getAttribute("cadID"))) {
                response.sendRedirect("../../messages_pages/jump_step.html");
            } else {
                
            
                String errorMessage = (String) session.getAttribute("cadPesError");
                if (errorMessage != null && !errorMessage.trim().isEmpty()) {
                    session.removeAttribute("cadPesError");
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
        <form action="../../prosseguePessoaServlet" method="post" class="form-box">
            <legend>Dados Pessoais</legend>
            <fieldset>
                <div class="flex-row form-row">
                    <div class="flex-column small-space">
                        <label for="txtCPF">CPF</label>
                        <input type="text" class="medium" id="txtCPF" name="txtCPF" maxlength="11" minlength="11" required>
                    </div>
                    <div class="flex-column large-space">
                        <label for="txtNome">Nome</label>
                        <input type="text" class="large" id="txtNome" name="txtNome" required>
                    </div>
                </div>
    
                <div class="flex-row form-row">
                    <div class="flex-column large-space">
                        <label for="txtDataNascimento">Data de Nascimento</label>
                        <input type="date" class="medium" id="txtDataNascimento" name="txtDataNascimento" required>
                    </div>
                    <div class="flex-column medium-space">
                        <label for="txtSexo">Sexo</label>
                        <select name="txtSexo" id="txtSexo" class="medium">
                            <option value="Masculino">Masculino</option>
                            <option value="Feminino">Feminino</option>                           
                        </select>
                    </div>                   
                </div>
                <div class="flex-row form-row end">                
                    <button type="submit" class="dark small-font" id="pro-pes" name="pro-pes">Prosseguir</button>
                </div>
            </fieldset>        
        </form>
        <%
            }
        %>

    </body>
</html>