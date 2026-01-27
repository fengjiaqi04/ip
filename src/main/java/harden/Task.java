package harden;

/**
 * Represents a generic task with a description and a completion status.
 * Specific task types (e.g., todo, deadline, event) extend this class.
 */
public abstract class Task {

    protected final String description;
    protected boolean isDone;

    /**
     * Constructs a task with the given description.
     * Tasks are initialized as not done.
     *
     * @param description Task description.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /** Marks this task as done. */
    public void markDone() {
        isDone = true;
    }

    /** Marks this task as not done. */
    public void markNotDone() {
        isDone = false;
    }

    /**
     * Returns the status icon used in task display.
     *
     * @return "X" if done, otherwise a blank space.
     */
    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /**
     * Returns a user-friendly string representation of the task.
     * Subclasses can prefix this using {@code super.toString()}.
     *
     * @return Display form of the task.
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    /**
     * Produces the save-file representation of this task.
     *
     * @return A single-line string used for saving/loading.
     */
    public abstract String serialize();
}
