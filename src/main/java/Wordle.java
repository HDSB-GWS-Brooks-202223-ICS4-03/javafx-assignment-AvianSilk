import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Wordle {

    private String answer;

    List <String> validWords;

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
        int wordNumber = rand.nextInt(validWords.size());
        answer = validWords.get(wordNumber).toUpperCase();
        System.out.println("Answer - " + answer);
    }

    @FXML
    private void whenTyped() {

        displayUpperCase(r1c1, r1c2, r1c3, r1c4, r1c5);
        checkWord(r1c1, r1c2, r1c3, r1c4, r1c5, r2c1);

        displayUpperCase(r2c1, r2c2, r2c3, r2c4, r2c5);
        checkWord(r2c1, r2c2, r2c3, r2c4, r2c5, r3c1);

        displayUpperCase(r3c1, r3c2, r3c3, r3c4, r3c5);
        checkWord(r3c1, r3c2, r3c3, r3c4, r3c5, r4c1);

        displayUpperCase(r4c1, r4c2, r4c3, r4c4, r4c5);
        checkWord(r4c1, r4c2, r4c3, r4c4, r4c5, r5c1);

        displayUpperCase(r5c1, r5c2, r5c3, r5c4, r5c5);
        checkWord(r5c1, r5c2, r5c3, r5c4, r5c5, r6c1);

        displayUpperCase(r6c1, r6c2, r6c3, r6c4, r6c5);
        checkWord(r6c1, r6c2, r6c3, r6c4, r6c5, r1c1);

        /* displayResult(r1c1, r1c2, 0); // Row 1 text fields
        displayResult(r1c2, r1c3, 1);
        displayResult(r1c3, r1c4, 2);
        displayResult(r1c4, r1c5, 3);
        displayResult(r1c5, r2c1, 4);
        validateWord(r1c1, r1c2, r1c3, r1c4, r1c5);

        displayResult(r2c1, r2c2, 0); //  Row 2 text fields
        displayResult(r2c2, r2c3, 1);
        displayResult(r2c3, r2c4, 2);
        displayResult(r2c4, r2c5, 3);
        displayResult(r2c5, r3c1, 4);
        validateWord(r2c1, r2c2, r2c3, r2c4, r2c5);

        displayResult(r3c1, r3c2, 0); // Row 3 text fields
        displayResult(r3c2, r3c3, 1);
        displayResult(r3c3, r3c4, 2);
        displayResult(r3c4, r3c5, 3);
        displayResult(r3c5, r4c1, 4);
        validateWord(r3c1, r3c2, r3c3, r3c4, r3c5);

        displayResult(r4c1, r4c2, 0); // Row 4 text fields
        displayResult(r4c2, r4c3, 1);
        displayResult(r4c3, r4c4, 2);
        displayResult(r4c4, r4c5, 3);
        displayResult(r4c5, r5c1, 4);
        validateWord(r4c1, r4c2, r4c3, r4c4, r4c5);

        displayResult(r5c1, r5c2, 0); // Row 5 text fields
        displayResult(r5c2, r5c3, 1);
        displayResult(r5c3, r5c4, 2);
        displayResult(r5c4, r5c5, 3);
        displayResult(r5c5, r6c1, 4);
        validateWord(r5c1, r5c2, r5c3, r5c4, r5c5);

        displayResult(r6c1, r6c2, 0); // Row 6 text fields
        displayResult(r6c2, r6c3, 1);
        displayResult(r6c3, r6c4, 2);
        displayResult(r6c4, r6c5, 3);
        displayResultAndShowAnswer(r6c5, 4);
        validateWord(r6c1, r6c2, r6c3, r6c4, r6c5);

        if (checkIfAnswered(r1c1, r1c2, r1c3, r1c4, r1c5)
                || checkIfAnswered(r2c1, r2c2, r2c3, r2c4, r2c5)
                || checkIfAnswered(r3c1, r3c2, r3c3, r3c4, r3c5)
                || checkIfAnswered(r4c1, r4c2, r4c3, r4c4, r4c5)
                || checkIfAnswered(r5c1, r5c2, r5c3, r5c4, r5c5)
                || checkIfAnswered(r6c1, r6c2, r6c3, r6c4, r6c5)) {
            mainLabel.setText("YOU WON!");
            mainLabel.requestFocus();
        } */

    }

    /* private boolean validateWord(TextField tf1, TextField tf2, TextField tf3, TextField tf4, TextField tf5) {
        // hbox.getChildren();
        // String guess = "";
        // for (int i = 0; i < hbox.getChildren().size(); i++) {
        //     guess += hbox.getChildren().get(i);
        // }
        String guess = tf1.getText().toLowerCase() + tf2.getText().toLowerCase() + tf3.getText().toLowerCase() + tf4.getText().toLowerCase() + tf5.getText().toLowerCase();
        return validWords.contains(guess);
    } */

    /* private String checkLetter(String input, int index) {
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
    } */

    private void displayUpperCase(TextField tf1, TextField tf2, TextField tf3, TextField tf4, TextField tf5) {
        tf1.setText(tf1.getText().toUpperCase());
        tf2.setText(tf2.getText().toUpperCase());
        tf3.setText(tf3.getText().toUpperCase());
        tf4.setText(tf4.getText().toUpperCase());
        tf5.setText(tf5.getText().toUpperCase());
    }

    private void checkWord(TextField tf1, TextField tf2, TextField tf3, TextField tf4, TextField tf5, TextField nextTF) {

        String guess = tf1.getText().toLowerCase() + tf2.getText().toLowerCase() + tf3.getText().toLowerCase() + tf4.getText().toLowerCase() + tf5.getText().toLowerCase();
        char[] guessCharArr = guess.toCharArray();

        // System.out.println(guess);

        if (guess.length() == 5) {
            if (!(validWords.contains(guess))) { // The guess is an invalid word
                tf1.setText("");
                tf2.setText("");
                tf3.setText("");
                tf4.setText("");
                tf5.setText("");
                // tf1.requestFocus();
            } else if (guess.equals(answer)) { // The guess is the answer
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
                if (!nextTF.getId().equals("r1c1")) {
                    nextTF.requestFocus();
                } else {
                    mainLabel.setText("It was " + answer);
                }
            }
        }
    }

    private String colorWord(char[] guessCharArrIn, TextField tf, int index) {
        if (guessCharArrIn[index] == answer.charAt(index)) {
            return "-fx-background-color: lightgreen;";
        } else {
            if (index == 0) {
                if (guessCharArrIn[index] == answer.charAt(1)
                    || guessCharArrIn[index] == answer.charAt(2)
                    || guessCharArrIn[index] == answer.charAt(3)
                    || guessCharArrIn[index] == answer.charAt(4))
                return "-fx-background-color: yellow;";
            } else if (index == 1) {
                if (guessCharArrIn[index] == answer.charAt(0)
                    || guessCharArrIn[index] == answer.charAt(2)
                    || guessCharArrIn[index] == answer.charAt(3)
                    || guessCharArrIn[index] == answer.charAt(4))
                return "-fx-background-color: yellow;";
            } else if (index == 2) {
                if (guessCharArrIn[index] == answer.charAt(0)
                    || guessCharArrIn[index] == answer.charAt(1)
                    || guessCharArrIn[index] == answer.charAt(3)
                    || guessCharArrIn[index] == answer.charAt(4))
                return "-fx-background-color: yellow;";
            } else if (index == 3) {
                if (guessCharArrIn[index] == answer.charAt(0)
                    || guessCharArrIn[index] == answer.charAt(1)
                    || guessCharArrIn[index] == answer.charAt(2)
                    || guessCharArrIn[index] == answer.charAt(4))
                return "-fx-background-color: yellow;";
            } else if (index == 4) {
                if (guessCharArrIn[index] == answer.charAt(0)
                    || guessCharArrIn[index] == answer.charAt(1)
                    || guessCharArrIn[index] == answer.charAt(2)
                    || guessCharArrIn[index] == answer.charAt(3))
                return "-fx-background-color: yellow;";
            }
        }
        return "-fx-background-color: lightgray;";
    }

    /* private boolean checkIfAnswered(TextField tf1, TextField tf2, TextField tf3, TextField tf4, TextField tf5) {
        return ((tf1.getStyle() == "-fx-background-color: lightgreen;")
        && (tf2.getStyle() == "-fx-background-color: lightgreen;")
        && (tf3.getStyle() == "-fx-background-color: lightgreen;")
        && (tf4.getStyle() == "-fx-background-color: lightgreen;")
        && (tf5.getStyle() == "-fx-background-color: lightgreen;"));
    } */

    /* private void displayResult(TextField currentTF, TextField nextTF, int index) {
        if (currentTF.isFocused() && currentTF.getText().length() == 1) {
            currentTF.setText(currentTF.getText().toUpperCase());
            currentTF.setStyle(checkLetter(currentTF.getText(), index));
            nextTF.requestFocus();
        }
    } */

    /* private void displayResultAndShowAnswer(TextField currentTF, int index) {
        if (currentTF.isFocused() && currentTF.getText().length() == 1) {
            currentTF.setText(currentTF.getText().toUpperCase());
            currentTF.setStyle(checkLetter(currentTF.getText(), index));
            mainLabel.setText("It was " + answer);
            mainLabel.requestFocus();
        }
    } */
}