package harden;

/**
 * Command that shows a help message containing available commands.
 */
public class HelpCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showMessage("Commands:");
        ui.showMessage("list");
        ui.showMessage("delete <task number>");
        ui.showMessage("todo <description>");
        ui.showMessage("deadline <description> /by <date time>");
        ui.showMessage("event <description> /from <start> /to <end>");
        ui.showMessage("mark <task number>");
        ui.showMessage("unmark <task number>");
        ui.showMessage("delete <task number>");
        ui.showMessage("find <keyword>");
        ui.showMessage("bye");
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
