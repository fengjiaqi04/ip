package harden;

/**
 * Abstract executable user command.
 */
public abstract class Command {

    /**
     * Executes the command.
     *
     * @param tasks Task list.
     * @param ui User interface.
     * @param storage Storage handler.
     * @throws HardenException If execution fails.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage)
            throws HardenException;

    /**
     * Indicates whether the application should exit.
     *
     * @return True if exit command.
     */
    public boolean isExit() {
        return false;
    }
}
