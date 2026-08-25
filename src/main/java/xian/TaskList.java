package xian;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents a list of tasks.
 * Provides operations to add, delete, retrieve, and iterate over tasks.
 */
public class TaskList implements Iterable<Task>{

    private final List<Task> tasks;

    /**
     * Creates a new, empty TaskList.
     */
    public TaskList(){
        tasks = new ArrayList<>();
    }

    /**
     * Returns the number of tasks currently in this list.
     *
     * @return the number of tasks.
     */
    public int getSize(){
        return tasks.size();
    }

    /**
     * Returns the task at the specified zero-based index.
     *
     * @param idx the zero-based index of the task to retrieve.
     * @return the task at the given index.
     */
    public Task get(int idx){
        return tasks.get(idx);
    }

    /**
     * Deletes and returns the task at the specified one-based index.
     *
     * @param idx the one-based index of the task to delete.
     * @return the task that was removed.
     */
    public Task delete(int idx){
        return tasks.remove(idx - 1);
    }

    /**
     * Adds a task to the end of this list.
     *
     * @param t the task to add.
     */
    public void add(Task t){
        tasks.add(t);
    }

    /**
     * Returns an iterator over the tasks in this list.
     *
     * @return an iterator over the tasks.
     */
    @Override
    public Iterator<Task> iterator(){
        return this.tasks.iterator();
    }

}