package daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class DAOGeneric<T> {

    public static EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
    private Class cls;

    public DAOGeneric(Class cls) {
        this.cls = cls;
    }

    public void insert(T e) {
        em.getTransaction().begin();
        em.persist(e);
        em.getTransaction().commit();
    }

    public void update(T e) {
        em.getTransaction().begin();
        em.merge(e);
        em.getTransaction().commit();
    }

    public void delete(T e) {
        em.getTransaction().begin();
        em.remove(e);
        em.getTransaction().commit();
    }

    public T get(Long id) {
        return refreshEntity((T) em.find(cls, id));
    }

    public T get(Integer id) {
        return refreshEntity((T) em.find(cls, id));
    }

    public T get(String id) {
        return refreshEntity((T) em.find(cls, id));
    }
    
    public T refreshEntity(T e) {
        if (e != null) {
            em.refresh(e);
        }
        return e;
    } 
    
    public void refreshAllEntities() {
        this.list().forEach((t) -> {            
            em.refresh(t);
        });
    }

    public List<T> list() {
        return em.createQuery("SELECT e FROM " + cls.getSimpleName() + " e").getResultList();
    }
}
