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

    </head>
    <body>
        <%--
           String txtCEP = request.getParameter("txtCEP");
           int txtNCasa = Integer.valueOf(request.getParameter("txtNCasa")); 
           
           Endereco endereco = new Endereco();
           endereco.setEnderecoPK(new EnderecoPK(txtCEP, txtNCasa));
        --%> 
        <form action="cadastroUsuario.jsp" method="post" class="form-box">
            <legend>Dados Pessoais</legend>
            <fieldset>
                <div class="flex-row form-row">
                    <div class="flex-column small-space">
                        <label for="txtCPF">CPF</label>
                        <input type="text" class="medium" id="txtCPF" name="txtCPF" maxlength="11">
                    </div>
                    <div class="flex-column large-space">
                        <label for="txtDesc">Nome</label>
                        <input type="text" class="large" id="txtNome" name="txtNome">
                    </div>
                </div>
    
                <div class="flex-row form-row">
                    <div class="flex-column large-space">
                        <label for="txtDataNascimento">Data de Nascimento</label>
                        <input type="text" class="medium" id="txtDataNascimento" name="txtDataNascimento">
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

    </body>
</html>