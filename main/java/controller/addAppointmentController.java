package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class addAppointmentController implements Initializable {


    @FXML
    private TextField titleField;

    @FXML
    private TextField descField;

    @FXML
    private TextField locationField;

    @FXML
    private TextField typeField;

    @FXML
    private ComboBox contactComboBox;

    @FXML
    private ComboBox userComboBox;

    @FXML
    private ComboBox customerComboBox;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private TextField startTime;

    @FXML
    private TextField endTime;

    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;


    @FXML
    private void selectedStartDate(ActionEvent actionEvent) {
    }

    @FXML
    private void selectedEndDate(ActionEvent actionEvent) {
    }

    @FXML
    private void cancelAddAppt(ActionEvent actionEvent) {
    }

    @FXML
    private void addAppointment(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
