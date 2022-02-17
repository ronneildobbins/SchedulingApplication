package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class appointmentQuery {

    private static ObservableList<Appointment> allAppointments =  FXCollections.observableArrayList();

    private static ObservableList<Appointment> allAppWeek =  FXCollections.observableArrayList();

    private static ObservableList<Appointment> allAppMonth =  FXCollections.observableArrayList();

    public static ObservableList<Appointment> getAllAppointments() {
        allAppointments.clear();

        try {

            String sql = "SELECT * FROM client_schedule.appointments";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

               Timestamp startTime = rs.getTimestamp("Start");
               LocalDateTime localStart = startTime.toLocalDateTime();

                Timestamp endTime = rs.getTimestamp("End");
                LocalDateTime localEnd = startTime.toLocalDateTime();


                Appointment appointment = new Appointment(rs.getInt("Appointment_ID"),
                        rs.getString("Title"), rs.getString("Description"),
                        rs.getString("Location"),rs.getString("Type"), localStart, localEnd,
                        rs.getInt("Customer_ID"), rs.getInt("User_ID"),
                        rs.getInt("Contact_ID"));
                allAppointments.add(appointment);

            }
            ps.close();
            return allAppointments;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public static ObservableList<Appointment> getAllAppWeek() {
        return allAppWeek;
    }

    public static ObservableList<Appointment> getAllAppMonth() {
        return allAppMonth;
    }

    public static boolean addAppointment(){

        return true;
    }


}
