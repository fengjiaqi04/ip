package harden;

public class ToDo extends Task {
    public ToDo(String description) {
        super(description);
    }
    public ToDo(String description, boolean isDone) {
        super(description, isDone);
    }
    @Override
    public String serialize() {
        return "T | " + (isDone ? 1 : 0) + " | " + description;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
