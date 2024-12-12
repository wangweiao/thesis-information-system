package application.javafx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.UUID;

public class RegisterController {

    private FXMLLoader fxmlLoader = new FXMLLoader();

    @FXML
    private TextField usernameTextField;

    @FXML
    private ImageView background;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private MenuButton facultyMenuButton;

    @FXML
    private Label errorLabel;

    @FXML
    private void initialize() {
        facultyMenuButton.setText("Select Faculty");

        MenuItem facultyInformatics = new MenuItem("Faculty of Informatics");
        MenuItem facultyMathematics = new MenuItem("Faculty of Mathematics");
        MenuItem facultyHumanities = new MenuItem("Faculty of Humanities");
        MenuItem facultyEconomics = new MenuItem("Faculty of Economics");

        // Add event handlers to update MenuButton text
        facultyInformatics.setOnAction(e -> facultyMenuButton.setText(facultyInformatics.getText()));
        facultyMathematics.setOnAction(e -> facultyMenuButton.setText(facultyMathematics.getText()));
        facultyHumanities.setOnAction(e -> facultyMenuButton.setText(facultyHumanities.getText()));
        facultyEconomics.setOnAction(e -> facultyMenuButton.setText(facultyEconomics.getText()));

        facultyMenuButton.getItems().addAll(facultyInformatics, facultyMathematics, facultyHumanities, facultyEconomics);
    }

    @FXML
    private void studentRegisterAction(ActionEvent event) throws IOException, URISyntaxException, InterruptedException {
        if (usernameTextField.getText().isEmpty() || passwordTextField.getText().isEmpty() || emailTextField.getText().isEmpty()
            || nameTextField.getText().isEmpty() || facultyMenuButton.getText().equals("Select Faculty")) {
            errorLabel.setText("All fields cannot be empty!");
        } else if (checkIfUsernameExists(usernameTextField.getText())) {
            errorLabel.setText("Username already exists!");
        } else {
            registerStudent(usernameTextField.getText(), passwordTextField.getText(), emailTextField.getText(),
                    nameTextField.getText(), facultyMenuButton.getText());
        }
    }

    private Long generateUniqueId() {
        UUID uuid = UUID.randomUUID();
        return Math.abs(uuid.getMostSignificantBits()); // Ensure the value is positive
    }

    private void registerStudent(String username, String password, String email, String name, String faculty) throws URISyntaxException, IOException, InterruptedException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost("http://localhost:8080/api/student");

            // Set headers
            post.setHeader("Content-Type", "application/json");

            String json = String.format("{\"username\": \"%s\", \"password\": \"%s\", \"name\": \"%s\"" +
                    ", \"email\": \"%s\", \"faculty\": \"%s\"}", username, password, name, email, faculty);


            post.setEntity(new StringEntity(json));

            // Execute the request
            try (CloseableHttpResponse response = client.execute(post)) {
                System.out.println(response.getCode());
                if (response.getCode() == 200) {
                    System.out.println("Student registered successfully!");
                    errorLabel.setText("Registration is successful!");
                } else {
                    System.out.println("Student registration failed!");
                    errorLabel.setText("Registration is failed!");
                }
                System.out.println("Status Code: " + response.getCode());
                System.out.println("Response Body: " + new String(response.getEntity().getContent().readAllBytes()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        HttpClient client = HttpClient.newHttpClient();

//        String json = String.format("{\"username\": \"%s\", \"password\": \"%s\", \"name\": \"%s\"" +
//                ", \"email\": \"%s\", \"faculty\": \"%s\"}", username, password, name, email, faculty);

//        String json = """
//                {
//                 "username": "rubi",
//                  "password": "654321",
//                  "name": "Will",
//                  "email": "will@email.com",
//                  "faculty": "Faculty of Informatics"
//                }
//                """;
//        System.out.println(json);
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(new URI("http://localhost:8080/api/student/"))
//                .header("Content-Type", "application/json")
//                .POST(HttpRequest.BodyPublishers.ofString(json))
//                .build();
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        if (response.statusCode() == 200) {
//            System.out.println("Student registered successfully!");
//            errorLabel.setText("Registration is successful!");
//        } else {
//            System.out.println("Student registration failed!");
//            errorLabel.setText("Registration is failed!");
//        }
    }

    private boolean checkIfUsernameExists(String username) {


        return false;
//        HttpClient client = HttpClient.newHttpClient();
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(new URI("http://localhost:8080/api/login/student/" + ))
//                .header("Content-Type", "application/json")
//                .POST(HttpRequest.BodyPublishers.ofString(json))
//                .build();
//
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//        if (response.statusCode() == 200) {
//            return true;
//        } else {
//            return false;
//        }
    }

    @FXML
    private void backAction(ActionEvent event) throws IOException {
        fxmlLoader.setLocation(getClass().getResource("/fxml/login.fxml"));
        Parent root = fxmlLoader.load();
//            fxmlLoader.<GameController>getController().setFirstPlayerName(firstPlayerNameTextField.getText());
//            fxmlLoader.<GameController>getController().setSecondPlayerName(secondPlayerNameTextField.getText());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
//            scene.getStylesheets().add(getClass().getResource("/css/ui.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void supervisorRegisterAction(ActionEvent event) {

    }

}
