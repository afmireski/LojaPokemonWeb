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

        <!-- MODAL ENDEREÇO -->
        <div class="modal"  id="end-form">
            <form action="" method="post" class="form-box">
                <div class="flex-row end"><span class="close-modal">&times;</span></div>
                <div class="flex-row form-row">
                    <div class="flex-column large-space">
                        <label for="txtDesc">Descrição</label>
                        <input type="text" class="large" id="txtDesc" name="txtDesc">
                    </div>
                </div>
                <div class="flex-row form-row">
                    <div class="flex-column large-space">
                        <label for="txtCidade">Cidade</label>
                        <input type="text" class="large" id="txtCidade" name="txtCidade">
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
                            <option value="0">Distrito Federal - DF</option>
                        </select>
                    </div>                    
                </div>
                <div class="flex-row form-row end">
                    <button type="button" class="dark" id="upd-end" name="upd-end">Salvar</button>
                </div>
            </form>
        </div>

        <script>
            var end_form = document.getElementById("end-form");

            var edit_end = document.getElementById("edit-end");

            var close_end = document.getElementsByClassName("close-modal")[0];

            edit_end.onclick = function () {
                var txtDesc = document.getElementById("txtDesc");
                var txtCidade = document.getElementById("txtCidade");
                var txtEstado = document.getElementById("txtEstado");

                txtDesc.value = '<%=(usuario.getPessoaCPF().getEndereco().getNome())%>';
                txtCidade.value = '<%=(usuario.getPessoaCPF().getEndereco().getCidade())%>';
                txtEstado.value = <%=usuario.getPessoaCPF().getEndereco().getUf()%>;

                end_form.style.display = "block";
            }

            close_end.onclick = function () {
                end_form.style.display = "none";
            }

            window.onclick = function (event) {
                if (event.target == end_form) {
                    end_form.style.display = "none";
                }
            }
        </script>

        <script src="../../scripts/generalScripts.js"></script>
        <%
            }
        %>
    </body>
</html>
