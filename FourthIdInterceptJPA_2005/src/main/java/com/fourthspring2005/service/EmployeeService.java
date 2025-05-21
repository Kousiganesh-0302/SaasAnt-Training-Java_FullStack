package com.fourthspring2005.service;

import java.util.List;

import com.fourthspring2005.model.Employee;

//public interface EmployeeService {
//    void create(Employee emp);
//    boolean update(Employee emp);
//    boolean delete(String empId);
//    List<Employee> getAll();
//    Employee getById(String empId);
//    boolean existsById(String empId);
//}

public interface EmployeeService {
    Employee createEmployee(Employee employee);
    Employee updateEmployee(Employee employee);
    void deleteEmployee(String empId);
    Employee getEmployeeById(String empId);
    List<Employee> getAllEmployees();
    boolean employeeExists(String empId);
}