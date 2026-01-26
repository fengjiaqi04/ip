public class ListCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showLine();

        if (tasks.size() == 0) {
            ui.showMessage("No tasks yet.");
            ui.showLine();
            return;
        }

        for (int i = 0; i < tasks.size(); i++) {
            ui.showMessage((i + 1) + "." + tasks.getTasks().get(i));
        }

        ui.showLine();
    }
}
