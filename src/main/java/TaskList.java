import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = new ArrayList<>();
        if (tasks != null) {
            for (Task t : tasks) {
                if (t != null) {
                    this.tasks.add(t);
                }
            }
        }
    }

    public int size() {
        return tasks.size();
    }

    public Task get(int index) throws HardenException {
        if (index < 0 || index >= tasks.size()) {
            throw new HardenException("OOPS!!! The task number is invalid.");
        }
        return tasks.get(index);
    }

    public void add(Task task) throws HardenException {
        if (task == null) {
            throw new HardenException("OOPS!!! Cannot add an empty task.");
        }
        tasks.add(task);
    }

    public Task remove(int index) throws HardenException {
        if (index < 0 || index >= tasks.size()) {
            throw new HardenException("OOPS!!! The task number is invalid.");
        }
        return tasks.remove(index);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
