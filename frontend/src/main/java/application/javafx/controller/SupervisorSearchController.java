package application.javafx.controller;

import application.model.Thesis;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
import java.util.List;

public class SupervisorSearchController {

    private FXMLLoader fxmlLoader = new FXMLLoader();

    private Gson gson = new Gson();

    @FXML
    private TableColumn<Thesis, String> title;

    @FXML
    private TableColumn<Thesis, Integer> year;

    @FXML
    private TableColumn<Thesis, String> supervisor;

    @FXML
    private TableView<Thesis> thesisTable;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private Button applyButton;

    private ArrayList<Thesis> thesisList;

    private String currentThesisId;

    private final String currentStudentName = UserSession.getInstance().getUsername();

    @FXML
    private void initialize() throws URISyntaxException, IOException, InterruptedException {

        thesisTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Get selected item
                Thesis selectedThesis = thesisTable.getSelectionModel().getSelectedItem();

                if (selectedThesis != null) {
                    // Display the description in the TextArea
                    descriptionArea.setText(selectedThesis.getDescription());
                    currentThesisId = selectedThesis.getId();
                }
            }
        });

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://127.0.0.1:8000/theses/"))
                .header("Content-Type", "application/json")
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();


        Type thesisListType = new TypeToken<ArrayList<Thesis>>() {
        }.getType();
        thesisList = gson.fromJson(responseBody, thesisListType);
        for (Thesis thesis : thesisList) {
            if (thesis.getStudentName().equals(currentStudentName)) {
                applyButton.setDisable(true);
            }
            System.out.println(thesis);
        }


        List<Thesis> winnerResults = thesisList.stream().toList();


        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        year.setCellValueFactory(new PropertyValueFactory<>("year"));
        supervisor.setCellValueFactory(new PropertyValueFactory<>("supervisorName"));
        ObservableList<Thesis> observableResult = FXCollections.observableArrayList();
        observableResult.addAll(winnerResults);
        thesisTable.setItems(observableResult);
    }


    @FXML
    private void backAction(ActionEvent event) throws IOException {
        fxmlLoader.setLocation(getClass().getResource("/fxml/supervisor_index.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }




}
