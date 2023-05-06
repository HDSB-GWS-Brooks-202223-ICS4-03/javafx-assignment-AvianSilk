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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * The controller for the Wordle game
 * 
 * @author Venkata Adapala (@AvianSilk)
 * @version 05-05-2023
 */
public class WordleController {

    /**
     * The VBox that contains all of the Hboxes acting as the rows
     */
    @FXML
    private VBox gridVBox;
    /**
     * The main (and only) label in the game
     */
    @FXML
    private Label mainLabel;
    /**
     * The button used for resetting the game
     */
    @FXML
    private Button playAgainB;
    /**
     * The lowercase and uppercase versions of the game's answer
     */
    private String answerLow, answerUp;
    /**
     * The list of words that are considered valid (pulled from the list)
     */
    private List<String> validWords;
    /**
     * A list of HBoxes containing each row
     */
    private List<HBox> rows;
    /**
     * A list which contains lists of textfields i.e. it is like a list
     * of rows which are actually lists of textfields
     */
    private List<List<TextField>> allTextFields;
    /**
     * The default formatting of each text field
     */
    private String defaultTextStyle = "-fx-background-color: black; -fx-border-color: gray; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 25.5; -fx-display-caret: false;";
    /**
     * The formatting of a text field when the letter exists in the answer at the
     * right position
     */
    private String greenTextStyle = "-fx-background-color: #246935; -fx-text-fill: white; -fx-font-weight: bold;";
    /**
     * The formatting of a text field when the letter exists in the answer at a
     * different position
     */
    private String yellowTextStyle = "-fx-background-color: #9e9823; -fx-text-fill: white; -fx-font-weight: bold;";
    /**
     * The formatting of a text field when the letter does not exist in the answer
     */
    private String grayTextStyle = "-fx-background-color: #2b2a2a; -fx-text-fill: white; -fx-font-weight: bold;";

    /**
     * The constructor of the class
     */
    public WordleController() {
    }

