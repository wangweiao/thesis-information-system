package application.javafx.controller;

import application.model.Thesis;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.UserSession;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class MyTopicController {

    private FXMLLoader fxmlLoader = new FXMLLoader();

    @FXML
    private TextField titleTextField;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private Button backButton;

    @FXML
    private TextField supervisorTextField;

    @FXML
    private Button cancelButton;

    private final Gson gson = new Gson();

    private final String currentStudentName = UserSession.getInstance().getUsername();

    private Thesis currentThesis;


    @FXML
    private void initialize() throws URISyntaxException, IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://127.0.0.1:8000/theses/"))
                .header("Content-Type", "application/json")
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();


        Type thesisListType = new TypeToken<ArrayList<Thesis>>() {}.getType();
        ArrayList<Thesis> thesisList = gson.fromJson(responseBody, thesisListType);
        for (Thesis thesis : thesisList) {
            if (thesis.getStudentName().equals(currentStudentName)) {
                currentThesis = thesis;
                titleTextField.setText(thesis.getTitle());
                descriptionTextArea.setText(thesis.getDescription());
                supervisorTextField.setText(thesis.getSupervisorName());
                break;
            }
            System.out.println(thesis);
        }
    }

    @FXML
    private void backAction(ActionEvent event) throws IOException {
        fxmlLoader.setLocation(getClass().getResource("/fxml/student_index.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void cancelAction(ActionEvent event) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://127.0.0.1:8000/theses/" + currentThesis.getId() + "?student_name=null"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.noBody())
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();

        System.out.println(responseBody);
        cancelButton.setDisable(true);
        titleTextField.clear();
        descriptionTextArea.clear();
        supervisorTextField.clear();
        openPopup();
    }

    private void openPopup() {
        Stage popupStage = new Stage();

        // Add content to the pop-up window
        Button btnClose = new Button("Close");
        Text msgTextField = new Text("Success!");
        btnClose.setOnAction(e -> popupStage.close());  // Close the pop-up when the button is clicked

        // Use a VBox layout to stack the button and text vertically
        VBox popupRoot = new VBox(10);  // 10 is the spacing between elements
        popupRoot.setAlignment(Pos.CENTER);  // Center all the children inside VBox

        popupRoot.getChildren().addAll(msgTextField, btnClose);

        Scene popupScene = new Scene(popupRoot, 400, 150);  // Width 400, Height 150
        popupStage.setTitle("Success");
        popupStage.setScene(popupScene);

        // Show the pop-up window
        popupStage.show();
    }




}