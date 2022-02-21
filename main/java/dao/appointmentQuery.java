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
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


/**
 * Handles all appointments query to database.
 *
 * @author ronneildobbins
 */
public class appointmentQuery {

    /**
     * A list of all the appointments
     */
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    /**
     * A list of all the appointments for the week
     */
    private static ObservableList<Appointment> allAppWeek = FXCollections.observableArrayList();

    /**
     * A list of all the appointments for the month
     */
    private static ObservableList<Appointment> allAppMonth = FXCollections.observableArrayList();

    /**
     * A list of all the appointments for the day
     */
    private static ObservableList<Appointment> allAppDays = FXCollections.observableArrayList();

    /**
     * A list of all the appointments by contacts
     */
    private static ObservableList<Appointment> appointbyContacts = FXCollections.observableArrayList();

    /**
     * A list of all the appointments for a certain user within the next 15 minutes
     */
    private static ObservableList<String> appointReminders = FXCollections.observableArrayList();

    /**
     * Gets a oberservable list of all appointment from database
     * @return Gets an oberservable list of all appointment
     */
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
                LocalDateTime localEnd = endTime.toLocalDateTime();


                Appointment appointment = new Appointment(rs.getInt("Appointment_ID"),
                        rs.getString("Title"), rs.getString("Description"),
                        rs.getString("Location"), rs.getString("Type"), localStart, localEnd,
                        rs.getInt("Customer_ID"), rs.getInt("User_ID"),
                        rs.getInt("Contact_ID"));
                allAppointments.add(appointment);

            }
            ps.close();
            return allAppointments;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets a oberservable list of all appointment within a week from database
     * @return Gets an oberservable list of all appointment within a week
     */
    public static ObservableList<Appointment> getAllAppWeek() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime weekFromNow = LocalDateTime.now().plusDays(7);


        allAppWeek.clear();

        try {

            String sql = "SELECT * FROM client_schedule.appointments WHERE Start BETWEEN ? AND ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(now));
            ps.setTimestamp(2, Timestamp.valueOf(weekFromNow));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                Timestamp startTime = rs.getTimestamp("Start");
                LocalDateTime localStart = startTime.toLocalDateTime();

                Timestamp endTime = rs.getTimestamp("End");
                LocalDateTime localEnd = endTime.toLocalDateTime();


                Appointment appointment = new Appointment(rs.getInt("Appointment_ID"),
                        rs.getString("Title"), rs.getString("Description"),
                        rs.getString("Location"), rs.getString("Type"), localStart, localEnd,
                        rs.getInt("Customer_ID"), rs.getInt("User_ID"),
                        rs.getInt("Contact_ID"));
                allAppWeek.add(appointment);

            }
            ps.close();
            return allAppWeek;


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets a oberservable list of all appointment within a month of current date and time from database
     * @return Gets an oberservable list of all appointment within a month
     */
    public static ObservableList<Appointment> getAllAppMonth() {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime monthFromNow = LocalDateTime.now().plusMonths(1);


        allAppMonth.clear();

        try {

            String sql = "SELECT * FROM client_schedule.appointments WHERE Start BETWEEN ? AND ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(now));
            ps.setTimestamp(2, Timestamp.valueOf(monthFromNow));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                Timestamp startTime = rs.getTimestamp("Start");
                LocalDateTime localStart = startTime.toLocalDateTime();

                Timestamp endTime = rs.getTimestamp("End");
                LocalDateTime localEnd = endTime.toLocalDateTime();


                Appointment appointment = new Appointment(rs.getInt("Appointment_ID"),
                        rs.getString("Title"), rs.getString("Description"),
                        rs.getString("Location"), rs.getString("Type"), localStart, localEnd,
                        rs.getInt("Customer_ID"), rs.getInt("User_ID"),
                        rs.getInt("Contact_ID"));
                allAppMonth.add(appointment);

            }
            ps.close();
            return allAppMonth;


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Checks to ensure no overlap with customer when scheduling. Used when creating an appointment.
     * @param customer_id the customer id
     * @param start the inputted start date & time
     * @param end the inputted end date & time
     * @return true if there is an overlap when scheduling. false if there is not a overlap when scheduling
     */
    public static boolean customerApptOverlap(int customer_id, LocalDateTime start, LocalDateTime end) {

        try {
            String sql = "SELECT * FROM client_schedule.appointments WHERE Customer_Id = ? AND (? >= Start AND ? < End OR" +
                    " ? > Start AND ? <= END OR ? <= Start AND ? >= End)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customer_id);
            ps.setTimestamp(2, Timestamp.valueOf(start));
            ps.setTimestamp(3, Timestamp.valueOf(start));
            ps.setTimestamp(4, Timestamp.valueOf(end));
            ps.setTimestamp(5, Timestamp.valueOf(end));
            ps.setTimestamp(6, Timestamp.valueOf(start));
            ps.setTimestamp(7, Timestamp.valueOf(end));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ps.close();
                return true;
            } else {
                ps.close();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }

    }

    /**
     * Checks to ensure no overlap with customer when scheduling. Used when modifying an appointment.
     * The difference is it checks to ensure that the appointment the user is trying to modify does not show up
     * @param customer_id the customer id
     * @param start the inputted start date & time
     * @param end the inputted end date & time
     * @param id the selected appointment id to modify
     * @return true if there is an overlap when scheduling. false if there is not a overlap when scheduling
     */
    public static boolean customerModApptOverlap(int customer_id, LocalDateTime start, LocalDateTime end, int id) {

        try {
            String sql = "SELECT * FROM client_schedule.appointments WHERE Customer_Id = ? AND (? >= Start AND ? < End OR" +
                    " ? > Start AND ? <= END OR ? <= Start AND ? >= End) AND Appointment_ID != ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customer_id);
            ps.setTimestamp(2, Timestamp.valueOf(start));
            ps.setTimestamp(3, Timestamp.valueOf(start));
            ps.setTimestamp(4, Timestamp.valueOf(end));
            ps.setTimestamp(5, Timestamp.valueOf(end));
            ps.setTimestamp(6, Timestamp.valueOf(start));
            ps.setTimestamp(7, Timestamp.valueOf(end));
            ps.setInt(8, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ps.close();
                return true;
            } else {
                ps.close();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }

    }

    /**
     *
     * Attempts to create a new appointment in the database based on the provided information
     * @param title the inputted appointment title
     * @param desc the inputted appointment description
     * @param location the inputted appointment location
     * @param type the inputted appointment type
     * @param userId the inputted appointment user id
     * @param contactId the inputted appointment contact id
     * @param customerId the inputted appointment customer id
     * @param start the inputted appointment start
     * @param end the inputted appointment end
     * @return true if add appointment was successful. Otherwise, return false
     */
    public static boolean addAppointment(String title, String desc, String location, String type,
                                         int userId, int contactId, int customerId, LocalDateTime start, LocalDateTime end) {
        try {
            String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID)  VALUES(?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, desc);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ps.setInt(7, customerId);
            ps.setInt(8, contactId);
            ps.setInt(9, userId);
            int rowAffected = ps.executeUpdate();
            if (rowAffected == 1) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }


    }

    /**
     * Attempts to modify an existing appointment in the database based on the provided information
     * @param id the selected appointment id to modify
     * @param title the inputted appointment title
     * @param desc the inputted appointment description
     * @param location the inputted appointment location
     * @param type the inputted appointment type
     * @param userId the inputted appointment user id
     * @param contactId the inputted appointment contact id
     * @param customerId the inputted appointment customer id
     * @param start the inputted appointment start
     * @param end the inputted appointment end
     * @return true if modify appointment was successful. Otherwise, return false
     */
    public static boolean modAppointment(int id, String title, String desc, String location, String type,
                                         int userId, int contactId, int customerId, LocalDateTime start, LocalDateTime end) {
        try {
            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, desc);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ps.setInt(7, customerId);
            ps.setInt(8, contactId);
            ps.setInt(9, userId);
            ps.setInt(10, id);
            int rowAffected = ps.executeUpdate();
            if (rowAffected == 1) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }


    }

    /**
     * Delete the appointment based on inputted appointment id.
     * @param id the appointment id
     * @return true if the deletion was successful and false otherwise
     */
    public static boolean deleteAppointment(int id) {
        try {
            String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, id);
            int rowAffected = ps.executeUpdate();
            if (rowAffected == 1) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }


    }

    /**
     * Delete all appointments based on inputted customer id. Used when deleting a customer
     * @param customer_id the customer id
     * @return true if the deletion was successful and false otherwise
     */
    public static boolean deleteApptById(int customer_id) {
        try {
            String sql = "DELETE FROM appointments WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customer_id);
            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            return false;
        }


    }


    /**
     * Gets the number of appointments within the next 16 minutes
     * @return a list of Strings of the appointments within the next 16 minutes
     * (Gave an extra min just in case)
     */
    public static ObservableList<String> getAppointReminders() {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reminderTime = LocalDateTime.now().plusMinutes(16);

        appointReminders.clear();

        try {
            String sql = "SELECT * FROM client_schedule.appointments WHERE Start BETWEEN ? AND ? AND User_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(now));
            ps.setTimestamp(2, Timestamp.valueOf(reminderTime));
            ps.setInt(3, userQuery.getCurrentUser().getUserId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            int id = rs.getInt("Appointment_ID");
            Timestamp start = rs.getTimestamp("Start");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
            String reminder = "Appointment ID: " + id + " At Date/Time: " + dtf.format(start.toLocalDateTime()) + "\n";
            appointReminders.add(reminder);
            }

            ps.close();
            return appointReminders;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets the number of appointments based on the month and type parameters. Used for report 1
     * @param month the number of the month inputted by the user
     * @param type the type of appointment inputted by the user
     * @return the number of appointments based on the month and type parameters. (int)
     */
    public static int getAppointByMonthType(int month, String type){

        try {
            String sql;
            PreparedStatement ps;
            if (type.isEmpty()) {
                sql = "SELECT COUNT(*) FROM client_schedule.appointments WHERE MONTH(START) = ?";
                ps = JDBC.connection.prepareStatement(sql);
                ps.setInt(1, month);
            }
            else if(month == 0){
                sql = "SELECT COUNT(*) FROM client_schedule.appointments WHERE Type = ?";
                ps = JDBC.connection.prepareStatement(sql);
                ps.setString(1, type);
            }
            else{
                sql = "SELECT COUNT(*) FROM client_schedule.appointments WHERE MONTH(START) = ? AND Type = ?";
                ps = JDBC.connection.prepareStatement(sql);
                ps.setInt(1, month);
                ps.setString(2, type);
            }

            ResultSet rs = ps.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            ps.close();
            return count;


        }catch(SQLException e){
            e.printStackTrace();
            return 0;
            //display alert
        }


    }

    /**
     * Get a list of appointments within 1 day (24 Hours). Used for report 3
     * @return  a list of appointments within 1 day (24 Hours)
     */
    public static ObservableList<Appointment> getAllAppointDays() {

        allAppDays.clear();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dayFromNow = LocalDateTime.now().plusDays(1);

        try {
            String sql = "SELECT * FROM client_schedule.appointments WHERE Start BETWEEN ? AND ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(now));
            ps.setTimestamp(2, Timestamp.valueOf(dayFromNow));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                Timestamp startTime = rs.getTimestamp("Start");
                LocalDateTime localStart = startTime.toLocalDateTime();

                Timestamp endTime = rs.getTimestamp("End");
                LocalDateTime localEnd = endTime.toLocalDateTime();


                Appointment appointment = new Appointment(rs.getInt("Appointment_ID"),
                        rs.getString("Title"), rs.getString("Description"),
                        rs.getString("Location"), rs.getString("Type"), localStart, localEnd,
                        rs.getInt("Customer_ID"), rs.getInt("User_ID"),
                        rs.getInt("Contact_ID"));
                allAppDays.add(appointment);

            }
            ps.close();
            return allAppDays;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get a list of appointments based on the contact
     * @param contactId the contact id which determines which appointments to show
     * @return a list of appointments based on the contact
     */
    public static ObservableList<Appointment> getAppByContact(int contactId) {

        appointbyContacts.clear();

        try {

            String sql = "SELECT * FROM client_schedule.appointments WHERE Contact_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, contactId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                Timestamp startTime = rs.getTimestamp("Start");
                LocalDateTime localStart = startTime.toLocalDateTime();

                Timestamp endTime = rs.getTimestamp("End");
                LocalDateTime localEnd = endTime.toLocalDateTime();


                Appointment appointment = new Appointment(rs.getInt("Appointment_ID"),
                        rs.getString("Title"), rs.getString("Description"),
                        rs.getString("Location"), rs.getString("Type"), localStart, localEnd,
                        rs.getInt("Customer_ID"), rs.getInt("User_ID"),
                        rs.getInt("Contact_ID"));
                appointbyContacts.add(appointment);

            }
            ps.close();
            return appointbyContacts;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
