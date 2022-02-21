package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Handles all division query to database.
 *
 * @author ronneildobbins
 */
public class divisionQuery {

    /**
     * A list of the names of all the division
     */
    private static ObservableList<String> allDivision =  FXCollections.observableArrayList();

    /**
     * Gets a division based on division id
     * @param divID the division id
     * @return the String of the division
     * @throws SQLException If a problem with the database occurs
     */
    public static String getDivision(int divID) throws SQLException {

        String sql = "SELECT * FROM client_schedule.first_level_divisions WHERE Division_ID = " + divID;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        String division = null;
        if (rs.next()) {
            division = rs.getString("Division");
            ps.close();
        }


        return division;
    }

    /**
     * Gets a list of all division associated with the country id
     * @param country_id the country id
     * @return A list of all division associated with the country id
     */
    public static ObservableList<String> getAllDivision(int country_id) {

        allDivision.clear();

        try {
            String sql = "SELECT * FROM client_schedule.first_level_divisions WHERE Country_ID = " + country_id;
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String division = rs.getString("Division");
                allDivision.add(division);

            }

            ps.close();
            return allDivision;

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }

    }

    /**
     *  Gets a division id based on division
     * @param division the String of the division
     * @return the division id
     */
    public static int getDivID(String division) {

        try {
            String sql = "SELECT * FROM client_schedule.first_level_divisions WHERE Division= ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, division);
            ResultSet rs = ps.executeQuery();
            int divID = 0;
            if (rs.next()) {
                divID = rs.getInt("Division_ID");
                ps.close();
            }

            return divID;

        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }

    }
}
