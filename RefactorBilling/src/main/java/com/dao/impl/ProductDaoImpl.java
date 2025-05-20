package com.dao.impl;

import com.dao.ProductDao;
import com.vo.Product;
import com.util.DBUtil;

import java.sql.*;
import java.util.*;

public class ProductDaoImpl implements ProductDao {
    private final Map<String,Product> cache = new LinkedHashMap<>();
    public ProductDaoImpl() { loadAll(); }

    private void loadAll() {
        try (Connection con = DBUtil.getInstance().getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(
               "SELECT product_id,name,price,tax_percentage FROM products")) {
            while (rs.next()) {
                Product p = new Product(
                  rs.getString("product_id"),
                  rs.getString("name"),
                  rs.getDouble("price"),
                  rs.getDouble("tax_percentage"));
                cache.put(p.getProductId(), p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(cache.values());
    }

    @Override
    public Optional<Product> findById(String id) {
        return Optional.ofNullable(cache.get(id));
    }

    @Override
    public void save(Product product) {
        try (Connection con = DBUtil.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(
                 "INSERT INTO products(product_id,name,price,tax_percentage) VALUES(?,?,?,?)")) {
            ps.setString(1, product.getProductId());
            ps.setString(2, product.getProductName());
            ps.setDouble(3, product.getPrice());
            ps.setDouble(4, product.getTaxPercentage());
            ps.executeUpdate();
            cache.put(product.getProductId(), product);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Product product) {
        try (Connection con = DBUtil.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(
                 "UPDATE products SET name=?, price=?, tax_percentage=? WHERE product_id=?")) {
            ps.setString(1, product.getProductName());
            ps.setDouble(2, product.getPrice());
            ps.setDouble(3, product.getTaxPercentage());
            ps.setString(4, product.getProductId());
            ps.executeUpdate();
            cache.put(product.getProductId(), product);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String productId) {
        try (Connection con = DBUtil.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(
                 "DELETE FROM products WHERE product_id=?")) {
            ps.setString(1, productId);
            ps.executeUpdate();
            cache.remove(productId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
