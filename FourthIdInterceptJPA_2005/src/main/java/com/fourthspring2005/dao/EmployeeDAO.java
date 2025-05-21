package com.fourthspring2005.dao;

import com.fourthspring2005.model.Employee;
import java.util.List;

public interface EmployeeDAO {
    Employee save(Employee employee);
    Employee update(Employee employee);
    void delete(String empId);
    Employee findById(String empId);
    List<Employee> findAll();
    boolean existsById(String empId);
}