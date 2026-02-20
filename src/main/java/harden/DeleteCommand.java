package harden;

/**
 * Deletes a task using its 0-based index provided by {@link Parser}.
 */
public class DeleteCommand extends Command {
    private final int index0; // 0-based

    public DeleteCommand(int index0) {
        this.index0 = index0;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws HardenException {
        if (index0 < 0 || index0 >= tasks.size()) {
            throw new HardenException("Task number is out of range.");
        }

        Task removed = tasks.removeTask(index0);

        storage.save(tasks.getTasks().toArray(new Task[0]), tasks.size());

        ui.showLine();
        ui.showMessage("Noted. I've removed this task:");
        ui.showMessage("  " + removed);
        ui.showMessage("Now you have " + tasks.size() + " tasks in the list.");
        ui.showLine();
    }
}
