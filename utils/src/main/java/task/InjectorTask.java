package task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.hk2.api.ServiceLocator;

/**
 * Specific task to inject injected tasks.
 *
 * Created by rgb on 19/06/15.
 */
public class InjectorTask<T extends InjectedTask> implements Runnable {

    private final Logger LOGGER = LogManager.getLogger(getClass());

    private final ServiceLocator serviceLocator;
    private final Class<T> task;
    private final Object[] params;

    public InjectorTask(ServiceLocator serviceLocator, Class<T> task, Object... params) {
        this.serviceLocator = serviceLocator;
        this.task = task;
        this.params = params;
    }

    @Override
    public void run() {
        T taskObject = serviceLocator.getService(task);

        LOGGER.debug("Starting injected task: {}", task.getName());

        taskObject.setParams(params);

        taskObject.run();
    }
}
