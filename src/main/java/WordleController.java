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

    private List <String> validWords;

    // private boolean isReset;

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
        
        // isReset = false;
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
    }

    @FXML
    private void whenTyped() {
        checkWord(r1c1, r1c2, r1c3, r1c4, r1c5, r2c1);
        checkWord(r2c1, r2c2, r2c3, r2c4, r2c5, r3c1);
        checkWord(r3c1, r3c2, r3c3, r3c4, r3c5, r4c1);
        checkWord(r4c1, r4c2, r4c3, r4c4, r4c5, r5c1);
        checkWord(r5c1, r5c2, r5c3, r5c4, r5c5, r6c1);
        checkWord(r6c1, r6c2, r6c3, r6c4, r6c5, r1c1); // r1c1 is just a placeholder

    }

    @FXML
    private void whenClicked() {
        resetGame();
    }

    private void checkWord(TextField tf1, TextField tf2, TextField tf3, TextField tf4, TextField tf5, TextField nextTF) {

        String guess = tf1.getText().toLowerCase() + tf2.getText().toLowerCase() + tf3.getText().toLowerCase() + tf4.getText().toLowerCase() + tf5.getText().toLowerCase();
        char[] guessCharArr = guess.toCharArray();

        /* ArrayList <Character> guessCharArrList = new ArrayList<Character>();
        for (char c : guessCharArr) {
            guessCharArrList.add(c);
            System.out.println(c);
        }
        Character ch = Character.MIN_VALUE; */

        displayText(tf1, tf2);
        displayText(tf2, tf3);
        displayText(tf3, tf4);
        displayText(tf4, tf5);
        displayText(tf5, nextTF);

        if (!(tf1.getText().equals("")
                || tf2.getText().equals("")
                || tf3.getText().equals("")
                || tf4.getText().equals("")
                || tf5.getText().equals(""))) { // Checking if all of the text fields in the row are filled
            if (!(validWords.contains(guess))) { // The guess is an invalid word
                tf1.clear();
                tf2.clear();
                tf3.clear();
                tf4.clear();
                tf5.clear();
                tf1.requestFocus();
                mainLabel.setText("Not a valid word");
            } else if (guess.equals(answerLow)) { // The guess is the answer
                tf1.setStyle("-fx-background-color: lightgreen;");
                tf2.setStyle("-fx-background-color: lightgreen;");
                tf3.setStyle("-fx-background-color: lightgreen;");
                tf4.setStyle("-fx-background-color: lightgreen;");
                tf5.setStyle("-fx-background-color: lightgreen;");
                mainLabel.setText("YOU WON!");
                mainLabel.requestFocus();
            } else { // The guess is a valid word but not the answer
                tf1.setStyle(colorWord(guessCharArr, tf1, 0));
                tf2.setStyle(colorWord(guessCharArr, tf2, 1));
                tf3.setStyle(colorWord(guessCharArr, tf3, 2));
                tf4.setStyle(colorWord(guessCharArr, tf4, 3));
                tf5.setStyle(colorWord(guessCharArr, tf5, 4));
                if (!tf5.getId().equals("r6c5"))
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
                mainLabel.requestFocus();
            } else {
                nextTF.requestFocus();
            }
        }
    }

    private void resetText(TextField tf1, TextField tf2, TextField tf3, TextField tf4, TextField tf5) {
        tf1.clear();
        tf2.clear();
        tf3.clear();
        tf4.clear();
        tf5.clear();

        tf1.setStyle("-fx-background-color: white;");
        tf2.setStyle("-fx-background-color: white;");
        tf3.setStyle("-fx-background-color: white;");
        tf4.setStyle("-fx-background-color: white;");
        tf5.setStyle("-fx-background-color: white;");

        tf1.setStyle("-fx-border-color: lightgray");
        tf2.setStyle("-fx-border-color: lightgray");
        tf3.setStyle("-fx-border-color: lightgray");
        tf4.setStyle("-fx-border-color: lightgray");
        tf5.setStyle("-fx-border-color: lightgray");
    }

    private void resetGame() {
        resetText(r1c1, r1c2, r1c3, r1c4, r1c5);
        resetText(r2c1, r2c2, r2c3, r2c4, r2c5);
        resetText(r3c1, r3c2, r3c3, r3c4, r3c5);
        resetText(r4c1, r4c2, r4c3, r4c4, r4c5);
        resetText(r5c1, r5c2, r5c3, r5c4, r5c5);
        resetText(r6c1, r6c2, r6c3, r6c4, r6c5);

        mainLabel.setText("Wordle");
        r1c1.requestFocus();

        Random rand  = new Random();
        answerLow = validWords.get(rand.nextInt(validWords.size()));
        answerUp = answerLow.toUpperCase();
    }

}