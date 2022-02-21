package model;

import dao.contactQuery;
import dao.userQuery;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Creates a appointment and saves appointment information
 *
 * @author ronneildobbins
 */
public class Appointment {

    /**
     * the appointment id
     */
    private int apptId;

    /**
     * the appointment title
     */
    private String apptTitle;

    /**
     * the appointment description
     */
    private String apptDescription;

    /**
     * the appointment location
     */
    private String apptLocation;

    /**
     * the appointment type
     */
    private String apptType;

    /**
     * the appointment start time
     */
    private LocalDateTime startTime;

    /**
     * the appointment end time
     */
    private LocalDateTime endTime;

    /**
     * the appointment customer id
     */
    private int customerID;

    /**
     * the appointment user id
     */
    private int userID;

    /**
     * the appointment contact id
     */
    private int contactID;

    /**
     * the appointment contact
     */
    private String Contact;

    /**
     * the appointment end time formatted (MM-dd-yyyy HH:mm)
     */
    private String formatEnd;

    /**
     * the appointment start time formatted (MM-dd-yyyy HH:mm)
     */
    private String formatStart;

    /**
     *
     * Constructs an appointment with the required parameter. Get the contact via the contact id provided.
     * Also format the start and end time for use in tables
     *
     * @param apptId the appointment's id
     * @param apptTitle the appointment's title
     * @param apptDescription the appointment's description
     * @param apptLocation the appointment's location
     * @param apptType the appointment's type
     * @param startTime the appointment's start time
     * @param endTime the appointment's end time
     * @param customerID the appointment's customer id
     * @param userID the appointment's user id
     * @param contactID the appointment's contact id
     */
    public Appointment(int apptId, String apptTitle, String apptDescription, String apptLocation,
                       String apptType, LocalDateTime startTime, LocalDateTime endTime, int customerID,
                       int userID, int contactID){

        this.apptId = apptId;
        this.apptTitle = apptTitle;
        this.apptDescription = apptDescription;
        this.apptLocation = apptLocation;
        this.apptType = apptType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
        this.Contact = contactQuery.getContact(this.contactID);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
        this.formatStart = dtf.format(startTime);
        this.formatEnd = dtf.format(endTime);


    }


    /**
     * Gets the appointment's id
     * @return the appointemnt's id
     */
    public int getApptId() {
        return apptId;
    }


    /**
     * Gets the appointment's title
     * @return the appointment's title
     */
    public String getApptTitle() {
        return apptTitle;
    }


    /**
     * Gets the appointment's description
     * @return the appointment's description
     */
    public String getApptDescription() {
        return apptDescription;
    }


    /**
     * Gets the appointment's location
     * @return the appointment's location
     */
    public String getApptLocation() {
        return apptLocation;
    }


    /**
     * Gets the appointment's type
     * @return the appointment's type
     */
    public String getApptType() {
        return apptType;
    }

    /**
     * Gets the appointment's start time
     * @return the appointment's start time
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Sets the apppintment's start time
     * @param startTime the desired start time
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the appointment's end time
     * @return the appointment's end time
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * Sets the apppintment's end time
     * @param endTime the desired end time
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets the appointment's customer id
     * @return the appointment's customer id
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Sets the appointment's customer id
     * @param customerID the desired customer id
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Gets the appointment's user id
     * @return the appointment's user id
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Sets the appointment's user id
     * @param userID the desired user id
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Gets the appointment's contact id
     * @return the appointment's contact id
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Sets the appointment's contact id
     * @param contactID the desired contact id
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Gets the appointment's contact (String)
     * @return Gets the appointment's contact (String)
     */
    public String getContact() {
        return Contact;
    }

    /**
     * Sets the appointment's contact (String)
     * @param contact the desired appointment's contact (String)
     */
    public void setContact(String contact) {
        Contact = contact;
    }

    /**
     * Gets the appointment's formatted end time (String) (MM-dd-yyyy HH:mm)
     * @return the appointment's formatted end time (String) (MM-dd-yyyy HH:mm)
     */
    public String getFormatEnd() {
        return formatEnd;
    }

    /**
     * Sets the appointment's formatted end time (String) (MM-dd-yyyy HH:mm)
     * @param formatEnd the desired formatted end time (String) (MM-dd-yyyy HH:mm)
     */
    public void setFormatEnd(String formatEnd) {
        this.formatEnd = formatEnd;
    }

    /**
     * Gets the appointment's formatted start time (String) (MM-dd-yyyy HH:mm)
     * @return the appointment's formatted start time (String) (MM-dd-yyyy HH:mm)
     */
    public String getFormatStart() {
        return formatStart;
    }

    /**
     * Sets the appointment's formatted start time (String) (MM-dd-yyyy HH:mm)
     * @param formatStart the desired formatted start time (String) (MM-dd-yyyy HH:mm)
     */
    public void setFormatStart(String formatStart) {
        this.formatStart = formatStart;
    }
}
