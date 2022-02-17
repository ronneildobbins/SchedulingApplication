package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class countryQuery {

    private static ObservableList<String> allCountries =  FXCollections.observableArrayList();

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

