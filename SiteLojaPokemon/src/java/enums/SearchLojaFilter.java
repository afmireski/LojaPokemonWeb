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
public enum SearchLojaFilter {
    
    NONE(0, ""),
    HAS_SEARCH(1, "(e.nome like :search or "
                + "e.tipoPokemonID.sigla like :search or "
                + "e.tipoPokemonID.descricao like :search)");
    
    private int value;
    private String query;

    private SearchLojaFilter(int value, String query) {
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
