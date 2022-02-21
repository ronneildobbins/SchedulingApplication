package controller;

import com.schedulingapp.Main;
import dao.appointmentQuery;
import dao.customerQuery;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Customer view controller that controls the customer view. The customer view shows a table and allows user to modify,
 * add, delete customer from the database.
 *
 * @author ronneildobbins
 */
public class customerViewController implements Initializable {

    /**
     * The customer the user wishes to modify
     */
    private static Customer modCustomer;

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
     * The customer Table
     */
    @FXML
    private TableView customerTable;

    /**
     * the customer table customer id table column
     */
    @FXML
    private TableColumn customer_id;

    /**
     * the customer table customer name table column
     */
    @FXML
    private TableColumn customerName;

    /**
     * the customer table customer address table column
     */
    @FXML
    private TableColumn customerAddress;

    /**
     * the customer table customer postal code table column
     */
    @FXML
    private TableColumn customerPostalCode;

    /**
     * the customer table customer phone table column
     */
    @FXML
    private TableColumn customerPhone;

    /**
     * the customer table customer division table column
     */
    @FXML
    private TableColumn customerDivision;

    /**
     * the customer table customer country table column
     */
    @FXML
    private TableColumn customerCountry;

    /**
     * get the Customer the user wishes to modify.
     * @return the Customer which the user wishes to modify
     */
    public static Customer getModCustomer() {
        return modCustomer;
    }

    /**
     *
     * When pressed the mod action get the selected customer from the table and takes you to the modify customer screen
     * @throws IOException if it fails to load the modify customer screen
     */
    @FXML
    private void modAction(){

        try {
            if(customerTable.getSelectionModel().getSelectedItem() == null){
                displayAlert(3);
            }else {
                Customer selectedItem = (Customer) customerTable.getSelectionModel().getSelectedItem();
                modCustomer = selectedItem;

                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("modCustomer.fxml"));
                Parent root = fxmlLoader.load();


                Stage mainMenu = new Stage();
                mainMenu.setTitle("Modify Customer");
                mainMenu.setScene(new Scene(root));
                mainMenu.initModality(Modality.APPLICATION_MODAL);
                mainMenu.show();

                Stage stage = (Stage) modifyButton.getScene().getWindow();
                stage.close();
            }
        }catch (NullPointerException e){
            displayAlert(3);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    /**
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
     * When pressed the add action button takes you to the add customer screen
     * @throws IOException if it fails to load add customer screen
     */
    @FXML
    private void addAction() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("addCustomer.fxml"));
        Parent root = fxmlLoader.load();


        Stage mainMenu = new Stage();
        mainMenu.setTitle("Add Customer");
        mainMenu.setScene(new Scene(root));
        mainMenu.initModality(Modality.APPLICATION_MODAL);
        mainMenu.show();

        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Get the selected customer to delete, confirms correct selected customer, then delete all the associated appointments
     * and the customers and informs user of it's success
     */
    @FXML
    private void deleteAction(){

        try {
            Customer selectedItem = (Customer) customerTable.getSelectionModel().getSelectedItem();
            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
            alertConfirm.setTitle("Customer Removal Check");
            alertConfirm.setHeaderText("Are you sure ?");
            alertConfirm.setContentText("All associated appointments will also be delete. Please review delete request as this cant be undone.\n\n" +
                    "Customer Id: " + selectedItem.getCustomer_id() + "\n Customer Name: " + selectedItem.getCustomerName());
            Optional<ButtonType> result = alertConfirm.showAndWait();
            if (result.get() == ButtonType.OK) {

                boolean successAppointment = appointmentQuery.deleteApptById(selectedItem.getCustomer_id());

                boolean successCustomer = customerQuery.deleteCustomer(selectedItem.getCustomer_id());
                if (successCustomer && successAppointment) {
                    Alert removalSuccess = new Alert(Alert.AlertType.INFORMATION);
                    removalSuccess.setTitle("Customer Removed");
                    removalSuccess.setHeaderText("Customer has been removed");
                    removalSuccess.setContentText("The customer " + selectedItem.getCustomerName() + " along with their associated appointments have been removed.");
                    Optional<ButtonType> finalResult = removalSuccess.showAndWait();
                    if (finalResult.get() == ButtonType.OK) {
                        customerTable.setItems(customerQuery.getAllCustomer());
                    } else {
                        displayAlert(2);
                    }
                }
            }
        }catch (NullPointerException e){
            //display alert e
            displayAlert(1);

        }
    }

    /**
     * Used to display various error message on the customer view screen
     * @param alertType alertType is used to determine which error to display
     */
    private void displayAlert(int alertType){

        Alert alertError = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1 -> {
                alertError.setTitle("Error");
                alertError.setHeaderText("No customer selected to delete");
                alertError.setContentText("Please select a customer to delete");
                alertError.showAndWait();
            }
            case 2 -> {
                alertError.setTitle("Error");
                alertError.setHeaderText("Delete Customer Failed");
                alertError.setContentText("Failed to delete customer. Please try Again");
                alertError.showAndWait();
            }
            case 3 -> {
                alertError.setTitle("Error");
                alertError.setHeaderText("No customer selected to modify");
                alertError.setContentText("Please select a customer to modify");
                alertError.showAndWait();
            }
        }

    }

    /**
     * The initialization in customer view controller is used to populate the customer table.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        customer_id.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        customerPostalCode.setCellValueFactory(new PropertyValueFactory<>("customerPostal"));
        customerPhone.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        customerDivision.setCellValueFactory(new PropertyValueFactory<>("Division"));
        customerCountry.setCellValueFactory(new PropertyValueFactory<>("Country"));
        customerTable.setItems(customerQuery.getAllCustomer());



    }
}
