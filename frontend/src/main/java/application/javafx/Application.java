package application.javafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import org.tinylog.Logger;

import static javafx.scene.media.AudioClip.INDEFINITE;

/**
 * The Application class of the project.
 */
public class Application extends javafx.application.Application {

    /**
     * The background music audio clip.
     */
    public static AudioClip audioClip;

    private static Stage primaryStage;

    /**
     * The start method of the Application class.
     *
     * @param stage the stage for showing a scene
     * @throws Exception declare exceptions that may occur during the execution of the program
     */
    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        Logger.info("The application has started.");
        audioClip = new AudioClip(getClass().getResource("/music/music.wav").toString());
        audioClip.setCycleCount(INDEFINITE);
        audioClip.play();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        stage.setTitle("InsightEdu");
        stage.setResizable(false);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

}
