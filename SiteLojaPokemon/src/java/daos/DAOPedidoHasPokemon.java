/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import enums.PhpOrderBy;
import enums.SearchPhpFilter;
import java.util.List;
import javax.persistence.Query;
import models.PedidoHasPokemon;
import models.PedidoHasPokemonPK;

/**
 *
 * @author AFMireski
 */
public class DAOPedidoHasPokemon extends DAOGeneric<PedidoHasPokemon> {
    
    public DAOPedidoHasPokemon() {
        super(PedidoHasPokemon.class);
    }    
    
    public PedidoHasPokemon get(PedidoHasPokemonPK pk) {
        return (PedidoHasPokemon) em.find(PedidoHasPokemon.class, pk);        
    }
    
    public List<PedidoHasPokemon> searchByPedidoID(Integer pedidoID) {
        return em.createQuery("SELECT e FROM PedidoHasPokemon e "
                + "WHERE e.pedidoHasPokemonPK.pedidoID = :id", PedidoHasPokemon.class)
                .setParameter(":id", pedidoID).getResultList();
    }
    
    public List<PedidoHasPokemon> searchByPokemonID(Integer pokemonID) {
        return em.createQuery("SELECT e FROM PedidoHasPokemon e "
                + "WHERE e.pedidoHasPokemonPK.pokemonID = :id", PedidoHasPokemon.class)
                .setParameter(":id", pokemonID).getResultList();
    }
    
    public List<PedidoHasPokemon> orderByPokemonID() {
        return em.createQuery("SELECT e FROM PedidoHasPokemon e ORDER BY e.pedidoHasPokemonPK.pokemonID", PedidoHasPokemon.class).getResultList();
    }
    
    public List<PedidoHasPokemon> orderByPedidoID() {
        return em.createQuery("SELECT e FROM PedidoHasPokemon e ORDER BY e.pedidoHasPokemonPK.pedidoID", PedidoHasPokemon.class).getResultList();
    }
    
    public List<PedidoHasPokemon> orderByQuantidade() {
        return em.createQuery("SELECT e FROM PedidoHasPokemon e ORDER BY e.quantidade", PedidoHasPokemon.class).getResultList();
    }
    
    public List<PedidoHasPokemon> orderByValorUnitario() {       
        return em.createQuery("SELECT e FROM PedidoHasPokemon e ORDER BY e.valorUnitario", PedidoHasPokemon.class).getResultList();
    }   
    
    public List<PedidoHasPokemon> listPedidoHasPokemonsByUser(int userID) {
        this.refreshAllEntities();
        return em.createQuery(
                "SELECT e FROM PedidoHasPokemon e WHERE e.pedido.usuarioID.id = :userID", 
                PedidoHasPokemon.class).setParameter("userID", userID).getResultList();
    }
    
    public List<PedidoHasPokemon> listPedidoHasPokemonsByUserWithFilters(
            int userID, String search, SearchPhpFilter filter, PhpOrderBy order) {
        this.refreshAllEntities();
        String query = "SELECT e FROM PedidoHasPokemon e WHERE e.pedido.usuarioID.id = :userID";
        
        if (filter.equals(SearchPhpFilter.HAS_SEARCH)) {
            query += String.format(" AND %s", filter.getQuery());
        }
        
        if (!order.equals(PhpOrderBy.NONE)) {
            query += String.format(" %s", order.getQuery());            
        }
        
        Query finalQuery = em.createQuery(query, PedidoHasPokemon.class);
        
        if (search != null && !search.trim().isEmpty()) {            
            finalQuery.setParameter("search", "%"+search+"%");
        }
        
        finalQuery.setParameter("userID", userID);
        
        return finalQuery.getResultList();
    }
    
    public List<PedidoHasPokemon> findAllPHPByPedidoID(Integer pedidoID) {
        Query query = em.createQuery("SELECT e FROM PedidoHasPokemon e WHERE e.pedidoHasPokemonPK.pedidoID = :pedidoID",
                 PedidoHasPokemon.class);
        query.setParameter("pedidoID", pedidoID);
        return query.getResultList();
    }
    
    public static void main(String[] args) {
        DAOPedidoHasPokemon daoPedidoHasPokemon = new DAOPedidoHasPokemon();
        
        daoPedidoHasPokemon.list().forEach((php) -> {
            System.out.println(php.toString());
            System.out.println(php.getPokemon().toString());
        });
    }
 
           
    
    
    
}
