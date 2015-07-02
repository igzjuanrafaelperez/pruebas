package persistence;

import org.glassfish.hk2.api.Factory;
import utils.SmartProperties;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Each factory creates unique EntityManager. Expected scope for factory is PerThread or general (PerLookup)
 *
 * Created by rgb on 10/06/15.
 */
public class EntityManagerDIFactory implements Factory<EntityManager> {

    private final EntityManager em;

    @Inject
    public EntityManagerDIFactory(EntityManagerFactory emf) {
        em = emf.createEntityManager();
    }

    @Override
    public EntityManager provide() {
        return em;
    }

    @Override
    public void dispose(EntityManager instance) {
        instance.close();
    }
}
