package com.service.impl;

import com.dao.CustomerDao;
import com.dao.ProductDao;
import com.service.InvoiceService;
import com.vo.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class InvoiceServiceImpl implements InvoiceService {
    private final CustomerDao customerDao;
    private final ProductDao productDao;
    private final Scanner sc;

    public InvoiceServiceImpl(CustomerDao customerDao, ProductDao productDao) {
        this.customerDao = customerDao;
        this.productDao  = productDao;
        this.sc = new Scanner(System.in);
    }

    @Override
    public void generateInvoice() {
        Store store = new Store("Elite SuperMarket", "Madurai");
        List<Product> products = productDao.findAll();
        for (Product p : products) {
            store.addProduct(p);
        }

        Customer customer = getCustomer(sc);
        store.displayProducts();
        List<InvoiceItem> cart = getCart(sc, store);
        printInvoice(store, customer, cart);
    }

    private Customer getCustomer(Scanner sc) {
        System.out.print("Enter Customer ID: ");
        String cid = sc.nextLine();
        Optional<Customer> opt = customerDao.findById(cid);
        if (opt.isPresent()) {
            Customer customer = opt.get();
            System.out.println("**********************************************************************");
            System.out.println("                            CUSTOMER DETAILS");
            System.out.println("**********************************************************************\n");
            System.out.printf("Customer ID     : %s%n", customer.getCustomerId());
            System.out.printf("Name            : %s%n", customer.getName());
            System.out.printf("Mobile Number   : %s%n", customer.getMobile());
            System.out.printf("Location        : %s%n%n", customer.getLocation());
            return customer;
        } else {
            return registerNewCustomer(cid);
        }
    }

    // FIXED: only takes the ID, uses the field `sc` internally
    private Customer registerNewCustomer(String cid) {
        System.out.println("Customer ID not found. Registering new customer.");
        System.out.print("Enter Customer Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Mobile Number: ");
        String mobile = validateMobile(sc, sc.nextLine());
        System.out.print("Enter Location: ");
        String location = sc.nextLine();

        Customer newCustomer = new Customer(cid, name, mobile, location);
        customerDao.save(newCustomer);
        System.out.println("Customer registered successfully.");
        return newCustomer;
    }

    private String validateMobile(Scanner sc, String mobile) {
        while (!mobile.matches("\\d{10}")) {
            System.out.print("Invalid mobile. Re-enter 10-digit number: ");
            mobile = sc.nextLine();
        }
        return mobile;
    }

    private List<InvoiceItem> getCart(Scanner sc, Store store) {
        Map<String, Double> qtyMap = new LinkedHashMap<>();
        boolean addMore = true;

        while (addMore) {
            System.out.print("\nEnter Product ID: ");
            String pid = sc.nextLine();
            Product product = store.getProduct(pid);
            if (product == null) {
                System.out.println("Product ID not found!");
                continue;
            }
            System.out.print("Enter Quantity: ");
            double qty = sc.nextDouble();
            sc.nextLine();
            qtyMap.put(pid, qtyMap.getOrDefault(pid, 0.0) + qty);

            System.out.print("Add another product? (yes/no): ");
            addMore = sc.nextLine().equalsIgnoreCase("yes");
        }

        List<InvoiceItem> cart = new ArrayList<>();
        for (Map.Entry<String, Double> e : qtyMap.entrySet()) {
            cart.add(new InvoiceItem(store.getProduct(e.getKey()), e.getValue()));
        }
        return cart;
    }

    private void printInvoice(Store store, Customer customer, List<InvoiceItem> cart) {
        StringBuilder sb = new StringBuilder();
        sb.append(generateInvoiceHeader(store, customer));

        double subtotal = 0, tax = 0, grand = 0;
        long epoch = System.currentTimeMillis();
        String filename = "E:/1 Kousi SaasAnt/Eclipse SaasAnt/RefactorBilling/src/main/java/outputtxt/invoice_" + customer.getCustomerId() + "_" + epoch + ".txt";
        
        for (InvoiceItem i : cart) {
            sb.append(generateInvoiceItemDetails(i));
            subtotal += i.getSubtotal();
            tax      += i.getTaxAmount();
            grand    += i.getTotal();
        }
        sb.append(generateInvoiceFooter(subtotal, tax, grand));

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(sb.toString());
            System.out.println("\nInvoice saved for " 
                + customer.getCustomerId() 
                + " as " + filename);
        } catch (IOException e) {
            System.out.println("Failed to save invoice.");
        }

        System.out.println(sb);
    }

    private String generateInvoiceHeader(Store store, Customer customer) {
        return String.format(
            "**********************************************************************%n" +
            "                               INVOICE%n" +
            "**********************************************************************%n%n" +
            "Store Name      : %s%nLocation        : %s%nGST             : GSTIN1234567890%n" +
            "Customer ID     : %s%nName            : %s%nMobile Number   : %s%nDate            : %s%n%n" +
            "                             ITEMS PURCHASED%n" +
            "----------------------------------------------------------------------%n" +
            "| Product Name  |  Price  | Quantity | Tax (%%) | Tax Amt |  Total   |%n" +
            "----------------------------------------------------------------------%n",
            store.getStoreName(), store.getLocation(),
            customer.getCustomerId(), customer.getName(),
            customer.getMobile(), new Date()
        );
    }

    private String generateInvoiceItemDetails(InvoiceItem item) {
        return String.format(
            "| %-13s | ₹%-6.2f | %-8.2f | %-7.1f%% | ₹%-6.2f | ₹%-7.2f |%n",
            item.getProduct().getProductName(),
            item.getProduct().getPrice(),
            item.getQuantity(),
            item.getProduct().getTaxPercentage(),
            item.getTaxAmount(),
            item.getTotal()
        );
    }

    private String generateInvoiceFooter(double sub, double tax, double grand) {
        return String.format(
            "----------------------------------------------------------------------%n" +
            "                            Invoice Summary%n" +
            "----------------------------------------------------------------------%n" +
            "Subtotal        : ₹%.2f%n" +
            "Tax             : ₹%.2f%n" +
            "Grand Total     : ₹%.2f%n" +
            "----------------------------------------------------------------------%n" +
            "Thank you for shopping with us!%n" +
            "----------------------------------------------------------------------%n",
            sub, tax, grand
        );
    }
}
