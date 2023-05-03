import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class WordleController {

    private String answerLow, answerUp;

    private List<String> validWords;

    private List<HBox> rows;
    private List<List<TextField>> allTextFields;

    private String defaultTextStyle = "-fx-background-color: black; -fx-border-color: gray; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 25.5; -fx-display-caret: false;";
    private String greenTextStyle = "-fx-background-color: #246935; -fx-text-fill: white; -fx-font-weight: bold;";
    private String yellowTextStyle = "-fx-background-color: #9e9823; -fx-text-fill: white; -fx-font-weight: bold;";
    private String grayTextStyle = "-fx-background-color: #2b2a2a; -fx-text-fill: white; -fx-font-weight: bold;";

    @FXML
    private VBox mainVBox, gridVBox;

    @FXML
    private AnchorPane anchPane;

    @FXML
    private Label mainLabel;

    /*
     * @FXML
     * private HBox row1, row2, row3, row4, row5, row6;
     * 
     * @FXML
     * private TextField r1c1, r1c2, r1c3, r1c4, r1c5;
     * 
     * @FXML
     * private TextField r2c1, r2c2, r2c3, r2c4, r2c5;
     * 
     * @FXML
     * private TextField r3c1, r3c2, r3c3, r3c4, r3c5;
     * 
     * @FXML
     * private TextField r4c1, r4c2, r4c3, r4c4, r4c5;
     * 
     * @FXML
     * private TextField r5c1, r5c2, r5c3, r5c4, r5c5;
     * 
     * @FXML
     * private TextField r6c1, r6c2, r6c3, r6c4, r6c5;
     */

    @FXML
    private Button playAgainB;

    public WordleController() {
    }

    /**
     * The initialize method is called after the constructor is run and
     * any @FXML annotated fields are populated. This is required because the
     * constructor does NOT have access to the @FXML annotated fields.
     */
    @FXML
    public void initialize() throws IOException {

        validWords = new ArrayList<String>();
        rows = new ArrayList<HBox>();
        allTextFields = new ArrayList<List<TextField>>();

        try {
            File wordList = new File(App.class.getResource("wordleWordListClean.txt").getPath());
            Scanner reader = new Scanner(wordList);
            while (reader.hasNextLine()) {
                validWords.add(reader.nextLine());
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }

        Random rand = new Random();
        answerLow = validWords.get(rand.nextInt(validWords.size()));
        answerUp = answerLow.toUpperCase();
        // System.out.println(answerUp);

        for (int i = 0; i < gridVBox.getChildren().size(); i++) {
            rows.add((HBox) gridVBox.getChildren().get(i));
            allTextFields.add(new ArrayList<TextField>());
        }
        for (int j = 0; j < rows.size(); j++) {
            for (int i = 0; i < rows.get(j).getChildren().size(); i++) {
                allTextFields.get(j).add((TextField) rows.get(j).getChildren().get(i));
            }
        }
    }

    @FXML
    private void whenTyped() {
        for (int i = 0; i <= 5; i++) {
            if (i == 5)
                checkWord(i, allTextFields.get(0).get(0));
            else
                checkWord(i, allTextFields.get(i + 1).get(0));
        }
    }

    @FXML
    private void whenClicked() {
        resetGame();
    }

    // private boolean whenKeyPressed(KeyEvent Ke) {
    //     if (Ke.getCode() == KeyCode.ENTER) {
    //         return true;
    //     } else {
    //         return false;
    //     }
        
    //     // currentTextFields.get(rowIndex).setOnKeyTyped(events -> {
    //     //     if (events.getCode() == KeyCode.ENTER) {
    //     //         boolean enterPressed = true;
    //     //     }
    //     // });
    // }

    /*
     * @FXML
     * private void switchToSecondary() throws IOException {
     * App.setRoot("secondary");
     * }
     */

    private void checkWord(int rowIndex, TextField nextTF) {

        /*
         * for (int j = 0; j < allTextFields.size(); j++) {
         * for (int i = 0; i < allTextFields.get(j).size(); i++) { // Setting all fields
         * in the row to editable
         * if (allTextFields.get(j).get(i).isFocused()) {
         * allTextFields.get(j).get(i).setEditable(true);
         * } else {
         * allTextFields.get(j).get(i).setEditable(false);
         * }
         * }
         * }
         */

        // boolean enterPressed = false;
        List<TextField> currentTextFields = allTextFields.get(rowIndex);
        String guess = "";
        for (int j = 0; j < currentTextFields.size(); j++) {
            guess += currentTextFields.get(j).getText().toLowerCase();
            // currentTextFields.get(j).setStyle("-fx-display-caret-false;");
        }
        char[] guessCharArr = guess.toCharArray();

        for (int i = 0; i < 4; i++) {
            displayText(currentTextFields.get(i), currentTextFields.get(i + 1));
        }
        displayText(currentTextFields.get(4), nextTF);

        /*
         * switch (rowIndex) {
         * case 0:
         * for (int i = 1; i < allTextFields.size(); i++) {
         * for (int j = 0; j < allTextFields.get(i).size(); j++) {
         * allTextFields.get(i).get(j).setEditable(false);
         * }
         * }
         * break;
         * case 1:
         * for (int i = 2; i < allTextFields.size(); i++) {
         * for (int j = 0; j < allTextFields.get(i).size(); j++) {
         * allTextFields.get(i).get(j).setEditable(false);
         * }
         * }
         * break;
         * case 2:
         * for (int i = 3; i < allTextFields.size(); i++) {
         * for (int j = 0; j < allTextFields.get(i).size(); j++) {
         * allTextFields.get(i).get(j).setEditable(false);
         * }
         * }
         * break;
         * case 3:
         * for (int i = 4; i < allTextFields.size(); i++) {
         * for (int j = 0; j < allTextFields.get(i).size(); j++) {
         * allTextFields.get(i).get(j).setEditable(false);
         * }
         * }
         * break;
         * case 4:
         * for (int j = 0; j < allTextFields.get(5).size(); j++) {
         * allTextFields.get(5).get(j).setEditable(false);
         * }
         * break;
         * 
         * default:
         * break;
         * }
         */

        // Checking if all of the text fields in the row are filled
        if (!(currentTextFields.get(0).getText().equals("")
                || currentTextFields.get(1).getText().equals("")
                || currentTextFields.get(2).getText().equals("")
                || currentTextFields.get(3).getText().equals("")
                || currentTextFields.get(4).getText().equals(""))) {
            if (!(validWords.contains(guess))) { // The guess is an invalid word
                for (TextField tf : currentTextFields) {
                    tf.clear();
                    final Duration timeWrong = Duration.millis(65);
                    TranslateTransition transTrans1 = new TranslateTransition(timeWrong);
                    transTrans1.setFromX(-65f);
                    transTrans1.setToX(0f);
                    transTrans1.setAutoReverse(true);
                    TranslateTransition transTrans2 = new TranslateTransition(timeWrong);
                    transTrans2.setFromX(65f);
                    transTrans2.setToX(0f);
                    SequentialTransition seqT = new SequentialTransition(tf, transTrans1, transTrans2);
                    seqT.play();
                }
                currentTextFields.get(0).requestFocus();
                mainLabel.setText("Not a valid word");

            } else if (guess.equals(answerLow)) { // The guess is the answer
                for (TextField tf : currentTextFields) {
                    tf.setStyle(greenTextStyle);
                    tf.setEditable(false);
                    final Duration timeCorrect = Duration.millis(1300);
                    RotateTransition rotTrans = new RotateTransition(timeCorrect);
                    rotTrans.setByAngle(360f);
                    SequentialTransition seqT = new SequentialTransition(tf, rotTrans);
                    seqT.play();
                }
                mainLabel.setText("YOU WON!");
                playAgainB.requestFocus();
            } else { // The guess is a valid word but not the answer
                for (int i = 0; i < currentTextFields.size(); i++) {
                    currentTextFields.get(i).setStyle(colorWord(guessCharArr, currentTextFields.get(i), i));
                    currentTextFields.get(i).setEditable(false);
                }
                if (!currentTextFields.get(4).getId().equals("r6c5"))
                    mainLabel.setText("Wordle");
            }
        }
    }

    private String colorWord(char[] guessCharArrIn, TextField tf, int index) {
        if (guessCharArrIn[index] == answerLow.charAt(index)) {
            return greenTextStyle;
        } else {
            if (index == 0) {
                if (guessCharArrIn[index] == answerLow.charAt(1)
                        || guessCharArrIn[index] == answerLow.charAt(2)
                        || guessCharArrIn[index] == answerLow.charAt(3)
                        || guessCharArrIn[index] == answerLow.charAt(4))
                    return yellowTextStyle;
            } else if (index == 1) {
                if (guessCharArrIn[index] == answerLow.charAt(0)
                        || guessCharArrIn[index] == answerLow.charAt(2)
                        || guessCharArrIn[index] == answerLow.charAt(3)
                        || guessCharArrIn[index] == answerLow.charAt(4))
                    return yellowTextStyle;
            } else if (index == 2) {
                if (guessCharArrIn[index] == answerLow.charAt(0)
                        || guessCharArrIn[index] == answerLow.charAt(1)
                        || guessCharArrIn[index] == answerLow.charAt(3)
                        || guessCharArrIn[index] == answerLow.charAt(4))
                    return yellowTextStyle;
            } else if (index == 3) {
                if (guessCharArrIn[index] == answerLow.charAt(0)
                        || guessCharArrIn[index] == answerLow.charAt(1)
                        || guessCharArrIn[index] == answerLow.charAt(2)
                        || guessCharArrIn[index] == answerLow.charAt(4))
                    return yellowTextStyle;
            } else if (index == 4) {
                if (guessCharArrIn[index] == answerLow.charAt(0)
                        || guessCharArrIn[index] == answerLow.charAt(1)
                        || guessCharArrIn[index] == answerLow.charAt(2)
                        || guessCharArrIn[index] == answerLow.charAt(3))
                    return yellowTextStyle;
            }
        }
        return grayTextStyle;
    }

    private void displayText(TextField currentTF, TextField nextTF) {
        if (currentTF.isFocused() && currentTF.getText().length() == 1) {
            currentTF.setText(currentTF.getText().toUpperCase());

            final Duration timeNext = Duration.millis(35);
            TranslateTransition transTrans = new TranslateTransition(timeNext);
            transTrans.setFromY(0f);
            transTrans.setToY(-20f);
            transTrans.setCycleCount(2);
            transTrans.setAutoReverse(true);
            SequentialTransition seqT = new SequentialTransition(currentTF, transTrans);
            seqT.play();

            if (currentTF.getId().equals("r6c5")) {
                mainLabel.setText("It was " + answerUp);
                playAgainB.requestFocus();
            } else {
                nextTF.requestFocus();
            }
        }
    }

    private void resetRow(int rowIndex) {
        List<TextField> currentTextFields = allTextFields.get(rowIndex);
        for (int j = 0; j < currentTextFields.size(); j++) {
            currentTextFields.get(j).clear();
            currentTextFields.get(j).setStyle(defaultTextStyle);
            currentTextFields.get(j).setEditable(true);
        }
    }

    private void resetGame() {
        for (int i = 0; i < rows.size(); i++) {
            resetRow(i);
        }
        mainLabel.setText("Wordle");
        allTextFields.get(0).get(0).requestFocus();

        Random rand = new Random();
        answerLow = validWords.get(rand.nextInt(validWords.size()));
        answerUp = answerLow.toUpperCase();
        // System.out.println(answerUp);
    }

}