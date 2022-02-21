package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Handles all country query to database.
 *
 * @author ronneildobbins
 */
public class countryQuery {

    /**
     * A list of the names of all the countries
     */
    private static ObservableList<String> allCountries =  FXCollections.observableArrayList();

    /**
     * Gets a country based on division id
     * @param divID the division id
     * @return the String of the country
     * @throws SQLException If a problem with the database occurs
     */
    public static String getCountry(int divID) throws SQLException {

        String sql = "SELECT * FROM client_schedule.first_level_divisions WHERE Division_ID = " + divID;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        int country_id = 0;
        if (rs.next()) {
            country_id = rs.getInt("Country_ID");
            ps.close();
        }

        String country = null;
        switch(country_id){
            case 0:
                return country;
            case 1:
                country = "U.S";
                return country;
            case 2:
                country = "UK";
                return country;
            case 3:
                country = "Canada";
                return country;
        }


        return null;
    }

    /**
     * Gets a list of names of all countries based on the database.
     * @return An observable list of String of names of all countries in the database.
     */
    public static ObservableList<String> getAllCountries(){
        allCountries.clear();

        try {
            String sql = "SELECT * FROM client_schedule.countries";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String country = rs.getString("Country");
                allCountries.add(country);

            }

            ps.close();
            return allCountries;

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }

    }

}

