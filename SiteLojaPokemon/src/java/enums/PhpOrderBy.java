/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enums;

/**
 *
 * @author AFMireski
 */
public enum PhpOrderBy {

    NONE(0, ""),
    MAIOR_QUANTIDADE(1, "ORDER BY e.quantidade DESC"),
    MENOR_QUANTIDADE(2, "ORDER BY e.quantidade"),
    MAIOR_V_U(3, "ORDER BY e.valorUnitario DESC"),
    MENOR_V_U(4, "ORDER BY e.valorUnitario"),
    TIPO_POKEMON(5, "ORDER BY e.pokemon.tipoPokemonID"),
    A_Z(6, "ORDER BY e.pokemon.nome"),
    Z_A(7, "ORDER BY e.pokemon.nome DESC"),
    PEDIDOS_RECENTES(8, "ORDER BY e.pedido.dataPedido DESC"),
    PEDIDOS_ANTIGOS(9, "ORDER BY e.pedido.dataPedido");

    private int value;
    private String query;

    private PhpOrderBy(int value, String query) {
        this.value = value;
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public int getValue() {
        return value;
    }

}
