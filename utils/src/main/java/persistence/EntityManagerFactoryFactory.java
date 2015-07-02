package persistence;

import org.glassfish.hk2.api.Factory;
import utils.SmartProperties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Factory to create unique EntityManagerFactory
 *
 * Created by rgb on 10/06/15.
 */
public class EntityManagerFactoryFactory implements Factory<EntityManagerFactory> {

    private static final String PERSISTENCE_UNIT_NAME = SmartProperties.get("smart.persistenceUnitName");
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    @Override
    public EntityManagerFactory provide() {
        return emf;
    }

    @Override
    public void dispose(EntityManagerFactory instance) {
        instance.close();
    }
}
