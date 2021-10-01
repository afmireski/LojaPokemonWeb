var poke_form = document.getElementById("poke-form");
var pagar_form = document.getElementById("pagar-form");

var btn_comprar = document.getElementById("btn-comprar");

var close_comprar = document.getElementsByClassName("close-modal")[0];
var close_pagar = document.getElementsByClassName("close-modal")[1];

let value;

function show_pokemon(id, nome, tipo, estoque, preco, imagem) {
    var img = document.getElementById("poke-image");
    img.src = `../..${imagem}`;
    img.alt = `${nome}`;

    var poke_nome = document.getElementById("poke-nome");
    poke_nome.innerText = `${nome}`;

    var poke_tipo = document.getElementById("poke-tipo");
    poke_tipo.innerHTML = tipo;

    var poke_estoque = document.getElementById("poke-estoque");
    poke_estoque.innerHTML = `Estoque: ${estoque}`;

    var txtQtd = document.getElementById("txtQtd");
    txtQtd.max =estoque;    

    var poke_price = document.getElementById("poke-preco");
    poke_price.innerHTML = `R$ ${preco.toFixed(2)}`;

    value = preco;
    
    var txtPokeID = document.getElementById("txtPokeID");
    txtPokeID.value = `${id}`;    

    poke_form.style.display = "block";
}

function show_pagar() {
    var total_price = document.getElementById("total-price");
    var txtQtd = document.getElementById("txtQtd");

    let total = value * txtQtd.value;

    total_price.innerHTML = `${total.toFixed(2)}`;
    
    var txtQtdP = document.getElementById("txtQtdP");
    txtQtdP.value = txtQtd.value;    

    pagar_form.style.display = "block";
}

close_comprar.onclick = function () {
    poke_form.style.display = "none";
}

close_pagar.onclick = function () {
    pagar_form.style.display = "none";
}

function bloquear_pagar() {
    var loader = document.getElementById('loader-modal');
    var btn_pagar = document.getElementById('btn-pagar');

    btn_pagar.disabled = true;
    loader.style.display = 'block';
}

window.onclick = function (event) {
    if (event.target == poke_form)  {
        poke_form.style.display = "none";
    } else if (event.target == pagar_form)  {
        pagar_form.style.display = "none";
    } else if (event.target == document.getElementById("filtro-form")) {
        document.getElementById("filtro-form").style.display = "none";
    }
}
