package harden;

import java.util.ArrayList;

/**
 * Represents an in-memory list of tasks.
 */
public class TaskList {

    private final ArrayList<Task> tasks;

    public Task get(int index) {
        return tasks.get(index);
    }

    /** Constructs an empty task list. */
    public TaskList() {
        tasks = new ArrayList<>();
    }

    /**
     * Constructs a task list from existing tasks.
     *
     * @param tasks Initial tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task.
     *
     * @param task Task to add.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Returns number of tasks.
     *
     * @return Task count.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns internal task list.
     *
     * @return Task list.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }

}
