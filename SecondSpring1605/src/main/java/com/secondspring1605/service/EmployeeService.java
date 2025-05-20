package com.secondspring1605.service;

import java.util.List;

import com.secondspring1605.model.Employee;

public interface EmployeeService {
    void create(Employee emp);
    boolean update(Employee emp);
    boolean delete(String empId);
    List<Employee> getAll();
    Employee getById(String empId);
    boolean existsById(String empId);
}