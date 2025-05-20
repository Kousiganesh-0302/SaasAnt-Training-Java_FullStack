package com.firstSpring1505.dao;

import com.firstSpring1505.model.Employee;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3307/saasant_billing";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "12345";

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
    }

    @Override
    public void insertEmployee(Employee emp) throws Exception {
        String sql = "INSERT INTO empformservlet(emp_id, emp_name, position, phone) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, emp.getEmpId());
            stmt.setString(2, emp.getEmpName());
            stmt.setString(3, emp.getPosition());
            stmt.setString(4, emp.getPhone());
            stmt.executeUpdate();
        }
    }

    @Override
    public boolean updateEmployee(Employee emp) throws Exception {
        String sql = "UPDATE empformservlet SET emp_name = ?, position = ?, phone = ? WHERE emp_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, emp.getEmpName());
            stmt.setString(2, emp.getPosition());
            stmt.setString(3, emp.getPhone());
            stmt.setString(4, emp.getEmpId());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteEmployeeById(String empId) throws Exception {
        String sql = "DELETE FROM empformservlet WHERE emp_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, empId);
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public List<Employee> findAll() throws Exception {
        String sql = "SELECT emp_id, emp_name, position, phone FROM empformservlet";
        List<Employee> list = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Employee e = new Employee();
                e.setEmpId(rs.getString("emp_id"));
                e.setEmpName(rs.getString("emp_name"));
                e.setPosition(rs.getString("position"));
                e.setPhone(rs.getString("phone"));
                list.add(e);
            }
        }
        return list;
    }

    @Override
    public Employee findById(String empId) throws Exception {
        String sql = "SELECT emp_id, emp_name, position, phone FROM empformservlet WHERE emp_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, empId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Employee e = new Employee();
                    e.setEmpId(rs.getString("emp_id"));
                    e.setEmpName(rs.getString("emp_name"));
                    e.setPosition(rs.getString("position"));
                    e.setPhone(rs.getString("phone"));
                    return e;
                }
            }
        }
        return null;
    }

    @Override
    public boolean existsById(String empId) throws Exception {
        String sql = "SELECT 1 FROM empformservlet WHERE emp_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, empId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }
}