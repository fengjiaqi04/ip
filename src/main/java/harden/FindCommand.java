package harden;

import java.util.ArrayList;

/**
 * Finds tasks whose descriptions contain a given keyword.
 */
public class FindCommand extends Command {

    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ArrayList<Task> matches = new ArrayList<>();

        //  use the internal list instead of tasks.get(i)
        for (Task task : tasks.getTasks()) {
            if (task != null && task.getDescription().contains(keyword)) {
                matches.add(task);
            }
        }

        ui.showLine();
        ui.showMessage("Here are the matching tasks in your list:");

        if (matches.isEmpty()) {
            ui.showMessage("No matching tasks found.");
        } else {
            for (int i = 0; i < matches.size(); i++) {
                ui.showMessage((i + 1) + "." + matches.get(i));
            }
        }

        ui.showLine();
    }
}
