package application.javafx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.tinylog.Logger;
import util.UserSession;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Controls the appearance and behaviours of the opening page.
 */
public class LoginController {

    private FXMLLoader fxmlLoader = new FXMLLoader();

    @FXML
    private TextField usernameTextField;

    @FXML
    private ImageView background;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Label errorLabel;

    @FXML
    private void studentLoginAction(ActionEvent actionEvent) throws IOException, URISyntaxException, InterruptedException {
        Logger.info("Username: '{}', Password: '{}'", usernameTextField.getText(), passwordTextField.getText());
        if (usernameTextField.getText().isEmpty() || passwordTextField.getText().isEmpty()) {
            errorLabel.setText("Please enter username and password!");
        } else if (authorizeStudent(usernameTextField.getText(), passwordTextField.getText())) {

            UserSession.getInstance().setUsername(usernameTextField.getText());
            String currentUsername = UserSession.getInstance().getUsername();
            Logger.info("Logged in user with username: '{}'", currentUsername);

            fxmlLoader.setLocation(getClass().getResource("/fxml/student_index.fxml"));
            Parent root = fxmlLoader.load();
//            fxmlLoader.<GameController>getController().setFirstPlayerName(firstPlayerNameTextField.getText());
//            fxmlLoader.<GameController>getController().setSecondPlayerName(secondPlayerNameTextField.getText());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
//            scene.getStylesheets().add(getClass().getResource("/css/ui.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } else {
            errorLabel.setText("Username or password is incorrect!");
        }
    }

    @FXML
    private void supervisorLoginAction(ActionEvent event) throws IOException, URISyntaxException, InterruptedException {
        if (usernameTextField.getText().isEmpty() || passwordTextField.getText().isEmpty()) {
            errorLabel.setText("Please enter username and password!");
        } else if (authorizeSupervisor(usernameTextField.getText(), passwordTextField.getText())) {
            UserSession.getInstance().setUsername(usernameTextField.getText());
            String currentUsername = UserSession.getInstance().getUsername();
            Logger.info("Logged in user with username: '{}'", currentUsername);

            fxmlLoader.setLocation(getClass().getResource("/fxml/supervisor_index.fxml"));
            Parent root = fxmlLoader.load();
//            fxmlLoader.<GameController>getController().setFirstPlayerName(firstPlayerNameTextField.getText());
//            fxmlLoader.<GameController>getController().setSecondPlayerName(secondPlayerNameTextField.getText());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
//            scene.getStylesheets().add(getClass().getResource("/css/ui.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
//            Logger.info("The user's name is set to {}, loading game scene", firstPlayerNameTextField.getText());
        } else {
            errorLabel.setText("Username or password is incorrect!");
        }
    }



    private boolean authorizeStudent(String username, String password) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String json = String.format("{\"username\": \"%s\", \"password\": \"%s\"}", username, password);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/api/login/student"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return true;
        } else {
            return false;
        }

        // Log the response
//        System.out.println("Response Code: " + response.statusCode());
//        System.out.println("Response Body: " + response.body());
//
//        JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
//        String responseString = jsonObject.get("response").getAsString();
//        suggestionTextArea.setText(responseString);

    }

    private boolean authorizeSupervisor(String username, String password) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String json = String.format("{\"username\": \"%s\", \"password\": \"%s\"}", username, password);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/api/login/supervisor"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return true;
        } else {
            return false;
        }

        // Log the response
//        System.out.println("Response Code: " + response.statusCode());
//        System.out.println("Response Body: " + response.body());
//
//        JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
//        String responseString = jsonObject.get("response").getAsString();
//        suggestionTextArea.setText(responseString);

    }

}
