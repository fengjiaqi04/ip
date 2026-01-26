package harden;

public class ByeCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        // Goodbye message is handled in harden.Harden.java after execute()
        // so we keep this empty.
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
