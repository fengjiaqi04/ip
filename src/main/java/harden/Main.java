package harden;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 * The main JavaFX application class.
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
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

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
        AnchorPane root = fxmlLoader.load();

        MainWindow controller = fxmlLoader.getController();
        controller.setHardenGui(hardenGui);

        Scene scene = new Scene(root);
        stage.setTitle("Harden");
        stage.setScene(scene);
        stage.show();
    }
}
