<%@page import="java.text.SimpleDateFormat"%>
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
        <link rel="stylesheet" href="../../styles/messages.css">

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

        <!-- Mensagem de Erro -->
        <%
            String errorMessage = (String) session.getAttribute("perfilError");

            if (errorMessage != null && !errorMessage.trim().isEmpty()) {
                session.removeAttribute("updateError");
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

            <legend>Dados pessoais</legend>
            <fieldset>
                <div class="flex-row form-row">
                    <div class="flex-column small-space">
                        <label for="pCPF">CPF</label>
                        <p id="pCPF">
                            <%=(usuario.getPessoaCPF().getCpf())%>
                        </p>
                    </div>
                    <div class="flex-column large-space">
                        <label for="pNome">Nome</label>
                        <p id="pNome">
                            <%=(usuario.getPessoaCPF().getNome())%>                        
                        </p>
                    </div>
                </div>

                <div class="flex-row form-row">
                    <div class="flex-column large-space">
                        <label for="pSexo">Sexo</label>
                        <p id="pSexo">                        
                            <%=(usuario.getPessoaCPF().getSexoDescricao())%>
                        </p>
                    </div>
                    <div class="flex-column medium-space">
                        <label for="pDataNascimento">Data de Nascimento</label>                    
                        <p id="pDataNascimento">                        
                            <%=(new SimpleDateFormat("dd/MM/yyyy").format(usuario.getPessoaCPF().getDataNascimento()))%>
                        </p>
                    </div>                    
                </div>
                <div class="flex-row form-row end">                
                    <button type="button" class="dark" id="edit-pes" name="edit-pes">Editar</button>
                </div>
            </fieldset>

            <legend>Dados de usuário</legend>
            <fieldset>
                <div class="flex-row form-row">
                    <div class="flex-column medium-space">
                        <label for="pEmail">E-mail</label>
                        <p id="pEmail">
                            <%=(usuario.getEmail())%>
                        </p>
                    </div>
                </div>                      
                <div class="flex-row form-row end">                
                    <button type="button" class="dark" id="edit-user" name="edit-user">Editar</button>
                </div>
            </fieldset>

            <div class="tile" style=" color: red;" id="excluir-tile">
                <span class="material-icons" style="margin-right: 5px;">
                    person_off
                </span>
                <div style="display: flex; align-self: center;">Excluir conta</div>
            </div>
            <div class="tile" style=" color: red;" onclick="logout()">
                <span class="material-icons" style="margin-right: 5px;">
                    logout
                </span>
                <div style="display: flex; align-self: center;">Logout</div>
            </div>
        </div>

        <!-- MODAL ENDEREÇO -->
        <div class="modal"  id="end-form">
            <form action="../../updateEnderecoServlet" method="post" class="modal-box"  accept-charset="utf-8">
                <div class="flex-row end"><span class="close-modal">&times;</span></div>
                <div class="flex-row form-row">
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
                </div>
                <div class="flex-row form-row end">
                    <button type="submit" class="dark" id="upd-end" name="upd-end">Salvar</button>
                </div>
            </form>
        </div>

        <!-- MODAL PESSOA -->
        <div class="modal"  id="pes-form">
            <form action="../../updatePessoaServlet" method="post" class="modal-box" accept-charset="utf-8">
                <div class="flex-row end"><span class="close-modal">&times;</span></div>
                <div class="flex-row form-row">
                    <div class="flex-column large-space">
                        <label for="txtNome">Nome</label>
                        <input type="text" class="large" id="txtNome" name="txtNome" required>
                    </div>
                </div>
                <div class="flex-row form-row">
                    <div class="flex-column uniforme-space">
                        <label for="txtSexo">Sexo</label>
                        <select name="txtSexo" id="txtSexo" class="medium">
                            <option value="Masculino">Masculino</option>
                            <option value="Feminino">Feminino</option>
                        </select>
                    </div>
                    <div class="flex-column uniforme-space">
                        <label for="txtDataNascimento">Data de Nascimento</label>
                        <input type="date" name="txtDataNascimento" id="txtDataNascimento" class="medium" required>
                    </div>                    
                </div>
                <div class="flex-row form-row end">
                    <button type="submit" class="dark" id="upd-pes" name="upd-pes">Salvar</button>
                </div>
            </form>
        </div>

        <!-- MODAL USUARIO -->
        <div class="modal"  id="user-form">
            <form action="../../updateUsuarioServlet" method="post" class="modal-box"  accept-charset="utf-8">
                <div class="flex-row end"><span class="close-modal">&times;</span></div>
                <div class="flex-row form-row">
                    <div class="flex-column uniforme-space">
                        <label for="txtEmail">E-mail</label>
                        <input type="email" class="large" id="txtEmail" name="txtEmail" required>
                    </div>
                </div>
                <div class="flex-row form-row">
                    <div class="flex-column uniforme-space">
                        <label for="txtVSenha">Verificação de senha</label>
                        <input type="password" class="large" id="txtVSenha" name="txtVSenha" placeholder="Informe sua senha atual" required>
                    </div>                                     
                </div>
                <div class="flex-row form-row">
                    <div class="flex-column uniforme-space">
                        <label for="txtNovaSenha">Nova senha</label>
                        <input type="password" class="large" id="txtNovaSenha" name="txtNovaSenha" placeholder="Opcional se não quiser atualizar">
                    </div>                                     
                </div>
                <div class="flex-row form-row">
                    <div class="flex-column uniforme-space">
                        <label for="txtConfirmaNovaSenha">Confirme à nova senha</label>
                        <input type="password" class="large" id="txtConfirmaNovaSenha" name="txtConfirmaNovaSenha" placeholder="Opcional se não quiser atualizar">
                    </div>                                     
                </div>
                <div class="flex-row form-row end">
                    <button type="submit" class="dark" id="upd-user" name="upd-user">Salvar</button>
                </div>
            </form>
        </div>

        <!-- MODAL EXCLUIR CONTA -->
        <div class="modal" id="excluir-conta-modal">            
            <div class="modal-box">
                <div class="flex-row end">
                    <span class="close-modal">&times;</span>
                </div>
                <div class="flex-row center">
                    <h1 class="excluir-title">Excluir conta</h1>
                </div>
                <div class="flex-row center">
                    <p>Deseja mesmo excluir sua conta?</p>
                </div>
                <div class="flex-row space-between">
                    <button type="button" class="no-delete-button" id="btn-cancel">
                        Cancelar
                    </button>
                    <button type="button" class="delete-button" id="btn-excluir-conta" onclick="excluirConta()">
                        Excluir
                    </button>
                </div>
            </div>
        </div>

        <script>
            //Form Endereço
            var end_form = document.getElementById("end-form");

            var edit_end = document.getElementById("edit-end");

            var close_end = document.getElementsByClassName("close-modal")[0];

            edit_end.onclick = function () {
                var txtDesc = document.getElementById("txtDesc");
                var txtCidade = document.getElementById("txtCidade");
                var txtEstado = document.getElementById("txtEstado");

                txtDesc.value = '<%=(usuario.getPessoaCPF().getEndereco().getNome())%>';
                txtCidade.value = '<%=(usuario.getPessoaCPF().getEndereco().getCidade())%>';
                txtEstado.value = '<%=usuario.getPessoaCPF().getEndereco().getUf()%>';

                end_form.style.display = "block";
            }

            close_end.onclick = function () {
                end_form.style.display = "none";
            }


            //Form Pessoa
            var pes_form = document.getElementById("pes-form");

            var edit_pes = document.getElementById("edit-pes");

            var close_pes = document.getElementsByClassName("close-modal")[1];

            edit_pes.onclick = function () {
                var txtNome = document.getElementById("txtNome");
                var txtSexo = document.getElementById("txtSexo");
                var txtDataNascimento = document.getElementById("txtDataNascimento");

                txtNome.value = '<%=(usuario.getPessoaCPF().getNome())%>';
                txtSexo.value = '<%=(usuario.getPessoaCPF().getSexoDescricao())%>';
                txtDataNascimento.value = '<%=new SimpleDateFormat("yyyy-MM-dd").format(usuario.getPessoaCPF().getDataNascimento())%>';

                pes_form.style.display = "block";
            }

            close_pes.onclick = function () {
                pes_form.style.display = "none";
            }

            //Form Usuário
            var user_form = document.getElementById("user-form");

            var edit_user = document.getElementById("edit-user");

            var close_user = document.getElementsByClassName("close-modal")[2];

            edit_user.onclick = function () {
                var txtEmail = document.getElementById("txtEmail");

                txtEmail.value = '<%=(usuario.getEmail())%>';

                user_form.style.display = "block";
            }

            close_user.onclick = function () {
                user_form.style.display = "none";
            }

            // Excluir Conta
            var excluir_modal = document.getElementById("excluir-conta-modal");

            var btn_cancel = document.getElementById("btn-cancel");

            var close_excluir = document.getElementsByClassName("close-modal")[3];

            var excluir_tile = document.getElementById("excluir-tile");
            
            excluir_tile.onclick = function () {
                excluir_modal.style.display = "block";
            }

            btn_cancel.onclick = function () {
                excluir_modal.style.display = "none";
            }

            close_excluir.onclick = function () {
                excluir_modal.style.display = "none";
            };

            window.onclick = function (event) {
                if (event.target == end_form) {
                    end_form.style.display = "none";
                } else if (event.target == pes_form) {
                    pes_form.style.display = "none";
                } else if (event.target == user_form) {
                    user_form.style.display = "none";
                }
            }

        </script>
        <script src="../../scripts/perfil/gerenciarConta.js"></script>
        <%
            }
        %>
    </body>
</html>
