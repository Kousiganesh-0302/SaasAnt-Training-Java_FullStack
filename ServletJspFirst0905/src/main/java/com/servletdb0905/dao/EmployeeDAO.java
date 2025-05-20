//package com.servletdb0905.dao;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import com.servletdb0905.model.Employee;
//
//public class EmployeeDAO {
//    private static final String JDBC_URL  = "jdbc:mysql://localhost:3307/saasant_billing";
//    private static final String JDBC_USER = "root";
//    private static final String JDBC_PASS = "12345";
//
//    public void insertEmployee(Employee emp) throws Exception {
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        String sql = "INSERT INTO empformservlet(emp_id, emp_name, position, phone) VALUES (?, ?, ?, ?)";
//
//        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.setString(1, emp.getEmpId());
//            stmt.setString(2, emp.getEmpName());
//            stmt.setString(3, emp.getPosition());
//            stmt.setString(4, emp.getPhone());
//
//            stmt.executeUpdate();
//        }
//    }
//}
//

package com.servletdb0905.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import com.servletdb0905.model.Employee;

public class EmployeeDAO {
    private static final String JDBC_URL  = "jdbc:mysql://localhost:3307/saasant_billing";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "12345";

    public void insertEmployee(Employee emp) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String sql = "INSERT INTO empformservlet(emp_id, emp_name, position, phone) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, emp.getEmpId());
            stmt.setString(2, emp.getEmpName());
            stmt.setString(3, emp.getPosition());
            stmt.setString(4, emp.getPhone());

            stmt.executeUpdate();
        }
    }
    
 // In EmployeeDAO.java, add these two methods:

    public boolean updateEmployee(Employee emp) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String sql = "UPDATE empformservlet SET emp_name = ?, position = ?, phone = ? WHERE emp_id = ?";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, emp.getEmpName());
            stmt.setString(2, emp.getPosition());
            stmt.setString(3, emp.getPhone());
            stmt.setString(4, emp.getEmpId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteEmployeeById(String empId) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String sql = "DELETE FROM empformservlet WHERE emp_id = ?";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, empId);
            return stmt.executeUpdate() > 0;
        }
    }
    public List<Employee> findAll() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String sql = "SELECT emp_id, emp_name, position, phone FROM empformservlet";
        List<Employee> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
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

}

