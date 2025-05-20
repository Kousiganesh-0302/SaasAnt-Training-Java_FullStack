////package com.service.impl;
////
////import org.junit.jupiter.api.Test;
////import static org.junit.jupiter.api.Assertions.*;
////
////class InvoiceServiceImplTest {
////  
////  // A minimal subclass that exposes validateMobile
////  static class Svc extends InvoiceServiceImpl {
////    Svc() { super(null, null); }
////    @Override
////    protected String validateMobile(java.util.Scanner sc, String mobile) {
////      return super.validateMobile(sc, mobile);
////    }
////  }
////
////  @Test
////  void validateMobile_rejectsBadInput() {
////    java.io.ByteArrayInputStream in = 
////        new java.io.ByteArrayInputStream("123\n0123456789\n".getBytes());
////    java.util.Scanner sc = new java.util.Scanner(in);
////    
////    String result = new Svc().validateMobile(sc, sc.nextLine());
////    assertEquals("0123456789", result);
////  }
////}
////
////package com.service.impl;
////
////import com.dao.CustomerDao;
////import com.dao.ProductDao;
////import com.vo.Customer;
////import com.vo.Product;
////import com.vo.InvoiceItem;
////import org.junit.jupiter.api.*;
////import java.io.*;
////import java.util.*;
////
////import static org.junit.jupiter.api.Assertions.*;
////
////class InvoiceServiceImplTest {
////  private InvoiceServiceImpl svc;
////  private File tempFile;
////
////  @BeforeEach
////  void setup() throws IOException {
////    // stub DAOs
////    CustomerDao customerDao = id -> Optional.of(new Customer("C1","Alice","0123456789","Madurai"));
////    ProductDao productDao = new ProductDao() {
////      @Override public List<Product> findAll() {
////        return List.of(new Product("P1","Soap",20,5));
////      }
////      // other methods unused
////      public Optional<Product> findById(String id) { return Optional.of(findAll().get(0)); }
////      public void save(Product p) {}
////      public void update(Product p) {}
////      public void delete(String id) {}
////    };
////
////    svc = new InvoiceServiceImpl(customerDao, productDao);
////    
////    // redirect System.in to simulate user:
////    String userInput =
////      "C1\n" +         // existing customer
////      "P1\n" +         // product
////      "2\n" +          // qty
////      "no\n";          // stop
////    System.setIn(new ByteArrayInputStream(userInput.getBytes()));
////
////    // capture System.out
////    System.setOut(new PrintStream(new ByteArrayOutputStream()));
////
////    // prepare to find the invoice file
////    tempFile = new File("invoice_C1_" + System.currentTimeMillis() + ".txt");
////  }
////
////  @AfterEach
////  void cleanup() {
////    System.setIn(System.in);
////    System.setOut(System.out);
////    // delete any invoice files created
////    for (File f : new File(".").listFiles((d,n)->n.startsWith("invoice_C1_"))) {
////      f.delete();
////    }
////  }
////
////  @Test
////  void generateInvoice_createsFileAndPrintsInvoice() throws IOException {
////    svc.generateInvoice();
////
////    // check file exists
////    File[] files = new File(".").listFiles((d,n)->n.startsWith("invoice_C1_") && n.endsWith(".txt"));
////    assertTrue(files.length == 1, "Invoice file should be created");
////
////    // check file contents
////    String content = Files.readString(files[0].toPath());
////    assertTrue(content.contains("INVOICE"), "Should contain header");
////    assertTrue(content.contains("Soap"), "Should list product");
////    assertTrue(content.contains("Quantity |"), "Should include quantity column");
////  }
////}
////
////
//
//package com.test;
//
//import com.dao.CustomerDao;
//import com.dao.ProductDao;
//import com.service.impl.InvoiceServiceImpl;
//import com.vo.Customer;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.Optional;
//import java.util.Scanner;
//
//import static org.junit.Assert.*;
//
//public class InvoiceServiceTest {
//
//    private InvoiceServiceImpl service;
//
//    @Before
//    public void setUp() {
//        // Using dummy dao implementations for real data; no mocking
//        service = new InvoiceServiceImpl(new DummyCustomerDao(), new DummyProductDao());
//    }
//
//    @Test
//    public void testInvalidCustomerId_OnlyNumbers_ShouldFail() {
//        String input = "100";  // Invalid because it lacks prefix
//        Optional<Customer> result = new DummyCustomerDao().findById(input);
//        assertFalse("Expected invalid customer ID", result.isPresent());
//    }
//
//    @Test
//    public void testInvalidCustomerId_EmptyInput_ShouldFail() {
//        String input = "";  // Empty input case
//        Optional<Customer> result = new DummyCustomerDao().findById(input);
//        assertFalse("Expected invalid customer ID", result.isPresent());
//    }
//
//    @Test
//    public void testInvalidCustomerId_NullInput_ShouldFail() {
//        String input = null;  // Null input case
//        Optional<Customer> result = new DummyCustomerDao().findById(input);
//        assertFalse("Expected invalid customer ID", result.isPresent());
//    }
//
//    @Test
//    public void testValidCustomerId_ShouldPass() {
//        String input = "C100";  // Assuming "C100" exists
//        Optional<Customer> result = new DummyCustomerDao().findById(input);
//        assertTrue("Expected valid customer ID", result.isPresent());
//    }
//}


package com.util;

import org.junit.Test;
import static org.junit.Assert.*;

public class ValidatorTest {

    @Test
    public void testValidCustomerIds() {
        assertTrue(Validator.isValidCustomerId("C100"));
        assertTrue(Validator.isValidCustomerId("C1"));
        assertTrue(Validator.isValidCustomerId("C99999"));
    }

    @Test
    public void testInvalidCustomerIds() {
        assertFalse(Validator.isValidCustomerId("100"));    // missing 'C'
        assertFalse(Validator.isValidCustomerId("XC100"));  // wrong prefix
        assertFalse(Validator.isValidCustomerId("C"));      // no digits
        assertFalse(Validator.isValidCustomerId("C12X"));   // non-digit suffix
        assertFalse(Validator.isValidCustomerId(""));       // empty
        assertFalse(Validator.isValidCustomerId(null));     // null
    }

    @Test
    public void testValidMobileNumbers() {
        assertTrue(Validator.isValidMobile("0123456789"));
        assertTrue(Validator.isValidMobile("9876543210"));
    }

    @Test
    public void testInvalidMobileNumbers() {
        assertFalse(Validator.isValidMobile("123456789"));   // 9 digits
        assertFalse(Validator.isValidMobile("12345678901")); // 11 digits
        assertFalse(Validator.isValidMobile("12345abcde"));  // letters
        assertFalse(Validator.isValidMobile(""));            // empty
        assertFalse(Validator.isValidMobile(null));          // null
    }
}
