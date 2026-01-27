package harden;

/**
 * Marks a task as done using its 1-based index.
 */
public class MarkCommand extends Command {
    private final int index0; // 0-based

    public MarkCommand(int index0) {
        this.index0 = index0;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws HardenException {
        if (index0 < 0 || index0 >= tasks.size()) {
            throw new HardenException("Task number is out of range.");
        }

        Task t = tasks.get(index0);
        t.markDone();

        storage.save(tasks.getTasks().toArray(new Task[0]), tasks.size());

        ui.showLine();
        ui.showMessage("Nice! I've marked this task as done:");
        ui.showMessage("  " + t);
        ui.showLine();
    }
}
