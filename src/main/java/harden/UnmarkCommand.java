package harden;

/**
 * Marks a task as not done using its 1-based index.
 */
public class UnmarkCommand extends Command {
    private final int index0; // 0-based

    public UnmarkCommand(int index0) {
        this.index0 = index0;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws HardenException {
        if (index0 < 0 || index0 >= tasks.size()) {
            throw new HardenException("Task number is out of range.");
        }

        Task t = tasks.get(index0);
        t.markNotDone();

        storage.save(tasks.getTasks().toArray(new Task[0]), tasks.size());

        ui.showLine();
        ui.showMessage("OK, I've marked this task as not done yet:");
        ui.showMessage("  " + t);
        ui.showLine();
    }
}
