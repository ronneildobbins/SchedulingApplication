package controller;

import com.schedulingapp.Main;
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

public class customerViewController implements Initializable {

    @FXML
    private Button backButton;
    @FXML
    private Button addButton;
    @FXML
    private Button modifyButton;
    @FXML
    private Button deleteButton;

    @FXML
    private TableView customerTable;
    @FXML
    private TableColumn customer_id;
    @FXML
    private TableColumn customerName;
    @FXML
    private TableColumn customerAddress;
    @FXML
    private TableColumn customerPostalCode;
    @FXML
    private TableColumn customerPhone;
    @FXML
    private TableColumn customerDivision;
    @FXML
    private TableColumn customerCountry;





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

    @FXML
    private void addAction() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("addCustomer.fxml"));
        Parent root = fxmlLoader.load();


        Stage mainMenu = new Stage();
        mainMenu.setTitle("Add Customer Window");
        mainMenu.setScene(new Scene(root));
        mainMenu.initModality(Modality.APPLICATION_MODAL);
        mainMenu.show();

        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void deleteAction(){

       Customer selectedItem = (Customer) customerTable.getSelectionModel().getSelectedItem();
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirm.setTitle("Customer Removal Check");
        alertConfirm.setHeaderText("Are you sure ?");
        alertConfirm.setContentText("All associated appointments will also be delete. Please review delete request as this cant be undone.\n\n" +
                "Customer Id: " + selectedItem.getCustomer_id() + "\n Customer Name: " + selectedItem.getCustomerName());
        Optional<ButtonType> result = alertConfirm.showAndWait();
        if(result.get() == ButtonType.OK){
            //Appoint delete

            boolean successCustomer = customerQuery.deleteCustomer(selectedItem.getCustomer_id());
            if (successCustomer) {
                Alert removalSuccess = new Alert(Alert.AlertType.INFORMATION);
                removalSuccess.setTitle("Customer Removed");
                removalSuccess.setHeaderText("Customer has been removed");
                removalSuccess.setContentText("The customer " + selectedItem.getCustomerName() + " along with their associated appointments have been removed.");
                Optional<ButtonType> finalResult = removalSuccess.showAndWait();
                if (finalResult.get() == ButtonType.OK) {
                    customerTable.setItems(customerQuery.getAllCustomer());
                } else {

                }
            }
        }
    }

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
