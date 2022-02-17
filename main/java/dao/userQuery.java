package dao;

import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class userQuery {

    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

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
        }

        return false;

    }
}
