package controller;

import com.schedulingapp.Main;
import dao.appointmentQuery;
import dao.customerQuery;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Appointment view controller that controls the appointment view. The appointment view shows a table and allows user to modify,
 * add, delete appointment from the database.
 *
 * @author ronneildobbins
 */
public class appointmentViewController implements Initializable {

    /**
     * The appointment the user wishes to modify
     */
    private static Appointment modAppoint;


    /**
     * The weekly appointment Table
     */
    @FXML
    private TableView appWeekTable;

    /**
     * the weekly appointment table appointment id table column
     */
    @FXML
    private TableColumn appWeekID;

    /**
     * the weekly appointment table appointment title table column
     */
    @FXML
    private TableColumn appWeekTitle;

    /**
     * the weekly appointment table appointment description table column
     */
    @FXML
    private TableColumn appWeekDesc;

    /**
     * the weekly appointment table appointment location table column
     */
    @FXML
    private TableColumn appWeekLocation;

    /**
     * the weekly appointment table appointment contact table column
     */
    @FXML
    private TableColumn appWeekContact;

    /**
     * the weekly appointment table appointment type table column
     */
    @FXML
    private TableColumn appWeekType;

    /**
     * the weekly appointment table appointment start date & time table column
     */
    @FXML
    private TableColumn appWeekStart;

    /**
     * the weekly appointment table appointment end date & time table column
     */
    @FXML
    private TableColumn appWeekEnd;

    /**
     * the weekly appointment table appointment customer id table column
     */
    @FXML
    private TableColumn appWeekCustomer;

    /**
     * the weekly appointment table appointment user id table column
     */
    @FXML
    private TableColumn appWeekUser;


    /**
     * The monthly appointment Table
     */
    @FXML
    private TableView appMonthTable;

    /**
     * the monthly appointment table appointment id table column
     */
    @FXML
    private TableColumn appMonthID;

    /**
     * the monthly appointment table appointment title table column
     */
    @FXML
    private TableColumn appMonthTitle;

    /**
     * the monthly appointment table appointment description table column
     */
    @FXML
    private TableColumn appMonthDesc;

    /**
     * the monthly appointment table appointment location table column
     */
    @FXML
    private TableColumn appMonthLocation;

    /**
     * the monthly appointment table appointment contact table column
     */
    @FXML
    private TableColumn appMonthContact;

    /**
     * the monthly appointment table appointment type table column
     */
    @FXML
    private TableColumn appMonthType;

    /**
     * the monthly appointment table appointment start date & time table column
     */
    @FXML
    private TableColumn appMonthStart;

    /**
     * the monthly appointment table appointment end date & time table column
     */
    @FXML
    private TableColumn appMonthEnd;


    /**
     * the monthly appointment table appointment customer id table column
     */
    @FXML
    private TableColumn appMonthCustomer;

    /**
     * the monthly appointment table appointment user id table column
     */
    @FXML
    private TableColumn appMonthUser;


    /**
     * The all appointment Table
     */
    @FXML
    private TableView allApptTable;

    /**
     * the all appointment table appointment id table column
     */
    @FXML
    private TableColumn allApptId;

    /**
     * the all appointment table appointment title table column
     */
    @FXML
    private TableColumn allApptTitle;

    /**
     * the all appointment table appointment description table column
     */
    @FXML
    private TableColumn allApptDesc;

    /**
     * the all appointment table appointment location table column
     */
    @FXML
    private TableColumn allApptLocation;

    /**
     * the all appointment table appointment contact table column
     */
    @FXML
    private TableColumn allApptContact;

    /**
     * the all appointment table appointment type table column
     */
    @FXML
    private TableColumn allApptType;

    /**
     * the all appointment table appointment start date & time table column
     */
    @FXML
    private TableColumn allApptStart;

    /**
     * the all appointment table appointment end date & time table column
     */
    @FXML
    private TableColumn allApptEnd;

    /**
     * the all appointment table appointment customer id table column
     */
    @FXML
    private TableColumn allApptCID;

