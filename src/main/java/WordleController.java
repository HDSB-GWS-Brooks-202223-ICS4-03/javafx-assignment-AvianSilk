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

    private List<HBox> rows;
    private List<List<TextField>> allTFs;

    @FXML
    private VBox mainVBox, gridVBox;

    @FXML
    private AnchorPane anchPane;

    @FXML
    private Label mainLabel;

    @FXML
    private HBox row1, row2, row3, row4, row5, row6;

    /* @FXML
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
    private TextField r6c1, r6c2, r6c3, r6c4, r6c5; */

    @FXML
    private Button replayB;

    @FXML
    public void initialize() throws IOException {

        validWords = new ArrayList<String>();
        rows = new ArrayList<HBox>();
        allTFs = new ArrayList<List<TextField>>();

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
            allTFs.add(new ArrayList<TextField>());
        }

        for (int j = 0; j < rows.size(); j++) {
            for (int i = 0; i < rows.get(j).getChildren().size(); i += 2) {
                allTFs.get(j).add((TextField) rows.get(j).getChildren().get(i));
            }
        }
    }

    @FXML
    private void whenTyped() {
        checkWord(0, allTFs.get(1).get(0));
        checkWord(1, allTFs.get(2).get(0));
        checkWord(2, allTFs.get(3).get(0));
        checkWord(3, allTFs.get(4).get(0));
        checkWord(4, allTFs.get(5).get(0));
        checkWord(5, allTFs.get(0).get(0));
    }

    @FXML
    private void whenClicked() {
        resetGame();
    }

    private void checkWord(int rowIndex, TextField nextTF) {

        List<TextField> currentTextFields = allTFs.get(rowIndex);
        String guess = "";
        for (int j = 0; j < currentTextFields.size(); j++) {
            guess += currentTextFields.get(j).getText().toLowerCase();
        }
        char[] guessCharArr = guess.toCharArray();

        displayText(currentTextFields.get(0), currentTextFields.get(1));
        displayText(currentTextFields.get(1), currentTextFields.get(2));
        displayText(currentTextFields.get(2), currentTextFields.get(3));
        displayText(currentTextFields.get(3), currentTextFields.get(4));
        displayText(currentTextFields.get(4), nextTF);

        // Checking if all of the text fields in the row are filled
        if (!(currentTextFields.get(0).getText().equals("")
                || currentTextFields.get(1).getText().equals("")
                || currentTextFields.get(2).getText().equals("")
                || currentTextFields.get(3).getText().equals("")
                || currentTextFields.get(4).getText().equals(""))) {
            if (!(validWords.contains(guess))) { // The guess is an invalid word
                for (TextField tf : currentTextFields) {
                    tf.clear();
                }
                currentTextFields.get(0).requestFocus();
                mainLabel.setText("Not a valid word");
            } else if (guess.equals(answerLow)) { // The guess is the answer
                for (TextField tf : currentTextFields) {
                    tf.setStyle("-fx-background-color: lightgreen;");
                    tf.setEditable(false);
                }
                mainLabel.setText("YOU WON!");
                replayB.requestFocus();
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

    private void resetRow(int rowIndex) {
        List<TextField> currentTextFields = allTFs.get(rowIndex);
        for (int j = 0; j < currentTextFields.size(); j++) {
            currentTextFields.get(j).clear();
            currentTextFields.get(j).setStyle("-fx-background-color: white;");
            currentTextFields.get(j).setStyle("-fx-border-color: lightgray;");
            currentTextFields.get(j).setEditable(true);
        }
    }

    private void resetGame() {
        for (int i = 0; i < rows.size(); i++) {
            resetRow(i);
        }
        mainLabel.setText("Wordle");
        allTFs.get(0).get(0).requestFocus();

        Random rand = new Random();
        answerLow = validWords.get(rand.nextInt(validWords.size()));
        answerUp = answerLow.toUpperCase();
    }

}