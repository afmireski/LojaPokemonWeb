var filtro_modal = document.getElementById("filtro-form");

var btn_filtro = document.getElementById("btn-filtro");

var btn_limpar_filtro = document.getElementById("btn-limpar-filtro");

var close_filtro = document.getElementsByClassName("close-modal")[2];

btn_limpar_filtro.onclick = function () {
    document.location.href = "../../clearLojaFilterServlet"
}

close_filtro.onclick = function () {
    filtro_modal.style.display = "none";
}

btn_filtro.onclick = function () {filtro_modal.style.display = "block";}



