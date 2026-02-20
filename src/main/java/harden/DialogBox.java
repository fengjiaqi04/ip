package harden;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.IOException;

/**
 * A dialog box consisting of an ImageView to represent the speaker and a Label
 * containing text from the speaker.
 */
public class DialogBox extends HBox {

    @FXML
    private Label dialog;

    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, ImageView img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(DialogBox.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        dialog.setText(text);
        dialog.setWrapText(true);
        dialog.setMaxWidth(320);

        displayPicture.setImage(img.getImage());
    }

    private void flip() {
        setAlignment(Pos.TOP_LEFT);
        getChildren().clear();
        getChildren().addAll(displayPicture, dialog);
    }

    public static DialogBox getUserDialog(String text, ImageView img) {
        DialogBox box = new DialogBox(text, img);
        box.setAlignment(Pos.TOP_RIGHT);
        return box;
    }

    public static DialogBox getHardenDialog(String text, ImageView img) {
        DialogBox box = new DialogBox(text, img);
        box.flip();
        return box;
    }
}
