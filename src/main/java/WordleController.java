import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class WordleController {

    private String answerLow, answerUp;

    private List<String> validWords;

    @FXML
    private VBox mainVBox, gridVBox;

    @FXML
    private AnchorPane anchPane;

    @FXML
    private Label mainLabel;

    @FXML
    private HBox row1, row2, row3, row4, row5, row6;

    @FXML
    private TextField r1c1, r1c2, r1c3, r1c4, r1c5;

    @FXML
    private TextField r2c1, r2c2, r2c3, r2c4, r2c5;

    @FXML
    private TextField r3c1, r3c2, r3c3, r3c4, r3c5;

    @FXML
    private TextField r4c1, r4c2, r4c3, r4c4, r4c5;

    @FXML
    private TextField r5c1, r5c2, r5c3, r5c4, r5c5;

    @FXML
    private TextField r6c1, r6c2, r6c3, r6c4, r6c5;

    @FXML
    private Button replayB;

    @FXML
    public void initialize() throws IOException {

        validWords = new ArrayList<String>();
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

        /* List <HBox> rows = new ArrayList<HBox>();
        List <ArrayList<TextField>> tFields = new ArrayList<ArrayList<TextField>>();
        for (int i = 0; i < gridVBox.getChildren().size(); i++) {
        rows.add((HBox) gridVBox.getChildren().get(i));
        // tFields.add((ArrayList<TextField>) (rows.get(i).getChildren()));
        }
        for (int k = 0; k < rows.size(); k++) {
        for (int l = 0; l < rows.get(k).getChildren().size(); l++) {
        tFields.get(k).add((TextField) rows.get(k).getChildren().get(l));
        }
        }
        for (HBox hb : rows) {
        System.out.println("HBox -" + "\t" + hb.getId());
        }
        for (int i = 0; i < 6; i++) {
        rows.get(i).getChildren();
        for (int j = 0; j < 5; j++) {
        ;
        }
        } */
    }

    @FXML
    private void whenTyped() {
        checkWord(row1, r2c1);
        checkWord(row2, r3c1);
        checkWord(row3, r4c1);
        checkWord(row4, r5c1);
        checkWord(row5, r6c1);
        checkWord(row6, r1c1);
    }

    @FXML
    private void whenClicked() {
        resetGame();
    }

    private void checkWord(HBox row, TextField nextTF) {

        String guess = "";
        List<TextField> textFields = new ArrayList<TextField>();
        for (int i = 0; i < row.getChildren().size(); i++) {
            textFields.add((TextField) row.getChildren().get(i));
            guess += textFields.get(i).getText().toLowerCase();
        }
        char[] guessCharArr = guess.toCharArray();

        /*
         * ArrayList <Character> guessCharArrList = new ArrayList<Character>();
         * for (char c : guessCharArr) {
         * guessCharArrList.add(c);
         * System.out.println(c);
         * }
         * Character ch = Character.MIN_VALUE;
         */

        displayText(textFields.get(0), textFields.get(1));
        displayText(textFields.get(1), textFields.get(2));
        displayText(textFields.get(2), textFields.get(3));
        displayText(textFields.get(3), textFields.get(4));
        displayText(textFields.get(4), nextTF);

        if (!(textFields.get(0).getText().equals("")
                || textFields.get(1).getText().equals("")
                || textFields.get(2).getText().equals("")
                || textFields.get(3).getText().equals("")
                || textFields.get(4).getText().equals(""))) { // Checking if all of the text fields in the row are filled
            if (!(validWords.contains(guess))) { // The guess is an invalid word
                for (TextField tf : textFields) {
                    tf.clear();
                }
                textFields.get(0).requestFocus();
                mainLabel.setText("Not a valid word");
            } else if (guess.equals(answerLow)) { // The guess is the answer
                for (int i = 0; i < textFields.size(); i++) {
                    textFields.get(i).setStyle("-fx-background-color: lightgreen;");
                }
                mainLabel.setText("YOU WON!");
                replayB.requestFocus();
            } else { // The guess is a valid word but not the answer
                for (int i = 0; i < textFields.size(); i++) {
                    textFields.get(i).setStyle(colorWord(guessCharArr, textFields.get(i), i));
                }
                if (!textFields.get(4).getId().equals("r6c5"))
                    mainLabel.setText("Wordle");
            }
        }
    }

    private String colorWord(char[] guessCharArrIn, TextField tf, int index) {
        if (guessCharArrIn[index] == answerLow.charAt(index)) {
            return "-fx-background-color: lightgreen;";
        } else {
            if (index == 0) {
                if (guessCharArrIn[index] == answerLow.charAt(1)
                        || guessCharArrIn[index] == answerLow.charAt(2)
                        || guessCharArrIn[index] == answerLow.charAt(3)
                        || guessCharArrIn[index] == answerLow.charAt(4))
                    return "-fx-background-color: yellow;";
            } else if (index == 1) {
                if (guessCharArrIn[index] == answerLow.charAt(0)
                        || guessCharArrIn[index] == answerLow.charAt(2)
                        || guessCharArrIn[index] == answerLow.charAt(3)
                        || guessCharArrIn[index] == answerLow.charAt(4))
                    return "-fx-background-color: yellow;";
            } else if (index == 2) {
                if (guessCharArrIn[index] == answerLow.charAt(0)
                        || guessCharArrIn[index] == answerLow.charAt(1)
                        || guessCharArrIn[index] == answerLow.charAt(3)
                        || guessCharArrIn[index] == answerLow.charAt(4))
                    return "-fx-background-color: yellow;";
            } else if (index == 3) {
                if (guessCharArrIn[index] == answerLow.charAt(0)
                        || guessCharArrIn[index] == answerLow.charAt(1)
                        || guessCharArrIn[index] == answerLow.charAt(2)
                        || guessCharArrIn[index] == answerLow.charAt(4))
                    return "-fx-background-color: yellow;";
            } else if (index == 4) {
                if (guessCharArrIn[index] == answerLow.charAt(0)
                        || guessCharArrIn[index] == answerLow.charAt(1)
                        || guessCharArrIn[index] == answerLow.charAt(2)
                        || guessCharArrIn[index] == answerLow.charAt(3))
                    return "-fx-background-color: yellow;";
            }
        }
        return "-fx-background-color: lightgray;";
    }

    private void displayText(TextField currentTF, TextField nextTF) {
        if (currentTF.isFocused() && currentTF.getText().length() == 1) {
            currentTF.setText(currentTF.getText().toUpperCase());
            if (currentTF.getId().equals("r6c5")) {
                mainLabel.setText("It was " + answerUp);
                replayB.requestFocus();
            } else {
                nextTF.requestFocus();
            }
        }
    }

    private void resetRow(HBox row) {
        List<TextField> textFields = new ArrayList<TextField>();
        for (int i = 0; i < row.getChildren().size(); i++) {
            textFields.add((TextField) row.getChildren().get(i));
            textFields.get(i).clear();
            textFields.get(i).setStyle("-fx-background-color: white;");
            textFields.get(i).setStyle("-fx-border-color: lightgray;");
        }
    }

    private void resetGame() {
        List <HBox> rows = new ArrayList<HBox>();
        for (int i = 0; i < gridVBox.getChildren().size(); i++) {
            rows.add((HBox) gridVBox.getChildren().get(i));
            resetRow(rows.get(i));
        }

        mainLabel.setText("Wordle");
        r1c1.requestFocus();

        Random rand = new Random();
        answerLow = validWords.get(rand.nextInt(validWords.size()));
        answerUp = answerLow.toUpperCase();
    }

}