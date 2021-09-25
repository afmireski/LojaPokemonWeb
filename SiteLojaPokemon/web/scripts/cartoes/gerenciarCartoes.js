var excluir_modal = document.getElementById("excluir-cartao-modal");

var editar_modal = document.getElementById("editar-cartao-modal");

var btn_cancel = document.getElementById("btn-cancel");

var close_excluir = document.getElementsByClassName("close-modal")[0];

var close_editar = document.getElementsByClassName("close-modal")[1];

function show_editar_cartao_modal(id, saldo, desc){
    var txtNumEd = document.getElementById("txtNumEd"); 
    txtNumEd.value = id; 
    var txtSaldoEd = document.getElementById("txtSaldoEd"); 
    txtSaldoEd.value = saldo; 
    var txtNomeEd = document.getElementById("txtNomeEd"); 
    txtNomeEd.value = desc; 
    editar_modal.style.display = "block"; 
}

function show_excluir_cartao_modal(id){
    var txtId = document.getElementById("txtId"); 
    txtId.value = id; 
    excluir_modal.style.display = "block"; 
}

btn_cancel.onclick = function () {
    excluir_modal.style.display = "none";
}

close_excluir.onclick = function () {
    excluir_modal.style.display = "none";
};

close_editar.onclick = function () {
    editar_modal.style.display = "none";
};


window.onclick = function (event) {
    if (event.target == excluir_modal)  {
        excluir_modal.style.display = "none";
    } else if (event.target == editar_modal) {
        editar_modal.style.display = "none";
    }
}