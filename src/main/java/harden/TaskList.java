package harden;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of tasks in the Harden chatbot.
 */
public class TaskList {

    /** Internal list storing all tasks. */
    private final List<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with an existing list of tasks.
     *
     * @param tasks List of tasks to initialize with
     */
    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return Number of tasks
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Adds a task to the list.
     *
     * @param task Task to be added
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Retrieves a task at the specified index.
     *
     * @param index Index of the task (0-based)
     * @return The task at the given index
     */
    public Task getTask(int index) {
        return tasks.get(index);
    }

    /**
     * Removes and returns the task at the specified index.
     *
     * @param index Index of the task to remove (0-based)
     * @return The removed task
     */
    public Task removeTask(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns the underlying list of tasks.
     *
     * @return List of tasks
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Adds a task to the list.
     * This is kept for backward compatibility with existing command code.
     *
     * @param task Task to be added
     */
    public void add(Task task) {
        addTask(task);
    }

    /**
     * Retrieves a task at the specified index.
     * This is kept for backward compatibility with existing command code.
     *
     * @param index Index of the task (0-based)
     * @return The task at the given index
     */
    public Task get(int index) {
        return getTask(index);
    }

}
