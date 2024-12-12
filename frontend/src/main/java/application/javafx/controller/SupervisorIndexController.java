package application.javafx.controller;

import application.javafx.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controls the appearance and behaviours of the opening page.
 */
public class SupervisorIndexController {

    private FXMLLoader fxmlLoader = new FXMLLoader();

    @FXML
    private void chatAction(ActionEvent actionEvent) {
        try {
            // Load the FXML file for the WebView

            fxmlLoader.setLocation(getClass().getResource("/fxml/chat.fxml"));
            Parent root = fxmlLoader.load();

            // Create a new Scene with the FXML layout
            Scene webViewScene = new Scene(root, 800, 600);

            // Get the primary stage and set the new scene
            Stage stage = (Stage) Application.getPrimaryStage();
            stage.setScene(webViewScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void searchAction(ActionEvent actionEvent) throws IOException {
        fxmlLoader.setLocation(getClass().getResource("/fxml/supervisor_search.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void submitAction(ActionEvent actionEvent) throws IOException {
        fxmlLoader.setLocation(getClass().getResource("/fxml/submit.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void signOutAction(ActionEvent actionEvent) throws IOException {
        fxmlLoader.setLocation(getClass().getResource("/fxml/login.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }


    @FXML
    private void topicAction(ActionEvent actionEvent) throws IOException {
        fxmlLoader.setLocation(getClass().getResource("/fxml/supervisor_topics.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }



}

