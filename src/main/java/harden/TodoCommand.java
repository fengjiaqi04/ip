package harden;

/**
 * Command that adds a todo task.
 */
public class TodoCommand extends Command {

    private final String description;

    /**
     * Constructs a todo command.
     *
     * @param description Todo description.
     */
    public TodoCommand(String description) {
        this.description = description;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage)
            throws HardenException {
        Task task = new ToDo(description);
        tasks.add(task);
        storage.save(tasks.getTasks());
        ui.showMessage("Added: " + task);
    }
}