    /**
     * The initialize method is called after the constructor is run and
     * any @FXML annotated fields are populated. This is required because the
     * constructor does NOT have access to the @FXML annotated fields.
     */
    @FXML
    public void initialize() throws IOException {

        // Instantiating important lists that were declared previously
        validWords = new ArrayList<String>();
        rows = new ArrayList<HBox>();
        allTextFields = new ArrayList<List<TextField>>();

        // Using a scanner to go through the list of valid words and adding them to the
        // list 'validWords'
        try {
            File wordList = new File(Wordle.class.getResource("wordleWordListClean.txt").getPath());
            Scanner reader = new Scanner(wordList);
            while (reader.hasNextLine()) {
                validWords.add(reader.nextLine());
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }

        // Generating an answer by picking a random word from the list
        Random rand = new Random();
        answerLow = validWords.get(rand.nextInt(validWords.size()));
        answerUp = answerLow.toUpperCase();

        // Adding all Hboxes to the 'rows' list while adding arraylists of text fields
        // in 'allTextFields'
        for (int i = 0; i < gridVBox.getChildren().size(); i++) {
            rows.add((HBox) gridVBox.getChildren().get(i));
            allTextFields.add(new ArrayList<TextField>());
        }

        // Adding lists of text fields to the arraylists added in 'allTextFields' previously,
        // thus creating a two-dimensional list i.e. a list of lists of text fields (based on row)
        for (int j = 0; j < rows.size(); j++) {
            for (int i = 0; i < rows.get(j).getChildren().size(); i++) {
                allTextFields.get(j).add((TextField) rows.get(j).getChildren().get(i));
            }
        }
    }

    /**
     * The method that runs when the user types in any of the textfields i.e. it
     * is the whenTyped method of each text field.
     * It contains a for-loop that loops through all of the rows checking each word.
     */
    @FXML
    private void whenTyped() {
        for (int i = 0; i <= 5; i++) {
            if (!(i == 5))
                // Checking the words in rows 1-5
                checkWord(i, allTextFields.get(i + 1).get(0));
            else
                // For the last row, there is no text field to be focused after the current text field,
                // therefore, the second parameter is just a placeholder
                checkWord(i, allTextFields.get(0).get(0));
        }
    }

    /**
     * The action that must take place when the "Play Again" button is pressed
     * (resetting the game)
     */
    @FXML
    private void whenClicked() {
        resetGame(); // resets the entire game
    }

    /**
     * A method that checks each word i.e. each row, accessed by the index of the
     * HBox in the 'rows' list
     * It involves displaying text in each text field correctly, along with changing
     * the colors appropriately
     * 
     * @param rowIndex The index of the current row i.e. the current HBox in the 'rows' list
     * @param nextTF   The text field to shift focus to after the word has been assessed
     */
    private void checkWord(int rowIndex, TextField nextTF) {

        // Creating a temporary list of text fields that are currently being used
        // using the rowIndex and 'allTextFields'
        List<TextField> currentTextFields = allTextFields.get(rowIndex);

        String guess = ""; // The word that is currently guessed

        // Adding the text from each text field in the row to 'guess'
        // in order to get the guessed word
        for (int j = 0; j < currentTextFields.size(); j++) {
            guess += currentTextFields.get(j).getText().toLowerCase();
        }

        char[] guessCharArr = guess.toCharArray(); // Converting the guessed word to a char array

        // Displaying text correctly (refer to the Javadoc about 'displayText()') in
        // each text field in the row
        for (int i = 0; i < 4; i++) {
            displayText(currentTextFields.get(i), currentTextFields.get(i + 1));
        }
        displayText(currentTextFields.get(4), nextTF);

        // The main logic for what to do with the guessed word is as follows:

        // Checking if all of the text fields in the row are filled
        if (!(currentTextFields.get(0).getText().equals("")
                || currentTextFields.get(1).getText().equals("")
                || currentTextFields.get(2).getText().equals("")
                || currentTextFields.get(3).getText().equals("")
                || currentTextFields.get(4).getText().equals(""))) {

            // If the guess is an invalid word
            if (!(validWords.contains(guess))) {
                for (TextField tf : currentTextFields) { // Clearing all the text fields and playing the jitter animation
                    tf.clear();
                    jitterAnimation(tf, 65, -65f, 65f, 0f);
                }
                currentTextFields.get(0).requestFocus(); // Shifting the focus back to the first text field
                mainLabel.setText("Not a valid word");
            }

            // If the guess is the answer
            else if (guess.equals(answerLow)) {
                for (TextField tf : currentTextFields) {
                    tf.setStyle(greenTextStyle); // Coloring the text fields green
                    tf.setEditable(false); // Ensuring that the text fields are no longer editable
                    rotateAnimation(tf, 1300, 360f); // Playing the rotating animation
                }
                mainLabel.setText("YOU WON!");
                playAgainB.requestFocus(); // Shifting focus to the 'Play Again' button
            }

            // If the guess is a valid word but not the answer
            else {
                // Using the 'checkLetter' method to determine the appropriate color for each text field
                for (int i = 0; i < currentTextFields.size(); i++) {
                    currentTextFields.get(i).setStyle(checkLetter(guessCharArr, currentTextFields.get(i), i));
                    currentTextFields.get(i).setEditable(false);
                }
                // If the last text field in the current row is the very last text field in the game
                if (!currentTextFields.get(4).getId().equals(allTextFields.get(5).get(4).getId()))
                    mainLabel.setText("Wordle");
            }
        }
    }

    /**
     * A method that takes care of everything that is necessary for displaying the
     * text in a text field (setting the text to uppercase, animating it and shifting
     * focus to the next text field)
     * 
     * @param currentTF The text field that is currently in focus
     * @param nextTF    The text field to which the focus must be shifted later
     */
    private void displayText(TextField currentTF, TextField nextTF) {

        // If the current text field is in focus and it contains some text
        if (currentTF.isFocused() && currentTF.getText().length() == 1) {
            currentTF.setText(currentTF.getText().toUpperCase()); // Setting the text inside to uppercase
            bounceAnimation(currentTF, 35, 0f, -20f, 2);

            // If the current text field is the very last text field, it means that the user
            // was unable to guess the answer. So, display the word and shift focus to the button
            if (currentTF.getId().equals(allTextFields.get(5).get(4).getId())) {
                mainLabel.setText(answerUp); // Displaying the answer
                playAgainB.requestFocus();
            } else {
                nextTF.requestFocus(); // If the current text field is not the last one, then just shift focus to the next one
            }
        }
    }

    /**
     * A method that checks each letter in word and assigns it a color
     * based on the existence of the letter in the answer, and its position
     * 
     * @param guessCharArrIn The guessed word converted into a char array
     * @param tf             The text field that is currently being checked
     * @param index          The index of the answer to be checked i.e. the position of the text field in the row
     * @return A string that has the appropriate style characteristics (mainly color) for the text field
     */
    private String checkLetter(char[] guessCharArrIn, TextField tf, int index) {

        if (guessCharArrIn[index] == answerLow.charAt(index)) { // If the current letter appears in the answer at the same position
            return greenTextStyle; // Coloring the letter green
        } else {
            if (index == 0) { // Depending on the index, checking if the letter appears elsewhere in the word
                if (guessCharArrIn[index] == answerLow.charAt(1)
                        || guessCharArrIn[index] == answerLow.charAt(2)
                        || guessCharArrIn[index] == answerLow.charAt(3)
                        || guessCharArrIn[index] == answerLow.charAt(4))
                    return yellowTextStyle; // Coloring the letter yellow
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
        return grayTextStyle; // Color the letter gray if it does not appear in the answer at all
    }

    /**
     * An animation that makes a text field appear to bounce momentarily
     * 
     * @param tf         The text field that is to be animated
     * @param duration   The duration of the animation
     * @param fromYPos   The starting Y-axis position of the text field
     * @param toYPos     The ending Y-axis position of the text field
     * @param cycleCount The number of cycles of the transition
     */
    private void bounceAnimation(TextField tf, int duration, float fromYPos, float toYPos, int cycleCount) {
        final Duration time = Duration.millis(duration);

        TranslateTransition transTrans = new TranslateTransition(time);
        transTrans.setFromY(fromYPos);
        transTrans.setToY(toYPos);
        transTrans.setCycleCount(cycleCount);
        transTrans.setAutoReverse(true);

        SequentialTransition seqT = new SequentialTransition(tf, transTrans);
        seqT.play();
    }

    /**
     * An animation that makes a text field appear to jitter sideways
     * 
     * @param tf        The text field that is to be animated
     * @param duration  The duration of the animation
     * @param fromXPos1 The starting X-axis position of the text field in the first half of the transition
     * @param fromXPos2 The starting X-axis position of the text field in the second half of the transition
     * @param toXPos    The ending X-axis position of the text field
     */
    private void jitterAnimation(TextField tf, int duration, float fromXPos1, float fromXPos2, float toXPos) {
        final Duration time = Duration.millis(duration);

        TranslateTransition transTrans1 = new TranslateTransition(time);
        transTrans1.setFromX(fromXPos1);
        transTrans1.setToX(toXPos);
        transTrans1.setAutoReverse(true);

        TranslateTransition transTrans2 = new TranslateTransition(time);
        transTrans2.setFromX(fromXPos2);
        transTrans2.setToX(toXPos);

        SequentialTransition seqT = new SequentialTransition(tf, transTrans1, transTrans2);
        seqT.play();
    }

    /**
     * An animation that makes a text field appear to rotate
     * 
     * @param tf       The text field that is to be animated
     * @param duration The duration of the animation
     * @param angle    The angle by which the text field should rotate
     */
    private void rotateAnimation(TextField tf, int duration, float angle) {
        final Duration time = Duration.millis(duration);

        RotateTransition rotTrans = new RotateTransition(time);
        rotTrans.setByAngle(angle);

        SequentialTransition seqT = new SequentialTransition(tf, rotTrans);
        seqT.play();
    }

    /**
     * A method to reset a row, allowing the user to guess a word again
     * 
     * @param rowIndex The index of the row that is to be reset i.e. the HBox in the 'rows' list
     */
    private void resetRow(int rowIndex) {
        List<TextField> currentTextFields = allTextFields.get(rowIndex); // List of the text fields currently in use
        for (int i = 0; i < currentTextFields.size(); i++) { // Resetting all text fields in the row
            currentTextFields.get(i).clear();
            currentTextFields.get(i).setStyle(defaultTextStyle);
            currentTextFields.get(i).setEditable(true);

            rotateAnimation(currentTextFields.get(i), 500, 180f);
            currentTextFields.get(i).setRotate(180); // Resetting the text fields to the original angle after the animation
        }
    }

    /**
     * A method that resets the entire game by resetting every row, the main label
     * and generating a new random answer
     */
    private void resetGame() {
        for (int i = 0; i < rows.size(); i++) { // Resetting every row
            resetRow(i);
        }
        mainLabel.setText("Wordle");
        allTextFields.get(0).get(0).requestFocus(); // Shifting focus back the very first text field

        // Generating a new answer
        Random rand = new Random();
        answerLow = validWords.get(rand.nextInt(validWords.size()));
        answerUp = answerLow.toUpperCase();
    }

}