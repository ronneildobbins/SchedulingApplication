package controller;

import com.schedulingapp.Main;
import dao.appointmentQuery;
import dao.contactQuery;
import dao.customerQuery;
import dao.userQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.DateTimeStringConverter;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 * Add Appointment Controller that can add appointments
 *
 * @author Ronneil Dobbins
 */
public class addAppointmentController implements Initializable {


    /**
     * the title field
     */
    @FXML
    private TextField titleField;


    /**
     * the description field
     */
    @FXML
    private TextField descField;

    /**
     * the location field
     */
    @FXML
    private TextField locationField;

    /**
     * the type field
     */
    @FXML
    private TextField typeField;

    /**
     * the contact combo box
     */
    @FXML
    private ComboBox contactComboBox;


    /**
     * the user combo box
     */
    @FXML
    private ComboBox userComboBox;

    /**
     * the customer combo box
     */
    @FXML
    private ComboBox customerComboBox;

    /**
     * the date picker start date
     */
    @FXML
    private DatePicker startDate;

    /**
     * the date picker end date
     */
    @FXML
    private DatePicker endDate;

    /**
     * the start time field
     */
    @FXML
    private TextField startTime;

    /**
     * the end time field
     */
    @FXML
    private TextField endTime;

    /**
     * the cancel button
     */
    @FXML
    private Button cancelButton;

    /**
     *  the save button
     */
    @FXML
    private Button saveButton;

    /**
     * After selecting an Start Date, it enables the Start Time text Field
     */
    @FXML
    private void selectedStartDate() {
        startTime.setDisable(false);
    }

    /**
     * After selecting an End Date, it enables the End Time text Field
     */
    @FXML
    private void selectedEndDate() {
        endTime.setDisable(false);
    }

