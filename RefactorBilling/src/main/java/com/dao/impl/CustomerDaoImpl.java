package com.dao.impl;


import com.dao.CustomerDao;
import com.vo.Customer;
import com.util.DBUtil;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CustomerDaoImpl implements CustomerDao {
    private final Map<String,Customer> cache = new HashMap<>();
    public CustomerDaoImpl() { loadAll(); }

    private void loadAll() {
        try (Connection con = DBUtil.getInstance().getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM customers")) {
            while (rs.next()) {
                Customer c = new Customer(
                  rs.getString("customer_id"),
                  rs.getString("name"),
                  rs.getString("mobile"),
                  rs.getString("location"));
                cache.put(c.getCustomerId(), c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Customer> findById(String id) {
        return Optional.ofNullable(cache.get(id));
    }

    @Override
    public void save(Customer customer) {
        try (Connection con = DBUtil.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(
               "INSERT INTO customers(customer_id,name,mobile,location) VALUES(?,?,?,?)")) {
            ps.setString(1, customer.getCustomerId());
            ps.setString(2, customer.getName());
            ps.setString(3, customer.getMobile());
            ps.setString(4, customer.getLocation());
            ps.executeUpdate();
            cache.put(customer.getCustomerId(), customer);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
