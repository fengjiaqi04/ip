package harden;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main JavaFX application class.
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Storage storage = new Storage("data/harden.txt");
        TaskList tasks = new TaskList();

        try {
            Task[] loaded = storage.load();
            for (Task t : loaded) {
                if (t != null) {
                    tasks.add(t);
                }
            }
        } catch (HardenException ignored) {
            // start with empty list
        }

        HardenGui hardenGui = new HardenGui(storage, tasks);
        MainWindow root = new MainWindow(hardenGui);

        Scene scene = new Scene(root, 500, 500);
        stage.setTitle("Harden");
        stage.setScene(scene);
        stage.show();
    }
}
