package com.dao;

import com.vo.Product;
import java.util.List;
import java.util.Optional;

public interface ProductDao {
    List<Product> findAll();
    Optional<Product> findById(String productId);
    void save(Product product);
    void update(Product product);
    void delete(String productId);
}
