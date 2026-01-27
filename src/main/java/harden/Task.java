package harden;

/**
 * Represents a task item with a description and completion status.
 * Subclasses define any additional fields (e.g., dates/times) and how to serialize.
 */
public abstract class Task {
    protected final String description;
    protected boolean isDone;

    /**
     * Creates a task with the given description. Task starts as not done.
     *
     * @param description Task description.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Creates a task with the given description and status.
     *
     * @param description Task description.
     * @param isDone Whether task is done.
     */
    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Returns the task description.
     *
     * @return Description string.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Marks this task as done.
     */
    public void markDone() {
        isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void markNotDone() {
        isDone = false;
    }

    /**
     * Returns the status icon used in UI.
     *
     * @return "X" if done, else " ".
     */
    protected String statusIcon() {
        return isDone ? "X" : " ";
    }

    /**
     * Converts this task into a line suitable for saving to disk.
     *
     * @return Serialized task line.
     */
    public abstract String serialize();

    /**
     * Default string representation for a basic task.
     *
     * @return Display string.
     */
    @Override
    public String toString() {
        return "[" + statusIcon() + "] " + description;
    }
}
