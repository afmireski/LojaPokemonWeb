/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import java.util.ArrayList;
import java.util.List;
import models.Pedido;

/**
 *
 * @author AFMireski
 */
public class DAOPedido extends DAOGeneric<Pedido> {
    
    public DAOPedido() {
        super(Pedido.class);
    }
    
    public List<Pedido> searchFast(Integer search) {
        return em.createQuery("SELECT e FROM Pedido e WHERE e.id = :search", Pedido.class).setParameter("search", search).getResultList();
    }
    
    public List<Pedido> orderByID(boolean isDesc) {
        return em.createQuery("SELECT e FROM Pedido e ORDER BY e.id " + (isDesc ? "DESC" : "ASC"), Pedido.class).getResultList();
    }
    
    public List<Pedido> orderByDataPedido() {
        return em.createQuery("SELECT e FROM Pedido e ORDER BY e.dataPedido", Pedido.class).getResultList();
    }
    
    public List<Pedido> orderByCartaoID() {
        return em.createQuery("SELECT e FROM Pedido e ORDER BY e.cartaoID.id", Pedido.class).getResultList();
    }
    
    public List<Pedido> orderByUsuarioID() {
        return em.createQuery("SELECT e FROM Pedido e ORDER BY e.usuarioID.id", Pedido.class).getResultList();
    }
    
    public Integer getLastPedidoID() {
        this.refreshAllEntities();
        return this.orderByID(true).get(0).getId();
    }
    
    public List<String> getFKList() {
        List<String> fks = new ArrayList<>();
        
        this.orderByID(false).forEach((p) -> {
            fks.add(p.toFK());
        });
        return fks;
    }
    
    public List<String> getEspecificFKList(List<Pedido> pedidos) {
        List<String> fks = new ArrayList<>();
        
        pedidos.forEach((p) -> {
            fks.add(p.toFK());
        });
        return fks;
    }
    
    
    public static void main(String[] args) {
        
    }
    
}
