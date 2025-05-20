package com.dao;

import com.vo.Customer;
import java.util.Optional;

public interface CustomerDao {
    Optional<Customer> findById(String customerId);
    void save(Customer customer);
}