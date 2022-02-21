package model;

import dao.countryQuery;
import dao.divisionQuery;

import java.sql.SQLException;


/**
 *
 * Creates a Customer and saves customer information
 *
 * @author ronneildobbins
 */
public class Customer {

    /**
     * the customer's ID
     */
    private int customer_id;

    /**
     * the customer's name
     */
    private String customerName;

    /**
     * the customer's address
     */
    private String customerAddress;

    /**
     * the customer's postal
     */
    private String customerPostal;

    /**
     * the customer's phone
     */
    private String customerPhone;

    /**
     *  the customer's division id
     */
    private int divisionId;

    /**
     * the customer's division
     */
    private String Division;

    /**
     * the customer's country
     */
    private String Country;


    /**
     *
     *  Constructs a customer with the required parameter
     *
     * @param id the customer's ID
     * @param name the customer's name
     * @param address the customer's address
     * @param postal the customer's postal
     * @param phone the customer's phone
     * @param divId the customer's division id
     * @param division the customer's division
     * @param country the customer's country
     */
    public Customer(int id, String name, String address, String postal, String phone, int divId, String division, String country){

        customer_id = id;

        customerName = name;

        customerAddress = address;

        customerPostal = postal;

        customerPhone = phone;

        divisionId = divId;

        Division = division;

        Country = country;


    }


    /**
     * Gets the customer's id
     * @return the customer's id
     */
    public int getCustomer_id() {
        return customer_id;
    }

    /**
     * Gets the customer's name
     * @return the customer's name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Gets the customer's address
     * @return the customer's address
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * Gets the customer's postal
     * @return the customer's postal
     */
    public String getCustomerPostal() {
        return customerPostal;
    }

    /**
     * Gets the customer's phone
     * @return the customer's phone
     */
    public String getCustomerPhone() {
        return customerPhone;
    }

    /**
     * Gets the customer's division id
     * @return the customer's division id
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Gets the customer's division
     * @return the customer's division
     */
    public String getDivision() {
        return Division;
    }

    /**
     * Gets the customer's country
     * @return the customer's country
     */
    public String getCountry() {
        return Country;
    }

}
