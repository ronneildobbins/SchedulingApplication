package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Handles all contact query to database.
 *
 * @author ronneildobbins
 */
public class contactQuery {

    /**
     * A list of the names of all the contacts
     */
    private static ObservableList<String> allContacts =  FXCollections.observableArrayList();

    /**
     * Gets a contact name string based on contact id
     * @param contactID the contact id
     * @return the String of the contact
     * @throws SQLException If a problem with the database occurs
     */
    public static String getContact(int contactID) {

        try {
            String sql = "SELECT * FROM client_schedule.contacts WHERE Contact_ID= ? ";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, contactID);
            ResultSet rs = ps.executeQuery();
            String Contact = null;
            if (rs.next()) {
                Contact = rs.getString("Contact_Name");
                ps.close();
            }

            return Contact;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets a list of names of all contacts based on the database.
     * @return An observable list of String of names of all contacts in the database.
     */
    public static ObservableList<String> getAllContacts(){
        allContacts.clear();

        try {
            String sql = "SELECT * FROM client_schedule.contacts;";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String contact = rs.getString("Contact_Name");
                allContacts.add(contact);

            }

            ps.close();
            return allContacts;

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Gets a contact id based on contact name string
     * @param name the contact name
     * @return the contact id
     */
    public static int getContactId(String name){
        try{
            String sql = "SELECT * FROM client_schedule.contacts WHERE Contact_Name= ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            int contactId = 0;
            if (rs.next()) {
                contactId = rs.getInt("Contact_ID");
                ps.close();
            }

            return contactId;

        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }

    }
}

