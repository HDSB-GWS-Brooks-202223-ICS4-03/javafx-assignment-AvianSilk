import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Wordle extends Application {

    @Override
    public void start(Stage stage) {

        //Create a layout pane to act at the main container for all UI elements
        VBox rootPane = new VBox();
        //Align anything added to this layout pane so it looks nicer
        rootPane.setAlignment(Pos.CENTER);

        //Create the scene and display it.
        Scene scene = new Scene(rootPane, 512, 614);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}