var pedido_form = document.getElementById("pedido-form");

var btn_excluir_pedido = document.getElementById("btn-excluir-pedido");

var close_pedido = document.getElementsByClassName("close-modal")[0];

function show_detalhes_pedido(idPoke, idPedido, nome, tipo, quantidade, preco, imagem) {
    var img = document.getElementById("poke-image");
    img.src = `../${imagem}`;
    img.alt = `${nome}`;

    var poke_nome = document.getElementById("poke-nome");
    poke_nome.innerText = `${nome}`;

    var poke_tipo = document.getElementById("poke-tipo");
    poke_tipo.innerHTML = tipo;

    var poke_quantidade = document.getElementById("poke-quantidade");
    poke_quantidade.innerHTML = `${quantidade}`;

    var poke_price = document.getElementById("poke-preco");
    poke_price.innerHTML = `${preco}`;

    var poke_total = document.getElementById("poke-total");
    poke_total.innerHTML = `${preco * quantidade}`;

    var txtPokeID = document.getElementById("txtPokeID");
    txtPokeID.value = idPoke;

    var txtPedidoID = document.getElementById("txtPedidoID");
    txtPedidoID.value = idPedido;    

    pedido_form.style.display = "block";
}

close_pedido.onclick = function () {    
    pedido_form.style.display = "none";
}

// Excluir Pedido
var excluir_modal = document.getElementById("excluir-pedido-modal");

var btn_cancel = document.getElementById("btn-cancel");

var close_excluir = document.getElementsByClassName("close-modal")[1];

btn_excluir_pedido.onclick = function () {
    excluir_modal.style.display = "block";
}

btn_cancel.onclick = function () {
    excluir_modal.style.display = "none";
}

close_excluir.onclick = function () {
    excluir_modal.style.display = "none";
};


window.onclick = function (event) {
    if (event.target == pedido_form)  {
        pedido_form.style.display = "none";
    } else if  (event.target == document.getElementById("filtro-form")) {
        document.getElementById("filtro-form").style.display = "none";
    }
}