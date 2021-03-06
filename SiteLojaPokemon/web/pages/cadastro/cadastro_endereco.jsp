<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="../../styles/style.css">
    <link rel="stylesheet" href="../../styles/flexbox.css">

    <link rel="shortcut icon" href="../../favicons/loja-pokemon-fav.ico" type="image/x-icon">

    <title>Loja Pokémon</title>
</head>

<body class="out-sys">
    <form action="../../prossegueEnderecoServlet" method="post" class="form-box">
        <legend>Endereço</legend>
        <fieldset>
            <div class="flex-row form-row">
                <div class="flex-column small-space">
                    <label for="txtCEP">CEP</label>
                    <input type="text" class="medium" id="txtCEP" name="txtCEP" maxlength="8" required>
                </div>
                <div class="flex-column large-space">
                    <label for="txtDesc">Descrição</label>
                    <input type="text" class="large" id="txtDesc" name="txtDesc" required>
                </div>
            </div>

            <div class="flex-row form-row">
                <div class="flex-column large-space">
                    <label for="txtCidade">Cidade</label>
                    <input type="text" class="large" id="txtCidade" name="txtCidade" required>
                </div>
                <div class="flex-column medium-space">
                    <label for="txtEstado">Estado</label>
                    <select name="txtEstado" id="txtEstado" class="medium">
                        <option value="1">Acre - AC</option>
                        <option value="2">Alagoas - AL</option>
                        <option value="3">Amapá - AP</option>
                        <option value="4">Amazonas - AM</option>
                        <option value="5">Bahia - BA</option>
                        <option value="6">Ceará - CE</option>
                        <option value="7">Espírito Santo - ES</option>
                        <option value="8">Goías - GO</option>
                        <option value="9">Maranhão - MA</option>
                        <option value="10">Mato Grosso - MT</option>
                        <option value="11">Mato Grosso do Sul - MS</option>
                        <option value="12">Minas Gerais - MG</option>
                        <option value="13">Pará - PA</option>
                        <option value="14">Paraíba - PB</option>
                        <option value="15" selected>Paraná - PR</option>
                        <option value="16">Pernambuco - PE</option>
                        <option value="17">Piauí - PI</option>
                        <option value="18">Rio de Janeiro - RJ</option>
                        <option value="19">Rio Grande do Norte - RN</option>
                        <option value="20">Rio Grande do Sul - RS</option>
                        <option value="21">Rondônia - RO</option>
                        <option value="22">Roraima - RR</option>
                        <option value="23">Santa Catarina - SC</option>
                        <option value="24">São Paulo - SP</option>
                        <option value="25">Sergipe - SE</option>
                        <option value="26">Tocantins - TO</option>
                        <option value="27">Distrito Federal - DF</option>
                    </select>
                </div>
                <div class="flex-column uniforme-space">
                    <label for="txtNCasa">Nº Casa</label>
                    <input type="number" class="medium" id="txtNCasa" name="txtNCasa" required>
                </div>
            </div>
            <div class="flex-row form-row end">                
                <button type="submit" class="dark small-font" id="pro-end" name="pro-end">Prosseguir</button>
            </div>
        </fieldset>        
    </form>
</body>

</html>