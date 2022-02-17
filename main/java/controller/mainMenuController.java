package controller;

import com.schedulingapp.Main;
import dao.customerQuery;
import dao.userQuery;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class mainMenuController implements Initializable {

    @FXML
    private Button customerButton;
    @FXML
    private Button appointButton;
    @FXML
    private Button reportsButton;
    @FXML
    private Label welcomeUser;

    @FXML
    private void customerAction() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("customerView.fxml"));
        Parent root = fxmlLoader.load();


        Stage customerView = new Stage();
        customerView.setTitle("Customer View");
        customerView.setScene(new Scene(root));
        customerView.initModality(Modality.APPLICATION_MODAL);
        customerView.show();

        Stage stage = (Stage) customerButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void appointAction() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("appointmentView.fxml"));
        Parent root = fxmlLoader.load();


        Stage customerView = new Stage();
        customerView.setTitle("Appointment View");
        customerView.setScene(new Scene(root));
        customerView.initModality(Modality.APPLICATION_MODAL);
        customerView.show();

        Stage stage = (Stage) appointButton.getScene().getWindow();
        stage.close();

    }

    @FXML
    private void reportAction() throws IOException {

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User user = userQuery.getCurrentUser();
        welcomeUser.setText("Welcome " + user.getUsername());
    }
}
