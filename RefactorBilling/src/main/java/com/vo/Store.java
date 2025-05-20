package com.vo;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.*;
import java.util.stream.IntStream;

import java.util.LinkedHashMap;
public class Store {
    private String storeName;
    private String location;
    private Map<String, Product> products;

    public Store(String storeName, String location) {
        this.storeName = storeName;
        this.location = location;
        this.products = new LinkedHashMap<String, Product>();
    }

    public String getStoreName() {
        return storeName;
    }

    public String getLocation() {
        return location;
    }

    public void addProduct(Product product) {
        products.put(product.getProductId(), product);
    }

    public Product getProduct(String productId) {
        return products.get(productId);
    }

        public void displayProducts() {
        System.out.println("**********************************************************************");
        System.out.println("                           AVAILABLE PRODUCTS");
        System.out.println("**********************************************************************");


        int index = 1;
        for (Product product : products.values()) {
            System.out.printf("%d. Product ID   : %s%n", index, product.getProductId());
            System.out.printf("   Name         : %s%n", product.getProductName());
            System.out.printf("   Price        : ₹%.2f%n", product.getPrice());
            System.out.printf("   Tax Percentage: %.2f%%%n%n", product.getTaxPercentage());
            index++;
        }
    }
}    
