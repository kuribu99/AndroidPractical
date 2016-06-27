package com.kongmy.androidprac;

import java.io.Serializable;

/**
 * Created by Kong My on 27/6/2016.
 */
public class SalesAgent implements Serializable {

    private String name;
    private String contactNumber;
    private String email;

    public SalesAgent(String name, String contactNumber, String email) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getEmail() {
        return email;
    }

}
