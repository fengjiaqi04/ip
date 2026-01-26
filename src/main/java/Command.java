public abstract class Command {
    /**
     * Executes the command.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws HardenException;

    /**
     * Whether this command should end the program.
     */
    public boolean isExit() {
        return false;
    }
}
