package harden;

/**
 * Represents a simple todo task.
 */
public class ToDo extends Task {

    /**
     * Constructs a todo task.
     *
     * @param description Task description.
     */
    public ToDo(String description) {
        super(description);
    }
    public ToDo(String description, boolean isDone) {
        super(description, isDone);
    }
    @Override
    public String serialize() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }

    @Override
    public String toString() {
        return "[T][" + (isDone ? "X" : " ") + "] " + description;
    }
}