    /**
     * the all appointment table appointment user id table column
     */
    @FXML
    private TableColumn allApptUId;

    /**
     * The back button
     */
    @FXML
    private Button backButton;

    /**
     * The add button
     */
    @FXML
    private Button addButton;

    /**
     * The modify button
     */
    @FXML
    private Button modifyButton;

    /**
     * The delete button
     */
    @FXML
    private Button deleteButton;

    /**
     * the current tab label (On startup displays All Tab pane)
     */
    @FXML
    private Label currentTabLabel;

    /**
     * Get the desired appointment to modify
     * @return the appointment the user wishes to modify
     */
    public static Appointment getModAppoint() {
        return modAppoint;
    }

    /**
     * Sets the current Tab label to all
     */
    @FXML
    private void selectAll() {
        if(this.currentTabLabel == null){

        }
        else {
            currentTabLabel.setText("Current Tab: All");
            modifyButton.setDisable(false);
            deleteButton.setDisable(false);
        }
    }

    /**
     * Sets the current Tab label to monthly and populates the monthly table. Also disables the modify and delete button
     */
    @FXML
    private void selectMonthly() {
        currentTabLabel.setText("Current Tab: Monthly");
        modifyButton.setDisable(true);
        deleteButton.setDisable(true);

        appMonthID.setCellValueFactory(new PropertyValueFactory<>("apptId"));
        appMonthTitle.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        appMonthDesc.setCellValueFactory(new PropertyValueFactory<>("apptDescription"));
        appMonthLocation.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
        appMonthContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        appMonthType.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        appMonthStart.setCellValueFactory(new PropertyValueFactory<>("formatStart"));
        appMonthEnd.setCellValueFactory(new PropertyValueFactory<>("formatEnd"));
        appMonthCustomer.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        appMonthUser.setCellValueFactory(new PropertyValueFactory<>("userID"));
        appMonthTable.setItems(appointmentQuery.getAllAppMonth());


    }

    /**
     * Sets the current Tab label to weekly and populates the weekly table. Also disables the modify and delete button
     */
    @FXML
    private void selectWeekly() {
        currentTabLabel.setText("Current Tab: Weekly");
        modifyButton.setDisable(true);
        deleteButton.setDisable(true);

        appWeekID.setCellValueFactory(new PropertyValueFactory<>("apptId"));
        appWeekTitle.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        appWeekDesc.setCellValueFactory(new PropertyValueFactory<>("apptDescription"));
        appWeekLocation.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
        appWeekContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        appWeekType.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        appWeekStart.setCellValueFactory(new PropertyValueFactory<>("formatStart"));
        appWeekEnd.setCellValueFactory(new PropertyValueFactory<>("formatEnd"));
        appWeekCustomer.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        appWeekUser.setCellValueFactory(new PropertyValueFactory<>("userID"));
        appWeekTable.setItems(appointmentQuery.getAllAppWeek());
    }

    /**
     * When pressed the mod action get the selected appointment from the table and takes you to the modify appointment screen
     * @throws IOException if it fails to load the modify appointment screen
     */
    @FXML
    private void modAction(){

        try {
            if(allApptTable.getSelectionModel().getSelectedItem() == null){
               displayAlert(3);
            }else {
                modAppoint = (Appointment) allApptTable.getSelectionModel().getSelectedItem();

                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("modAppointment.fxml"));
                Parent root = fxmlLoader.load();


                Stage mainMenu = new Stage();
                mainMenu.setTitle("Modify Appointment");
                mainMenu.setScene(new Scene(root));
                mainMenu.initModality(Modality.APPLICATION_MODAL);
                mainMenu.show();

                Stage stage = (Stage) modifyButton.getScene().getWindow();
                stage.close();
            }
        }catch (NullPointerException e){
            //display alert 3
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * When pressed the add action button takes you to the add appointment screen
     * @throws IOException if it fails to load add appointment screen
     */
    @FXML
    private void addAction() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("addAppointment.fxml"));
        Parent root = fxmlLoader.load();


