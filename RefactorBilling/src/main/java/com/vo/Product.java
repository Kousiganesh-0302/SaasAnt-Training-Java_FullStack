package com.vo;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Product implements Serializable {
    private String productId;
    private String productName;
    private double price;
    private double taxPercentage;

    public Product(String productId, String productName, double price, double taxPercentage) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.taxPercentage = taxPercentage;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public double getTaxPercentage() {
        return taxPercentage;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setTaxPercentage(double taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s | Price: ₹%.2f | Tax%% %.2f", productId, productName, price, taxPercentage);
    }
}

