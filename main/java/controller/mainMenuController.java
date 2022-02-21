package controller;

import com.schedulingapp.Main;
import dao.appointmentQuery;
import dao.customerQuery;
import dao.userQuery;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Main Menu Controller that controls the main menu screen
 *
 * @author ronneildobbins
 */
public class mainMenuController implements Initializable {

    /**
     * Takes to customer view screen
     */
    @FXML
    private Button customerButton;

    /**
     * Takes to appointment view screen
     */
    @FXML
    private Button appointButton;

    /**
     * Takes to reports view screen
     */
    @FXML
    private Button reportsButton;

    /**
     *  Label that welcomes the user
     */
    @FXML
    private Label welcomeUser;

    /**
     *
     * Navigates to the customer view screen
     * @throws IOException if it can't load the customer view screen
     */
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

    /**
     * Navigates to the appointment view screen
     * @throws IOException if it can't load the appointment view screen
     */
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

    /**
     *
     * Navigates to the report view screen
     * @throws IOException if it can't load the report view screen
     */
    @FXML
    private void reportAction() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("reportView.fxml"));
        Parent root = fxmlLoader.load();


        Stage customerView = new Stage();
        customerView.setTitle("Reports View");
        customerView.setScene(new Scene(root));
        customerView.initModality(Modality.APPLICATION_MODAL);
        customerView.show();

        Stage stage = (Stage) reportsButton.getScene().getWindow();
        stage.close();

    }

    /**
     *
     * Gets the current user from userQuery and welcomes the user
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        User user = userQuery.getCurrentUser();
        welcomeUser.setText("Welcome " + user.getUsername());

    }
}