        Stage mainMenu = new Stage();
        mainMenu.setTitle("Add Appointment");
        mainMenu.setScene(new Scene(root));
        mainMenu.initModality(Modality.APPLICATION_MODAL);
        mainMenu.show();

        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Get the selected appointment to delete, confirms correct selected appointment, then delete all the appointment
     * and informs user of it's success
     */
    @FXML
    private void deleteAction(){

        try {
            Appointment selectedItem = (Appointment) allApptTable.getSelectionModel().getSelectedItem();
            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
            alertConfirm.setTitle("Appointment Removal Check");
            alertConfirm.setHeaderText("Are you sure ?");
            alertConfirm.setContentText("All associated appointments will also be delete. Please review delete request as this cant be undone.\n\n" +
                    "Appointment Id: " + selectedItem.getApptId() + "\n Appointment Title: " + selectedItem.getApptTitle());
            Optional<ButtonType> result = alertConfirm.showAndWait();
            if (result.get() == ButtonType.OK) {
                //Appoint delete
                boolean successAppointment = appointmentQuery.deleteAppointment(selectedItem.getApptId());
                if (successAppointment) {
                    Alert removalSuccess = new Alert(Alert.AlertType.INFORMATION);
                    removalSuccess.setTitle("Appointment Removed");
                    removalSuccess.setHeaderText("Appointment has been removed");
                    removalSuccess.setContentText("The appointment " + selectedItem.getApptTitle() + " has been removed.");
                    Optional<ButtonType> finalResult = removalSuccess.showAndWait();
                    if (finalResult.get() == ButtonType.OK) {
                        allApptTable.setItems(appointmentQuery.getAllAppointments());
                    }
                } else {
                    displayAlert(1);
                }
            }
        }catch (NullPointerException e){
            displayAlert(2);
        }
    }

    /**
     *
     * When pressed the back action takes you back to the main menu
     * @throws IOException if it fails to load main menu
     */
    @FXML
    private void backAction() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainMenu.fxml"));
        Parent root = fxmlLoader.load();


        Stage mainMenu = new Stage();
        mainMenu.setTitle("Main Menu");
        mainMenu.setScene(new Scene(root));
        mainMenu.initModality(Modality.APPLICATION_MODAL);
        mainMenu.show();

        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Used to display various error message on the appointment view screen
     * @param alertType alertType is used to determine which error to display
     */
    private void displayAlert(int alertType){

        Alert alertError = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1 -> {
                alertError.setTitle("Error");
                alertError.setHeaderText("Delete Appointment Failed");
                alertError.setContentText("Failed to delete appointment. Please try Again");
                alertError.showAndWait();
            }
            case 2 -> {
                alertError.setTitle("Error");
                alertError.setHeaderText("No appointment selected to delete");
                alertError.setContentText("Please select an appointment to delete");
                alertError.showAndWait();
            }
            case 3 -> {
                alertError.setTitle("Error");
                alertError.setHeaderText("No appointment selected to modify");
                alertError.setContentText("Please select an appointment to modify");
                alertError.showAndWait();
            }
        }

    }

    /**
     * The initialization in appointment view controller is used to populate the appointment table.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

          allApptId.setCellValueFactory(new PropertyValueFactory<>("apptId"));
          allApptTitle.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
          allApptDesc.setCellValueFactory(new PropertyValueFactory<>("apptDescription"));
          allApptLocation.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
          allApptContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
          allApptType.setCellValueFactory(new PropertyValueFactory<>("apptType"));
          allApptStart.setCellValueFactory(new PropertyValueFactory<>("formatStart"));
          allApptEnd.setCellValueFactory(new PropertyValueFactory<>("formatEnd"));
          allApptCID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
          allApptUId.setCellValueFactory(new PropertyValueFactory<>("userID"));
          allApptTable.setItems(appointmentQuery.getAllAppointments());

    }
}
