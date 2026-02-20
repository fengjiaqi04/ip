package harden;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main entry point of the Harden chatbot application.
 * Responsible for initializing components and running the main interaction loop.
 */
public class Harden {

    private static final String DEFAULT_SAVE_PATH = "data/harden.txt";

    private final Storage storage;
    private final Ui ui;
    private TaskList tasks;

    /**
     * Constructs the Harden chatbot.
     * Initializes UI, storage, and loads tasks from disk if available.
     */
    public Harden() {
        this.ui = new Ui();
        this.storage = new Storage(DEFAULT_SAVE_PATH);
        this.tasks = loadTasksSafely();
    }

    private TaskList loadTasksSafely() {
        try {
            Task[] loaded = storage.load();
            return new TaskList(toNonNullList(loaded));
        } catch (HardenException e) {
            ui.showError(e.getMessage());
            return new TaskList();
        }
    }

    private List<Task> toNonNullList(Task[] tasksArray) {
        List<Task> list = new ArrayList<>();
        if (tasksArray == null) {
            return list;
        }
        for (Task task : tasksArray) {
            if (task != null) {
                list.add(task);
            }
        }
        return list;
    }

    /**
     * Runs the main program loop, reading user input and executing commands
     * until an exit command is encountered.
     */
    public void run() {
        ui.showWelcome();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            if (!scanner.hasNextLine()) {
                ui.showGoodbye();
                break;
            }

            String input = scanner.nextLine();
            boolean shouldExit = handleInput(input);

            if (shouldExit) {
                ui.showGoodbye();
                break;
            }
        }
    }

    private boolean handleInput(String input) {
        try {
            Command command = Parser.parse(input);
            command.execute(tasks, ui, storage);
            return command.isExit();
        } catch (HardenException e) {
            ui.showError(e.getMessage());
            return false;
        }
    }

    /**
     * Program entry point.
     *
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) {
        new Harden().run();
    }
}
