package harden;

/**
 * Provides responses for the GUI by reusing existing command logic.
 */
public class HardenGui {

    private final Storage storage;
    private final TaskList tasks;
    /**
     * Creates a GUI adapter with the given storage and task list.
     *
     * @param storage Storage used to load/save tasks.
     * @param tasks Task list used by the app.
     */
    public HardenGui(Storage storage, TaskList tasks) {
        this.storage = storage;
        this.tasks = tasks;
    }
    /**
     * Returns the greeting shown when the GUI starts.
     *
     * @return Greeting message.
     */
    public String getGreeting() {
        GuiUi ui = new GuiUi();
        ui.showWelcome();
        return ui.getOutput();
    }
    /**
     * Checks whether the given input is an exit command.
     *
     * @param input User command.
     * @return True if the input signals exiting the app.
     */
    public boolean isExitCommand(String input) {
        return input != null && input.trim().equalsIgnoreCase("bye");
    }
    /**
     * Processes a user input string and returns the chatbot response.
     *
     * @param input User command.
     * @return Response text to display in the GUI.
     */
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
