package model;

import dao.countryQuery;
import dao.divisionQuery;

import java.sql.SQLException;

public class Customer {

    private int customer_id;

    private String customerName;

    private String customerAddress;

    private String customerPostal;

    private String customerPhone;

    private int divisionId;

    private String Division;

    private String Country;


    public Customer(int id, String name, String address, String postal, String phone, int divId, String division, String country) throws SQLException {

        setCustomer_id(id);

        setCustomerName(name);

        customerAddress = address;

        customerPostal = postal;

        customerPhone = phone;

        divisionId = divId;

        Division = division;

        Country = country;


    }


    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int id) {
        customer_id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerPostal() {
        return customerPostal;
    }

    public void setCustomerPostal(String customerPostal) {
        this.customerPostal = customerPostal;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public String getDivision() {
        return Division;
    }

    public void setDivision(String division) {
        Division = division;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }
}
