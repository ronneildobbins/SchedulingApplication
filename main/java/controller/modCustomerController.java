package controller;

import com.schedulingapp.Main;
import dao.countryQuery;
import dao.customerQuery;
import dao.divisionQuery;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Modify Customer Controller that can modify a customer
 *
 * @author Ronneil Dobbins
 */
public class modCustomerController implements Initializable {

    /**
     * the id text field
     */
    @FXML
    private TextField idField;

    /**
     * the name text field
     */
    @FXML
    private TextField nameField;

    /**
     * the address text field
     */
    @FXML
    private TextField addressField;

    /**
     * the postal text field
     */
    @FXML
    private TextField postalField;

    /**
     * the phone text field
     */
    @FXML
    private TextField phoneField;

    /**
     * the country combo box
     */
    @FXML
    private ComboBox<String> countryCombo;

    /**
     * the division combo box
     */
    @FXML
    private ComboBox divisionCombo;

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
     *
     * Attempts to modify customer. Checks to make sure the required information is formatted and valid. Then forward
     * that information to the customerQuery which updates the appointment on the database. After completing it returns
     * the user to the customer view screen. All checks have error messages and if an Exception were to occur it has a
     * custom error message. (Error message and information alerts are in here)
     *
     * @throws IOException if it fails to load the customer view screen
     */
    @FXML
    private void modifyCustomer() throws IOException {


        String name = nameField.getText();
        String address = addressField.getText();
        String postal = postalField.getText();
        String phone = phoneField.getText();

        if(divisionCombo.getValue() == null){
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("Error");
            alertError.setHeaderText("Division Not Selected");
            alertError.setContentText("Please select a Division");
            alertError.showAndWait();

        }
        else {
            if (name.isEmpty() || address.isEmpty() || postal.isEmpty() || phone.isEmpty()) {
                Alert alertError = new Alert(Alert.AlertType.ERROR);
                alertError.setTitle("Error");
                alertError.setHeaderText("Empty or Blank Fields");
                alertError.setContentText("Please fill out all fields to create customer");
                alertError.showAndWait();

            } else {

                int divID = divisionQuery.getDivID((String) divisionCombo.getValue());

                int id = Integer.parseInt(idField.getText());

                boolean success = customerQuery.modifyCustomer(id, name, address, postal, phone, divID);

                if (success) {
                    Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION);
                    alertSuccess.setTitle("Success");
                    alertSuccess.setHeaderText("Modify Customer Success");
                    alertSuccess.setContentText("The attempt to modify a new customer has succeed. Please press the OK button to be redirected to customer view");
                    Optional<ButtonType> result = alertSuccess.showAndWait();
                    if(result.get() == ButtonType.OK){

                        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("customerView.fxml"));
                        Parent root = fxmlLoader.load();


                        Stage customerView = new Stage();
                        customerView.setTitle("Customer View");
                        customerView.setScene(new Scene(root));
                        customerView.initModality(Modality.APPLICATION_MODAL);
                        customerView.show();

                        Stage stage = (Stage) saveButton.getScene().getWindow();
                        stage.close();
                    }

                } else {
                    Alert alertError = new Alert(Alert.AlertType.ERROR);
                    alertError.setTitle("Error");
                    alertError.setHeaderText("Modify Customer Failed");
                    alertError.setContentText("The attempt to modify a new customer has failed. Please Try Again !");
                    alertError.showAndWait();
                }
            }
        }
    }

    /**
     * When pressed the cancel mod customer action takes you back to the customer view menu
     * @throws IOException if it can't load the customer view screen
     */
    @FXML
    private void cancelModCustomer() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("customerView.fxml"));
        Parent root = fxmlLoader.load();


        Stage customerView = new Stage();
        customerView.setTitle("Customer View");
        customerView.setScene(new Scene(root));
        customerView.initModality(Modality.APPLICATION_MODAL);
        customerView.show();

        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

    }

    /**
     * When a country is selected it enables the division combo box and
     * populates it with the division based on country id
     *
     */
    @FXML
    private void countrySelected(){

        try {
            String country = countryCombo.getValue();

            int countryid = switch (country) {
                case "U.S" -> 1;
                case "UK" -> 2;
                case "Canada" -> 3;
                default -> 0;
            };

            divisionCombo.setItems(divisionQuery.getAllDivision(countryid));
            divisionCombo.setDisable(false);

            divisionCombo.setValue(null);

        }catch (NullPointerException e){

        }


    }


    /**
     * The modCustomerController initialization set the values based on the selected customer the user wants to modify.
     * It populates the country combo box with items from each database.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Customer selectedCustomer = customerViewController.getModCustomer();

        countryCombo.setItems(countryQuery.getAllCountries());


        idField.setText(String.valueOf(selectedCustomer.getCustomer_id()));
        nameField.setText(selectedCustomer.getCustomerName());
        addressField.setText(selectedCustomer.getCustomerAddress());
        postalField.setText(selectedCustomer.getCustomerPostal());
        phoneField.setText(selectedCustomer.getCustomerPhone());

        countryCombo.setValue(selectedCustomer.getCountry());

        divisionCombo.setValue(selectedCustomer.getDivision());


    }

    }

