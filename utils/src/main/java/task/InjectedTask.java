package task;

/**
 * Interface for injected tasks.
 *
 * Created by rgb on 19/06/15.
 */
public interface InjectedTask extends Runnable {

    /**
     * If this class is used with {@link InjectorTask} this method will be call after constructor and params will be available when run method will be call
     *
     * @param params params no injectable (fixed) to right works of this task
     */
    void setParams(Object ... params);
}
