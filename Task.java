/**
 * Task interface.
 * A task is something that needs to happen
 * or something that we can run() and return a value.
 * @param <T> : task.
 */
public interface Task<T> {

    /**
     * run the task.
     * @return task.
     * @throws Exception in game running .
     */
    T run() throws Exception;
}