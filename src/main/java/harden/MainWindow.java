package harden;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * Controller for MainWindow.fxml.
 */
public class MainWindow {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox dialogContainer;

    @FXML
    private TextField userInput;

    @FXML
    private Button sendButton;

    private HardenGui hardenGui;

    private Image userImage;
    private Image hardenImage;
    /**
     * Initializes the main window after the FXML fields are injected.
     * Sets up auto-scrolling and loads the user/bot avatar images.
     */
    @FXML
    public void initialize() {
        // Auto-scroll to bottom when new dialogs are added
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        // Load images from resources
        userImage = new Image(getClass().getResourceAsStream("/images/User.png"));
        hardenImage = new Image(getClass().getResourceAsStream("/images/Harden.png"));
    }

    /**
     * Injects the HardenGui instance and shows the startup greeting.
     *
     * @param hardenGui The GUI adapter for Harden.
     */
    public void setHardenGui(HardenGui hardenGui) {
        this.hardenGui = hardenGui;

        String greeting = hardenGui.getGreeting();
        dialogContainer.getChildren().add(
                DialogBox.getHardenDialog(greeting, new ImageView(hardenImage))
        );
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input == null || input.trim().isEmpty()) {
            return;
        }

        dialogContainer.getChildren().add(
                DialogBox.getUserDialog(input, new ImageView(userImage))
        );

        String response = hardenGui.getResponse(input);
        dialogContainer.getChildren().add(
                DialogBox.getHardenDialog(response, new ImageView(hardenImage))
        );

        userInput.clear();

        if (hardenGui.isExitCommand(input)) {
            Platform.exit();
        }
    }
}
