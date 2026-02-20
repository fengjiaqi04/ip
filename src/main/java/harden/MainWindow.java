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

/**
 * Main GUI window for Harden.
 * Keeps UI wiring (input -> response) in one place.
 */
public class MainWindow extends BorderPane {

    private static final int SPACING = 8;
    private static final int PADDING = 10;

    private static final String PROMPT_TEXT = "Type a command...";
    private static final String SEND_BUTTON_TEXT = "Send";

    private static final String USER_PREFIX = "You: ";
    private static final String BOT_PREFIX = "Harden: ";

    private final VBox dialogContainer;
    private final TextField userInput;
    private final HardenGui hardenGui;

    public MainWindow(HardenGui hardenGui) {
        this.hardenGui = hardenGui;

        this.dialogContainer = createDialogContainer();
        ScrollPane scrollPane = createScrollPane(dialogContainer);

        this.userInput = new TextField();
        this.userInput.setPromptText(PROMPT_TEXT);

        Button sendButton = new Button(SEND_BUTTON_TEXT);
        HBox inputBox = createInputBox(userInput, sendButton);

        setCenter(scrollPane);
        setBottom(inputBox);

        addBotText(hardenGui.getGreeting());

        sendButton.setOnAction(e -> handleInput());
        userInput.setOnAction(e -> handleInput());
    }

    private VBox createDialogContainer() {
        VBox container = new VBox(SPACING);
        container.setPadding(new Insets(PADDING));
        return container;
    }

    private ScrollPane createScrollPane(VBox content) {
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.vvalueProperty().bind(content.heightProperty());
        return scrollPane;
    }

    private HBox createInputBox(TextField input, Button sendButton) {
        HBox inputBox = new HBox(SPACING, input, sendButton);
        inputBox.setPadding(new Insets(PADDING));
        return inputBox;
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
        addText(USER_PREFIX + msg);
    }

    private void addBotText(String msg) {
        addText(BOT_PREFIX + msg);
    }

    private void addText(String text) {
        dialogContainer.getChildren().add(new Text(text));
    }
}
