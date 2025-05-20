package com.firstSpring1505.dao;

import com.firstSpring1505.model.Employee;
import java.util.List;

public interface EmployeeDAO {
    void insertEmployee(Employee emp) throws Exception;
    boolean updateEmployee(Employee emp) throws Exception;
    boolean deleteEmployeeById(String empId) throws Exception;
    List<Employee> findAll() throws Exception;
    Employee findById(String empId) throws Exception;
    boolean existsById(String empId) throws Exception;
}