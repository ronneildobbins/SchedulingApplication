package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class customerQuery {

    private static ObservableList<Customer> allCustomers =  FXCollections.observableArrayList();

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

}
