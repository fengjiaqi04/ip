package harden;

/**
 * Provides responses for the GUI by reusing existing command logic.
 */
public class HardenGui {

    private final Storage storage;
    private final TaskList tasks;

    public HardenGui(Storage storage, TaskList tasks) {
        this.storage = storage;
        this.tasks = tasks;
    }

    public String getGreeting() {
        GuiUi ui = new GuiUi();
        ui.showWelcome();
        return ui.getOutput();
    }

    public boolean isExitCommand(String input) {
        return input != null && input.trim().equalsIgnoreCase("bye");
    }

    public String getResponse(String input) {
        try {
            Command command = Parser.parse(input);
            GuiUi ui = new GuiUi();

            command.execute(tasks, ui, storage);
            return ui.getOutput();

        } catch (HardenException e) {
            return e.getMessage();
        }
    }
}
