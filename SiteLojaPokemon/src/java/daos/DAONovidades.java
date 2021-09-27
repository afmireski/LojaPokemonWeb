/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import java.util.List;
import models.Novidades;

/**
 *
 * @author Matheus
 */
public class DAONovidades extends DAOGeneric<Novidades>{
    
    public DAONovidades() {
        super(Novidades.class);
    }
    
    public List<Novidades> searchByID(int id) {
        return em.createQuery("SELECT e FROM Novidades e WHERE e.id = :id").setParameter("id", id).getResultList();
    }
    
    public List<Novidades> listOrderByID() {
        return em.createQuery("SELECT e FROM Novidades e ORDER BY e.id").getResultList();
    }
    
    public Novidades getUltimaNovidade(){
        this.refreshAllEntities();
        List<Novidades> lista = em.createQuery("SELECT e FROM Novidades e ORDER BY e.id desc").getResultList(); 
        return lista.isEmpty()?null:lista.get(0); 
    }
    
    public static void main(String[] args) {
        ///VERIFICA OS DADOS NO BANCO
        DAONovidades daoNovidades = new DAONovidades();
        List<Novidades> end = daoNovidades.list();

        end.forEach((e) -> {
            System.out.println(e.getId() + "-" + e.getTitulo());
        });
    }
}
