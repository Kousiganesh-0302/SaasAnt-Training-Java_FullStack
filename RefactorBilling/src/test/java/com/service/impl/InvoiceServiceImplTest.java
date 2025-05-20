// src/test/java/com/service/impl/InvoiceServiceImplTest.java
package com.service.impl;

import com.dao.CustomerDao;
import com.dao.ProductDao;
import com.vo.Customer;
import com.vo.Product;

import org.junit.Before;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import static org.junit.Assert.*;

public class InvoiceServiceImplTest {
    private InvoiceServiceImpl service;

    @Before
    public void setUp() {
        // Stub CustomerDao: in-memory map
        CustomerDao customerDao = new CustomerDao() {
            private final Map<String, Customer> map = new HashMap<>();
            @Override public Optional<Customer> findById(String id) { return Optional.ofNullable(map.get(id)); }
            @Override public void save(Customer c) { map.put(c.getCustomerId(), c); }
        };
        // Stub ProductDao: empty product list
        ProductDao productDao = new ProductDao() {
            @Override public List<Product> findAll() { return Collections.emptyList(); }
            @Override public Optional<Product> findById(String id) { return Optional.empty(); }
            @Override public void save(Product p) {}
            @Override public void update(Product p) {}
            @Override public void delete(String id) {}
        };
        service = new InvoiceServiceImpl(customerDao, productDao);
    }

    /** 
     * Simulate entering an existing Customer ID (\"C100\") 
     * and expect the same Customer returned.
     */
    @Test
    public void testGetCustomer_existing() throws Exception {
        // seed the DAO with a Customer C100
        Customer existing = new Customer("C200", "Alice", "0123456789", "Wonderland");
        Field daoField = InvoiceServiceImpl.class.getDeclaredField("customerDao");
        daoField.setAccessible(true);
        ((CustomerDao) daoField.get(service)).save(existing);

        // inject a Scanner that will feed "C100\n"
        Scanner sc = new Scanner("C100\n");
        Field scField = InvoiceServiceImpl.class.getDeclaredField("sc");
        scField.setAccessible(true);
        scField.set(service, sc);

        // call private getCustomer(Scanner) via reflection
        Method m = InvoiceServiceImpl.class.getDeclaredMethod("getCustomer", Scanner.class);
        m.setAccessible(true);
        Customer result = (Customer) m.invoke(service, sc);

        assertEquals("C100", result.getCustomerId());
        assertEquals("Alice", result.getName());
        assertEquals("0123456789", result.getMobile());
    }

    /**
     * Simulate entering an invalid Customer ID ("100"),
     * then providing name/mobile/location,
     * and expect a new Customer to be registered.
     */
    @Test
    public void testGetCustomer_newRegistration() throws Exception {
        String input = String.join("\n",
            "100",            // first prompt: ID
            "Bob",            // Customer Name
            "9876543210",     // Mobile
            "Builderland"     // Location
        ) + "\n";

        Scanner sc = new Scanner(input);
        // inject our test Scanner
        Field scField = InvoiceServiceImpl.class.getDeclaredField("sc");
        scField.setAccessible(true);
        scField.set(service, sc);

        // invoke getCustomer
        Method m = InvoiceServiceImpl.class.getDeclaredMethod("getCustomer", Scanner.class);
        m.setAccessible(true);
        Customer result = (Customer) m.invoke(service, sc);

        assertEquals("100", result.getCustomerId());
        assertEquals("Bob", result.getName());
        assertEquals("9876543210", result.getMobile());
        assertEquals("Builderland", result.getLocation());
    }
}
