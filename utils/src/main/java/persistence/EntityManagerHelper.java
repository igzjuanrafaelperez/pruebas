package persistence;


import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * Created by dgarcia on 08/04/2015.
 */
public class EntityManagerHelper {

    private final EntityManager em;

    @Inject
    public EntityManagerHelper(EntityManager em) {
        this.em = em;
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public EntityTransaction getTransaction() {
        return getEntityManager().getTransaction();
    }

    public void beginTransaction() {
        getTransaction().begin();
    }

    public void rollback() {
        getTransaction().rollback();
    }

    public void safeRollback() {
        EntityTransaction tx = getTransaction();

        if ( tx != null && tx.isActive() ) {
            rollback();
        }
    }

    public void commit() {
        getTransaction().commit();
    }
}