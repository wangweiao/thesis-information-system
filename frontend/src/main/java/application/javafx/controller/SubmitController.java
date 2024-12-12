package application.javafx.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.UserSession;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SubmitController {

    private FXMLLoader fxmlLoader = new FXMLLoader();

    @FXML
    private TextField titleTextField;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private Button submitButton;

    @FXML
    private Button analyzeButton;

    @FXML
    private TextArea suggestionTextArea;


    @FXML
    private void backAction(ActionEvent event) throws IOException {
        fxmlLoader.setLocation(getClass().getResource("/fxml/supervisor_index.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void handleSubmitButton(ActionEvent event) throws URISyntaxException, IOException, InterruptedException {
        String title = titleTextField.getText();
        String description = descriptionTextArea.getText();
        String currentUsername = UserSession.getInstance().getUsername();

        HttpClient client = HttpClient.newHttpClient();
        String json = String.format("{\"title\": \"%s\", \"description\": \"%s\", \"supervisor_name\": \"%s\", \"student_name\": \"null\", \"year\": 2024}"
                , title, description, currentUsername);

        System.out.println(json);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://127.0.0.1:8000/theses/"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Log the response
        System.out.println("Response Code: " + response.statusCode());
        System.out.println("Response Body: " + response.body());


    }

    @FXML
    private void analyzeAction(ActionEvent event) {
        // Disable the submit button and show loading state
        analyzeButton.setDisable(true);
        suggestionTextArea.setText("Loading... Please wait.");

        // Extract input values
        String title = titleTextField.getText();
        String description = descriptionTextArea.getText();

        // Create a background task to send the POST request
        Task<Void> postRequestTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                sendPostRequest(title, description);
                return null;
            }
        };


//        // Set task completion handlers
//        postRequestTask.setOnSucceeded(e -> {
//            // Re-enable the submit button and update the suggestion area
//            submitButton.setDisable(false);
//            suggestionTextArea.setText("Request completed successfully.");
//        });
//
//        postRequestTask.setOnFailed(e -> {
//            // Re-enable the submit button and show an error message
//            submitButton.setDisable(false);
//            suggestionTextArea.setText("An error occurred while processing your request.");
//        });

        // Run the task in a background thread
        new Thread(postRequestTask).start();
    }

    private void sendPostRequest(String title, String description) throws URISyntaxException, IOException, InterruptedException {
        String prompt = "Please answer in 50 words. What do you think about the following thesis with title '" + title + "'" + " and description '" + description + "'.";
        System.out.println(prompt);
        HttpClient client = HttpClient.newHttpClient();
        String json = String.format("{\"model\": \"llama3.2\", \"prompt\": \"%s\", \"stream\": false}", prompt.replaceAll("\\p{Cntrl}", ""));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:11434/api/generate"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Log the response
        System.out.println("Response Code: " + response.statusCode());
        System.out.println("Response Body: " + response.body());

        JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
        String responseString = jsonObject.get("response").getAsString();
        suggestionTextArea.setText(responseString);

    }


}