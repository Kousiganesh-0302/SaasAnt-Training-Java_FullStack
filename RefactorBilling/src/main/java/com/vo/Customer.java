package com.vo;


import java.io.Serializable;

public class Customer implements Serializable {
    private String customerId, name, mobile, location;

    public Customer(String customerId, String name, String mobile, String location) {
        this.customerId = customerId;
        this.name = name;
        this.mobile = mobile;
        this.location = location;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getLocation() {
        return location;
    }

    public String getDetails() {
        return String.format("Customer ID: %s | Name: %s | Mobile: %s | Location: %s", customerId, name, mobile, location);
    }
}

