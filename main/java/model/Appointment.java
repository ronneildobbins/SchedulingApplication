package model;

import dao.contactQuery;
import dao.userQuery;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {

    private int apptId;

    private String apptTitle;

    private String apptDescription;

    private String apptLocation;

    private String apptType;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private int customerID;

    private int userID;

    private int contactID;

    private String Contact;

    private String formatEnd;

    private String formatStart;

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
        this.formatEnd = dtf.format(endTime);
        this.formatStart = dtf.format(startTime);

    }


    public int getApptId() {
        return apptId;
    }

    public void setApptId(int apptId) {
        this.apptId = apptId;
    }

    public String getApptTitle() {
        return apptTitle;
    }

    public void setApptTitle(String apptTitle) {
        this.apptTitle = apptTitle;
    }

    public String getApptDescription() {
        return apptDescription;
    }

    public void setApptDescription(String apptDescription) {
        this.apptDescription = apptDescription;
    }

    public String getApptLocation() {
        return apptLocation;
    }

    public void setApptLocation(String apptLocation) {
        this.apptLocation = apptLocation;
    }

    public String getApptType() {
        return apptType;
    }

    public void setApptType(String apptType) {
        this.apptType = apptType;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getFormatEnd() {
        return formatEnd;
    }

    public void setFormatEnd(String formatEnd) {
        this.formatEnd = formatEnd;
    }

    public String getFormatStart() {
        return formatStart;
    }

    public void setFormatStart(String formatStart) {
        this.formatStart = formatStart;
    }
}