    /**
     * When pressed the cancel add appointment action takes you back to the appointment view menu
     * @throws IOException if it can't load the appointment view screen
     */
    @FXML
    private void cancelAddAppt() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("appointmentView.fxml"));
        Parent root = fxmlLoader.load();


        Stage customerView = new Stage();
        customerView.setTitle("Appointment View");
        customerView.setScene(new Scene(root));
        customerView.initModality(Modality.APPLICATION_MODAL);
        customerView.show();

        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     *
     * Attempts to add appointment. Checks to make sure the required information is formatted and valid. Then forward
     * that information to the appointmentQuery which updates the appointment on the database. After completing it returns
     * the user to the appointment view screen. All checks have error messages and if an Exception were to occur it has a
     * custom error message
     *
     */
    @FXML
    private void addAppointment() {

        try {
            String title = titleField.getText();
            String desc = descField.getText();
            String location = locationField.getText();
            String type = typeField.getText();
            if (title.isEmpty() || desc.isEmpty() || location.isEmpty() || type.isEmpty()) {
                displayAlert(1);

            } else {

                String contact = (String) contactComboBox.getValue();
                String user = (String) userComboBox.getValue();
                String customer = (String) customerComboBox.getValue();
                if (contact.isEmpty() || user.isEmpty() || customer.isEmpty()) {
                    displayAlert(1);
                } else {

                    if (startDate.getValue() == null || startTime.getText().isEmpty() ||
                            endDate.getValue() == null || endTime.getText().isEmpty()) {
                        displayAlert(1);
                    }
                    else {

                        boolean validStartTime = isValidTime(startTime);
                        boolean validEndTime = isValidTime(endTime);

                        if (validEndTime && validStartTime) {

                            LocalTime start_time = LocalTime.parse(startTime.getText());
                            LocalTime end_time = LocalTime.parse(endTime.getText());


                            LocalDate start_date = startDate.getValue();
                            LocalDateTime startDateTime = LocalDateTime.of(start_date, start_time);

                            LocalDate end_date = endDate.getValue();
                            LocalDateTime endDateTime = LocalDateTime.of(end_date, end_time);

                            boolean validStart = isBusinessHours(startDateTime);
                            boolean validEnd = isBusinessHours(endDateTime);

                            if (validStart && validEnd) {

                                if(startDateTime.isBefore(endDateTime)) {
                                    int customerId = customerQuery.getCustomerID(customer);

                                    boolean isOverlap = appointmentQuery.customerApptOverlap(customerId, startDateTime, endDateTime);

                                    if (isOverlap) {
                                        displayAlert(4);
                                    } else {

                                        int userId = userQuery.getUserId(user);
                                        int contactId = contactQuery.getContactId(contact);

                                        //ADD Appointment
                                        boolean success = appointmentQuery.addAppointment(title, desc, location, type, userId,
                                                contactId, customerId, startDateTime, endDateTime);

                                        if (success) {
                                            Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION);
                                            alertSuccess.setTitle("Success");
                                            alertSuccess.setHeaderText("Create Appointment Success");
                                            alertSuccess.setContentText("The attempt to create appointment has succeed. Please press the OK button to be redirected to appoitment view");
                                            Optional<ButtonType> result = alertSuccess.showAndWait();
                                            if (result.get() == ButtonType.OK) {

                                                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("appointmentView.fxml"));
                                                Parent root = fxmlLoader.load();


                                                Stage customerView = new Stage();
                                                customerView.setTitle("Appointment View");
                                                customerView.setScene(new Scene(root));
                                                customerView.initModality(Modality.APPLICATION_MODAL);
                                                customerView.show();

                                                Stage stage = (Stage) saveButton.getScene().getWindow();
                                                stage.close();

                                            }
                                        } else {
                                            displayAlert(5);
                                        }

                                    }
                                }else{
                                    displayAlert(6);
                                }

                            } else {
                                displayAlert(3);
                            }
                        }else{
                            displayAlert(2);
                        }
                    }
                }
            }
        } catch (Exception e) {
        displayAlert(5);
        }
    }

    /**
     * Checks to ensure the time inputted is in business hours. The business hours are 08:00 AM to 10:00 PM EST and are
     * converted to local date time
     *
     * @param DateTime the desired datetime which to check if it is in business hours
     * @return true if the DateTime is within business hours and false if it falls outside of business hours.
     */
    private boolean isBusinessHours(LocalDateTime DateTime) {

        int inputHour = DateTime.getHour();
        int inputMinute = DateTime.getMinute();

        LocalTime inputTime = LocalTime.of(inputHour,inputMinute);

        LocalDate date = LocalDate.of(DateTime.getYear(), DateTime.getMonth(), DateTime.getDayOfMonth());

        ZoneId zoneId = ZoneId.of("America/New_York");
        LocalTime openHoursEST = LocalTime.of(8,00);
        LocalTime closeHoursEST = LocalTime.of(22,00);
        LocalDateTime openDTEst = LocalDateTime.of(date, openHoursEST);
        LocalDateTime  closeDTEST = LocalDateTime.of(date, closeHoursEST);
        ZonedDateTime zoned_ODT_EST = openDTEst.atZone(zoneId);
        ZonedDateTime zoned_CDT_EST = closeDTEST.atZone(zoneId);
        ZonedDateTime localOpenHours = zoned_ODT_EST.withZoneSameInstant(ZoneId.systemDefault());
        ZonedDateTime localClosedHours = zoned_CDT_EST.withZoneSameInstant(ZoneId.systemDefault());

        LocalTime openTime = LocalTime.of(localOpenHours.getHour(), localOpenHours.getMinute());

        LocalTime closeTime = LocalTime.of(localClosedHours.getHour(), localClosedHours.getMinute());

        if(inputTime.isAfter(openTime) && inputTime.isBefore(closeTime))
        {
            return true;
        }
        else{
            return false;
        }


    }

    /**
     * Checks to ensure the text field for time is formatted correctly and can be used as a time
     * @param Time The user inputted text field text of time
     * @return true if the text field input can be used as a valid time and false if it is not formatted correctly and can not be used.
     */
    private boolean isValidTime(TextField Time) {
        try{
           LocalTime time = LocalTime.parse(Time.getText());
           return true;

       }catch (Exception e){
           return false;
       }

    }

    /**
     * Display error messages based on alert types Contains Lambda expression to
     * switch alert types
     * @param alertType alertType is used to determine which error to display
     */
    private void displayAlert(int alertType){

        Alert alertError = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1 -> {
                alertError.setTitle("Error");
                alertError.setHeaderText("Blank or Empty Fields");
                alertError.setContentText("Please fill in all fields to create appointment");
                alertError.showAndWait();
            }
            case 2 -> {
                alertError.setTitle("Error");
                alertError.setHeaderText("Start or End Time Not Valid");
                alertError.setContentText("The inputted start or end time are not valid. Please ensure that time is written" +
                        " (HH:mm) Ex: 22:00 ");
                alertError.showAndWait();
            }
            case 3 -> {
                alertError.setTitle("Error");
                alertError.setHeaderText("Outside of Business Hours");
                alertError.setContentText("Please ensure that start and end time are within 08:00 AM and 10:00 PM EST");
                alertError.showAndWait();
            }
            case 4 -> {
                alertError.setTitle("Error");
                alertError.setHeaderText("Customer Appointment Conflict");
                alertError.setContentText("Appointment overlap. Please ensure customer has no overlapping appointments");
                alertError.showAndWait();
            }
            case 5 -> {
                alertError.setTitle("Error");
                alertError.setHeaderText("Appointment Creation Failed");
                alertError.setContentText("Creation of Appointment failed. Please Try Again");
                alertError.showAndWait();
            }
            case 6 -> {
                alertError.setTitle("Error");
                alertError.setHeaderText("End Time before Start Time");
                alertError.setContentText("Please ensure end date & time are after start date and time");
                alertError.showAndWait();
            }
        }

    }

    /**
     * The addAppointmentController initialization populates the contact, user,  and customer combo box with items from each database.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        contactComboBox.setItems(contactQuery.getAllContacts());

        userComboBox.setItems(userQuery.getAllUsers());

        customerComboBox.setItems(customerQuery.getCustomersNames());




    }
}
