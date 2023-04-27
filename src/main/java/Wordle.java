import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Wordle extends Application {

    private String answer = "WORDS";

    @FXML
    VBox mainVBox, gridVBox;

    @FXML
    AnchorPane anchPane;

    @FXML
    Label mainLabel;

    @FXML
    HBox row1, row2, row3, row4, row5, row6;

    @FXML
    TextField r1c1, r1c2, r1c3, r1c4, r1c5;

    @FXML
    TextField r2c1, r2c2, r2c3, r2c4, r2c5;

    @FXML
    TextField r3c1, r3c2, r3c3, r3c4, r3c5;

    @FXML
    TextField r4c1, r4c2, r4c3, r4c4, r4c5;

    @FXML
    TextField r5c1, r5c2, r5c3, r5c4, r5c5;

    @FXML
    TextField r6c1, r6c2, r6c3, r6c4, r6c5;
    
    @Override
    public void start(Stage stage) {}

    @FXML
    private void typed() {

        displayResult(r1c1, r1c2, 0); // Row 1 text fields
        displayResult(r1c2, r1c3, 1);
        displayResult(r1c3, r1c4, 2);
        displayResult(r1c4, r1c5, 3);
        displayResult(r1c5, r2c1, 4);

        displayResult(r2c1, r2c2, 0); //  Row 2 text fields
        displayResult(r2c2, r2c3, 1);
        displayResult(r2c3, r2c4, 2);
        displayResult(r2c4, r2c5, 3);
        displayResult(r2c5, r3c1, 4);

        displayResult(r3c1, r3c2, 0); // Row 3 text fields
        displayResult(r3c2, r3c3, 1);
        displayResult(r3c3, r3c4, 2);
        displayResult(r3c4, r3c5, 3);
        displayResult(r3c5, r4c1, 4);

        displayResult(r4c1, r4c2, 0); // Row 4 text fields
        displayResult(r4c2, r4c3, 1);
        displayResult(r4c3, r4c4, 2);
        displayResult(r4c4, r4c5, 3);
        displayResult(r4c5, r5c1, 4);

        displayResult(r5c1, r5c2, 0); // Row 5 text fields
        displayResult(r5c2, r5c3, 1);
        displayResult(r5c3, r5c4, 2);
        displayResult(r5c4, r5c5, 3);
        displayResult(r5c5, r6c1, 4);

        displayResult(r6c1, r6c2, 0); // Row 6 text fields
        displayResult(r6c2, r6c3, 1);
        displayResult(r6c3, r6c4, 2);
        displayResult(r6c4, r6c5, 3);
        displayResult(r6c5, 4);

    }

    private String checkLetter(String input, int index) {
        char inpChar = input.toCharArray()[0];
        if (inpChar == answer.charAt(index)) {
            return "-fx-background-color: lightgreen;";
        } else {
            if (index == 0) {
                if (inpChar == answer.charAt(1)
                    || inpChar == answer.charAt(2)
                    || inpChar == answer.charAt(3)
                    || inpChar == answer.charAt(4))
                return "-fx-background-color: yellow;";
            } else if (index == 1) {
                if (inpChar == answer.charAt(0)
                    || inpChar == answer.charAt(2)
                    || inpChar == answer.charAt(3)
                    || inpChar == answer.charAt(4))
                return "-fx-background-color: yellow;";
            } else if (index == 2) {
                if (inpChar == answer.charAt(0)
                    || inpChar == answer.charAt(1)
                    || inpChar == answer.charAt(3)
                    || inpChar == answer.charAt(4))
                return "-fx-background-color: yellow;";
            } else if (index == 3) {
                if (inpChar == answer.charAt(0)
                    || inpChar == answer.charAt(1)
                    || inpChar == answer.charAt(2)
                    || inpChar == answer.charAt(4))
                return "-fx-background-color: yellow;";
            } else if (index == 4) {
                if (inpChar == answer.charAt(0)
                    || inpChar == answer.charAt(1)
                    || inpChar == answer.charAt(2)
                    || inpChar == answer.charAt(3))
                return "-fx-background-color: yellow;";
            }
        }
        return "-fx-background-color: lightgray;";
    }

    private void displayResult(TextField currentTF, TextField nextTF, int index) {
        if (currentTF.isFocused() && currentTF.getText().length() == 1) {
            currentTF.setStyle(checkLetter(currentTF.getText(), index));
            nextTF.requestFocus();
        }
    }

    private void displayResult(TextField currentTF, int index) {
        if (currentTF.isFocused() && currentTF.getText().length() == 1) {
            currentTF.setStyle(checkLetter(currentTF.getText(), index));
        }
    }

    public static void main(String[] args) {
        launch();
    }

}