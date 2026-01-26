package harden;

public abstract class Task {
    protected final String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markDone() {
        isDone = true;
    }

    public void markNotDone() {
        isDone = false;
    }

    protected String statusIcon() {
        return isDone ? "X" : " ";
    }

    public abstract String serialize();

    @Override
    public String toString() {
        return "[" + statusIcon() + "] " + description;
    }
}
