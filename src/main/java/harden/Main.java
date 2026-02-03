package harden;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * The main JavaFX application class.
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Label label = new Label("Hello from Harden GUI!");
        Scene scene = new Scene(new StackPane(label), 400, 300);

        stage.setTitle("Harden");
        stage.setScene(scene);
        stage.show();
    }
}
