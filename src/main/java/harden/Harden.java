package harden;

import java.util.ArrayList;
import java.util.Scanner;

public class Harden {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

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

    public void run() {
        ui.showWelcome();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                // ✅ prevents NoSuchElementException (EOF) when running via Gradle
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

    public static void main(String[] args) {
        new Harden().run();
    }
}
