package harden;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MainWindow extends BorderPane {

    private final VBox dialogContainer;
    private final TextField userInput;
    private final HardenGui hardenGui;

    public MainWindow(HardenGui hardenGui) {
        this.hardenGui = hardenGui;

        dialogContainer = new VBox(8);
        dialogContainer.setPadding(new Insets(10));

        ScrollPane scrollPane = new ScrollPane(dialogContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        userInput = new TextField();
        userInput.setPromptText("Type a command...");

        Button sendButton = new Button("Send");

        HBox inputBox = new HBox(8, userInput, sendButton);
        inputBox.setPadding(new Insets(10));

        setCenter(scrollPane);
        setBottom(inputBox);

        addBotText(hardenGui.getGreeting());

        sendButton.setOnAction(e -> handleInput());
        userInput.setOnAction(e -> handleInput());
    }

    private void handleInput() {
        String input = userInput.getText();
        if (input == null || input.trim().isEmpty()) {
            return;
        }

        addUserText(input);

        String response = hardenGui.getResponse(input);
        addBotText(response);

        userInput.clear();

        if (hardenGui.isExitCommand(input)) {
            Platform.exit();
        }
    }

    private void addUserText(String msg) {
        dialogContainer.getChildren().add(new Text("You: " + msg));
    }

    private void addBotText(String msg) {
        dialogContainer.getChildren().add(new Text("Harden: " + msg));
    }
}
