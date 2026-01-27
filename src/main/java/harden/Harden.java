package harden;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main entry point of the Harden chatbot application.
 * Responsible for initializing components and running the main interaction loop.
 */
public class Harden {

    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

    /**
     * Constructs the Harden chatbot.
     * Initializes UI, storage, and loads tasks from disk if available.
     */
    public Harden() {
        ui = new Ui();
        storage = new Storage("data/harden.txt");

        try {
            Task[] loaded = storage.load();
            ArrayList<Task> list = new ArrayList<>();
            if (loaded != null) {
                for (Task t : loaded) {
                    if (t != null) {
                        list.add(t);
                    }
                }
            }
            tasks = new TaskList(list);
        } catch (HardenException e) {
            ui.showError(e.getMessage());
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main program loop, reading user input and executing commands
     * until an exit command is encountered.
     */
    public void run() {
        ui.showWelcome();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                if (!scanner.hasNextLine()) {
                    ui.showGoodbye();
                    break;
                }

                String input = scanner.nextLine();
                Command command = Parser.parse(input);
                command.execute(tasks, ui, storage);

                if (command.isExit()) {
                    ui.showGoodbye();
                    break;
                }
            } catch (HardenException e) {
                ui.showError(e.getMessage());
            }
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
