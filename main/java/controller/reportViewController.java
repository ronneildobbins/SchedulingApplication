package controller;

import com.schedulingapp.Main;
import dao.appointmentQuery;
import dao.contactQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

/**
 * Report view controller that controls the report view. There are three reports tabs that the user can choose.
 *
 * @author ronneildobbins
 */
public class reportViewController implements Initializable {

    /**
     * back button takes to main menu
     */
    @FXML
    private Button backButton;

    /**
     * Report 3 Column User ID
     */
    @FXML
    private TableColumn todayUser;

    /**
     * Report 3 Column Customer ID
     */
    @FXML
    private TableColumn todayCustomer;

    /**
     * Report 3 Column End Date & Time
     */
    @FXML
    private TableColumn todayEnd;

    /**
     * Report 3 Column Start Date & Time
     */
    @FXML
    private TableColumn todayStart;

    /**
     * Report 3 Column Type
     */
    @FXML
    private TableColumn todayType;

    /**
     * Report 3 Column Contact
     */
    @FXML
    private TableColumn todayContact;

    /**
     * Report 3 Column Location
     */
    @FXML
    private TableColumn todayLocation;

    /**
     * Report 3 Column Description
     */
    @FXML
    private TableColumn todayDesc;

    /**
     * Report 3 Column Title
     */
    @FXML
    private TableColumn todayTitle;

    /**
     * Report 3 Column Appointment ID
     */
    @FXML
    private TableColumn todayId;

    /**
     * Report 3 Table
     */
    @FXML
    private TableView todayTable;


    /**
     * the contact table customer id column
     */
    @FXML
    private TableColumn contactCustomer;

    /**
     * the contact table End Date & Time table column
     */
    @FXML
    private TableColumn contactEnd;

    /**
     * the contact table Start Date & Time table column
     */
    @FXML
    private TableColumn contactStart;

    /**
     * the contact table type table column
     */
    @FXML
    private TableColumn contactType;

    /**
     * the contact table description table column
     */
    @FXML
    private TableColumn contactDesc;

    /**
     * the contact table title table column
     */
    @FXML
    private TableColumn contactTitle;

    /**
     * the contact table contact id table column
     */
    @FXML
    private TableColumn contactId;

    /**
     * the contact table
     */
    @FXML
    private TableView customerTable;


    /**
     * the combo box that holds the list of all contacts.
     */
    @FXML
    private ComboBox contactCombo;


    /**
     * the combo box that holds the number of each month (1-12)
     */
    @FXML
    private ComboBox monthComboBox;

    /**
     * The number of appointments that match appointment type and/or appointment month
     */
    @FXML
    private Label numberFound;

    /**
     * the type input field
     */
    @FXML
    private TextField typeField;


    /**
     * List of the number of months (1-12)
     */
    private static ObservableList<Integer> months =  FXCollections.observableArrayList();

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
     * One of the three reports. It allows user to search the total number of customer appointments by type or month.
     * (Part of the requirements in 3f)
     */
    @FXML
    private void searchTypeMonth(){

        try {

            if (monthComboBox.getValue() == null && typeField.getText().equals(null)){
                displayAlert(2);
            }
            else {

                String Type;
                int month;

                if(monthComboBox.getValue() == (null)){
                    month = 0;
                }
                else {
                month = (int) monthComboBox.getValue();
                }

                if (typeField.getText().equals(null)) {
                    Type = "";
                } else {
                    Type = typeField.getText();
                }

                    int found = appointmentQuery.getAppointByMonthType(month, Type);

                    numberFound.setText(String.valueOf(found));

                }



        }catch (Exception e){
            displayAlert(1);
        }

    }

    /**
     *One of the three reports. By selecting a contact from the combo box,
     * the searchContact allows the user to see the schedule of appointments for each contact.
     * (Part of the requirements in 3f)
     */
    @FXML
    private void searchContact(){

        if (contactCombo.getValue() == null){
            displayAlert(3);
        }
        else{
            try {
                int id  = contactQuery.getContactId((String) contactCombo.getValue());
                ObservableList<Appointment> Schedule = appointmentQuery.getAppByContact(id);

                contactId.setCellValueFactory(new PropertyValueFactory<>("apptId"));
                contactTitle.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
                contactDesc.setCellValueFactory(new PropertyValueFactory<>("apptDescription"));
                contactType.setCellValueFactory(new PropertyValueFactory<>("apptType"));
                contactStart.setCellValueFactory(new PropertyValueFactory<>("formatStart"));
                contactEnd.setCellValueFactory(new PropertyValueFactory<>("formatEnd"));
                contactCustomer.setCellValueFactory(new PropertyValueFactory<>("customerID"));
                customerTable.setItems(Schedule);


            }catch (Exception e){
                displayAlert(4);
            }
        }
    }

    /**
     *One of the three reports. It allows the user to see a list of all appointments that are 1 day(24 Hours) away.
     * I choose this as my report choice as I thought the user would find actually use in being able to see all upcoming appointments
     * 1 day out.
     * (Part of the requirements in 3f)
     */
    @FXML
    private void reportThreeAction(){

        todayId.setCellValueFactory(new PropertyValueFactory<>("apptId"));
        todayTitle.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        todayDesc.setCellValueFactory(new PropertyValueFactory<>("apptDescription"));
        todayLocation.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
        todayContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        todayType.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        todayStart.setCellValueFactory(new PropertyValueFactory<>("formatStart"));
        todayEnd.setCellValueFactory(new PropertyValueFactory<>("formatEnd"));
        todayCustomer.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        todayUser.setCellValueFactory(new PropertyValueFactory<>("userID"));
        todayTable.setItems(appointmentQuery.getAllAppointDays());
    }

    /**
     * Used to display various error message on the report view screen
     * @param alertType alertType is used to determine which error to display
     */
    private void displayAlert(int alertType){

        Alert alertError = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1 -> {
                alertError.setTitle("Error");
                alertError.setHeaderText("Search Unsuccessful");
                alertError.setContentText("Search unsuccessful. Please ensure you have chosen at least a month. Then Try Again!");
                alertError.showAndWait();
            }
            case 2 -> {
                alertError.setTitle("Error");
                alertError.setHeaderText("One Value Must be Selected");
                alertError.setContentText("Please select a Month or a Type");
                alertError.showAndWait();
            }
            case 3 -> {
                alertError.setTitle("Error");
                alertError.setHeaderText("Contact must be selected");
                alertError.setContentText("Please select a contact from the combo box");
                alertError.showAndWait();
            }
            case 4 -> {
                alertError.setTitle("Error");
                alertError.setHeaderText("Search Unsuccessful");
                alertError.setContentText("Please try search again");
                alertError.showAndWait();
            }
        }

    }

    /**
     * The initialization in report view controller is used to populate the month combo box on report 1. It is also used to populate the
     * contacts list on report 2. Contains lambda expression to populate the month combo box with the number of months (1-12).
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        IntStream.range(1,13).forEach(value -> months.add(value));
        monthComboBox.setItems(months);

        contactCombo.setItems(contactQuery.getAllContacts());


    }
}
