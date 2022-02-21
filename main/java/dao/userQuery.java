package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Handles all user query to database.
 *
 * @author ronneildobbins
 */
public class userQuery {

    /**
     * A list of the names of all users
     */
    private static ObservableList<String> allUsers =  FXCollections.observableArrayList();

    /**
     * the current user logged in
     */
    private static User currentUser;

    /**
     * Gets the current user
     * @return the current user
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Takes the username and password and verify it with the database before logging the user in.
     * Used to verify the login information with the database.
     * @param Username the username of the user attempting to login
     * @param Password the password of the user attempting to login
     * @return true if the information provided is valid and false if there is a error with the information
     */
    public static boolean login(String Username, String Password) {
        try {
            String sql = "SELECT * FROM client_schedule.users Where Password = '" + Username + "' AND User_Name = '" + Password + "'";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int user_id = rs.getInt("User_ID");
                String username = rs.getString("User_Name");
                currentUser = new User(user_id,username);
                ps.close();
                return true;

            }
            else{
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }



    }

    /**
     * Gets a list of names of all users based on the database.
     * @return An observable list of String of names of all users in the database.
     */
    public static ObservableList<String> getAllUsers(){
        allUsers.clear();

        try {
            String sql = "SELECT * FROM client_schedule.users;";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String contact = rs.getString("User_Name");
                allUsers.add(contact);

            }

            ps.close();
            return allUsers;

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Gets the user id based on the username
     * @param name the username
     * @return the user id (int)
     */
    public static int getUserId(String name){

        try{
            String sql = "SELECT * FROM client_schedule.users WHERE User_Name= ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            int userID = 0;
            if (rs.next()) {
                userID = rs.getInt("User_ID");
                ps.close();
            }

            return userID;

        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }

    }

    /**
     * Gets the username based on the user id
     * @param id the user id
     * @return the username (String)
     */
    public static String getUserName(int id){

        try{
            String sql = "SELECT * FROM client_schedule.users WHERE User_ID= ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            String name = null;
            if (rs.next()) {
                name = rs.getString("User_Name");
                ps.close();

            }
            return name;

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }

    }
}
