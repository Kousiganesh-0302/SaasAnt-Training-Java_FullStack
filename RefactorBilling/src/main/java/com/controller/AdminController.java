package com.controller;


import com.dao.ProductDao;
import com.vo.Product;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class AdminController {
    private final ProductDao productDao;
    private final Scanner sc = new Scanner(System.in);

    public AdminController(ProductDao productDao) {
        this.productDao = productDao;
    }

    public void start() {
        while (true) {
            System.out.println("\n--- Admin Panel ---");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Delete Product");
            System.out.println("4. View Products");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 : addProduct(); break;
                case 2 : updateProduct(); break;
                case 3 : deleteProduct(); break;
                case 4 : viewProducts(); break;
                case 5 : {
                    return;
                } 
                default : System.out.println("Invalid choice. Please try again."); break;
            }
        }
    }

    private void addProduct() {
        System.out.print("Enter Product ID: ");
        String id = sc.nextLine();
        if (productDao.findById(id).isPresent()) {
            System.out.println("Product ID already exists.");
            return;
        }
        System.out.print("Enter Product Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Price: ");
        double price = Double.parseDouble(sc.nextLine());
        System.out.print("Enter Tax Percentage: ");
        double tax = Double.parseDouble(sc.nextLine());

        Product p = new Product(id, name, price, tax);
        productDao.save(p);
        System.out.println("Product added successfully.");
    }

    private void updateProduct() {
        System.out.print("Enter Product ID to update: ");
        String id = sc.nextLine();
        Optional<Product> opt = productDao.findById(id);
        if (opt.isEmpty()) {
            System.out.println("Product not found.");
            return;
        }
        Product product = opt.get();
        System.out.print("Enter New Product Name: ");
        product.setProductName(sc.nextLine());
        System.out.print("Enter New Price: ");
        product.setPrice(Double.parseDouble(sc.nextLine()));
        System.out.print("Enter New Tax Percentage: ");
        product.setTaxPercentage(Double.parseDouble(sc.nextLine()));

        productDao.update(product);
        System.out.println("Product updated successfully.");
    }

    private void deleteProduct() {
        System.out.print("Enter Product ID to delete: ");
        String id = sc.nextLine();
        productDao.delete(id);
        System.out.println("If existed, product deleted successfully.");
    }

    private void viewProducts() {
        List<Product> list = productDao.findAll();
        System.out.println("\n--- Product List ---");
        System.out.printf("%-10s %-20s %-10s %-10s%n", "ID", "Name", "Price", "Tax%");
        for (Product p : list) {
            System.out.printf("%-10s %-20s %-10.2f %-10.2f%n",
                p.getProductId(), p.getProductName(), p.getPrice(), p.getTaxPercentage());
        }
    }
}

