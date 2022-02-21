package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;


/**
 * Handles all customer query to database.
 *
 * @author ronneildobbins
 */
public class customerQuery {

    /**
     *  A list of all the customers
     */
    private static ObservableList<Customer> allCustomers =  FXCollections.observableArrayList();

    /**
     * A list of all the customer names
     */
    private static ObservableList<String> customerNames =  FXCollections.observableArrayList();

    /**
     * Gets a oberservable list of all customers from database
     * @return Gets an oberservable list of all customers
     */
    public static ObservableList<Customer> getAllCustomer(){

        allCustomers.clear();

        try {

            String sql = "SELECT * FROM client_schedule.customers";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int customer_id = rs.getInt("Customer_ID");
                String customer_name = rs.getString("Customer_Name");
                String Address = rs.getString("Address");
                String Postal = rs.getString("Postal_Code");
                String Phone = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");
                String Division = (divisionQuery.getDivision(divisionId));
                String Country = (countryQuery.getCountry(divisionId));

                Customer customer = new Customer(customer_id, customer_name, Address, Postal, Phone, divisionId, Division, Country);
                allCustomers.add(customer);

            }
           //Test
            ps.close();
            return allCustomers;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Attempts to create a new customer in the database based on the provided information
     * @param name the inputted name
     * @param address the inputted address
     * @param postal the inputted postal
     * @param phone the inputted phone
     * @param divId the inputted division id
     * @return true if add customer was successful. Otherwise, return false
     */
    public static boolean addCustomer(String name, String address, String postal, String phone, int divId){

        try {
            String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(?,?,?,?,?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1,name);
            ps.setString(2,address);
            ps.setString(3, postal);
            ps.setString(4, phone);
            ps.setInt(5, divId);
            int rowAffected = ps.executeUpdate();
            if(rowAffected == 1){
                return true;
            }
            else{
                return false;
            }

        }catch (Exception e){
            return false;
        }



    }

    /**
     * Attempts to modify an existing customer in the database based on the provided information
     * @param id the selected Customer id to modify
     * @param name the inputted name
     * @param address the inputted address
     * @param postal the inputted postal
     * @param phone the inputted phone
     * @param divId the inputted division id
     * @return true if modify customer was successful. Otherwise, return false
     */
    public static boolean modifyCustomer(int id ,String name, String address, String postal, String phone, int divId){

        try {
            String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1,name);
            ps.setString(2,address);
            ps.setString(3, postal);
            ps.setString(4, phone);
            ps.setInt(5, divId);
            ps.setInt(6, id);
            int rowAffected = ps.executeUpdate();
            if(rowAffected == 1){
                return true;
            }
            else{
                return false;
            }

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }



    }

    /**
     * Delete the customer based on inputted customer id.
     * @param id the customer id
     * @return true if delete customer was successful. Otherwise, return false
     */
    public static  boolean deleteCustomer(int id){
        try {
            String sql = "DELETE FROM customers WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, id);
            int rowAffected = ps.executeUpdate();
            if(rowAffected == 1){
                return true;
            }
            else{
                return false;
            }

        }catch (Exception e){
            return false;
        }
    }

    /**
     * Gets an oberservable list of Customer Names
     * @return an oberservable list of Customer Names
     */
    public static ObservableList<String> getCustomersNames(){

        customerNames.clear();

        try {
            String sql = "SELECT * FROM client_schedule.customers;";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String contact = rs.getString("Customer_Name");
                customerNames.add(contact);

            }

            ps.close();
            return customerNames;

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Gets the customer id based on the customer name
     * @param name the customer name
     * @return the customer id (int)
     */
    public static int getCustomerID(String name){

        try {
            String sql = "SELECT * FROM client_schedule.customers WHERE Customer_Name= ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            int customerID = 0;
            if (rs.next()) {
                customerID = rs.getInt("Customer_ID");
                ps.close();
            }

            return customerID;

        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Gets the customer name based on the customer id
     * @param id the customer id
     * @return the customer name
     */
    public static String getCustomerName(int id){

        try {
            String sql = "SELECT * FROM client_schedule.customers WHERE Customer_ID= ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            String name = null;
            if (rs.next()) {
                name = rs.getString("Customer_Name");
                ps.close();
            }

            return name;

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

}
