package com.secondspring1605.dao;

import com.secondspring1605.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EmployeeDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insertEmployee(Employee emp) {
        String sql = "INSERT INTO empformservlet(emp_id, emp_name, position, phone) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, emp.getEmpId(), emp.getEmpName(), emp.getPosition(), emp.getPhone());
    }

    @Override
    public boolean updateEmployee(Employee emp) {
        String sql = "UPDATE empformservlet SET emp_name = ?, position = ?, phone = ? WHERE emp_id = ?";
        int rows = jdbcTemplate.update(sql, emp.getEmpName(), emp.getPosition(), emp.getPhone(), emp.getEmpId());
        return rows > 0;
    }

    @Override
    public boolean deleteEmployeeById(String empId) {
        String sql = "DELETE FROM empformservlet WHERE emp_id = ?";
        int rows = jdbcTemplate.update(sql, empId);
        return rows > 0;
    }

    @Override
    public List<Employee> findAll() {
        String sql = "SELECT emp_id, emp_name, position, phone FROM empformservlet";
        return jdbcTemplate.query(sql, new EmployeeRowMapper());
    }

    @Override
    public Employee findById(String empId) {
        String sql = "SELECT emp_id, emp_name, position, phone FROM empformservlet WHERE emp_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new EmployeeRowMapper(), empId);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean existsById(String empId) {
        String sql = "SELECT 1 FROM empformservlet WHERE emp_id = ?";
        try {
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, empId);
            return count != null && count > 0;
        } catch (Exception e) {
            return false;
        }
    }

    private static class EmployeeRowMapper implements RowMapper<Employee> {
        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employee e = new Employee();
            e.setEmpId(rs.getString("emp_id"));
            e.setEmpName(rs.getString("emp_name"));
            e.setPosition(rs.getString("position"));
            e.setPhone(rs.getString("phone"));
            return e;
        }
    }
}