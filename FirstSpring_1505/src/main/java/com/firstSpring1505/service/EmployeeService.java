package com.firstSpring1505.service;

import com.firstSpring1505.model.Employee;
import com.firstSpring1505.dao.*;
import java.util.List;

public interface EmployeeService {
    void create(Employee emp) throws Exception;
    boolean update(Employee emp) throws Exception;
    boolean delete(String empId) throws Exception;
    List<Employee> getAll() throws Exception;
    Employee getById(String empId) throws Exception;
    boolean existsById(String empId) throws Exception;
}