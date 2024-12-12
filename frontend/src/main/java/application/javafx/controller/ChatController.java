package application.javafx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatController {

    private FXMLLoader fxmlLoader = new FXMLLoader();

    @FXML
    private WebView webView;

    @FXML
    public void initialize() {
        // Initialize the WebEngine
        WebEngine webEngine = webView.getEngine();

        // Load the local HTML file
        webEngine.load(getClass().getResource("/saddle/index.html").toString());
    }

    @FXML
    private void backAction(ActionEvent actionEvent) throws IOException {
        fxmlLoader.setLocation(getClass().getResource("/fxml/student_index.fxml"));
        Parent root = fxmlLoader.load();
//            fxmlLoader.<GameController>getController().setFirstPlayerName(firstPlayerNameTextField.getText());
//            fxmlLoader.<GameController>getController().setSecondPlayerName(secondPlayerNameTextField.getText());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
//            scene.getStylesheets().add(getClass().getResource("/css/ui.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
