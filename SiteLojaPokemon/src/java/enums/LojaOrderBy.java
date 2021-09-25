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
public enum LojaOrderBy {

    NONE(0, ""),
    MAIOR_ESTOQUE(1, "ORDER BY e.estoque DESC"),
    MENOR_ESTOQUE(2, "ORDER BY e.estoque"),
    TIPO_POKEMON(3, "ORDER BY e.tipoPokemonID"),
    A_Z(4, "ORDER BY e.nome"),
    Z_A(5, "ORDER BY e.nome DESC");

    private int value;
    private String query;

    private LojaOrderBy(int value, String query) {
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
