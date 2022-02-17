package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class divisionQuery {

    private static ObservableList<String> allDivision =  FXCollections.observableArrayList();

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
