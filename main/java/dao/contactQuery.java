package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class contactQuery {

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
}

