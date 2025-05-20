package com.controller;

import com.controller.AdminController;
import com.dao.CustomerDao;
import com.dao.ProductDao;
import com.dao.impl.*;
import com.service.*;
import com.service.impl.InvoiceServiceImpl;
//import com.test.*;
import com.vo.*;

import java.util.Scanner;

public class BillingController {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ProductDao productDao   = new ProductDaoImpl();
        CustomerDao customerDao = new CustomerDaoImpl();
        InvoiceService invoiceService =
          new InvoiceServiceImpl(customerDao, productDao);

        while (true) {
            System.out.println("\n--- Store Application ---");
            System.out.println("1. Admin Panel");
            System.out.println("2. User Purchase");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1 : new AdminController(productDao).start();  
                break;// see below
                case 2 : invoiceService.generateInvoice();
                break;
                case 3 : { System.out.println("Goodbye!"); System.exit(0);}
                break;
                default : System.out.println("Invalid choice.");
                break;
            }
        }
    }
}

