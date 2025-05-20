package com.secondspring1605.dao;

import java.util.List;

import com.secondspring1605.model.Employee;

//public interface EmployeeDAO {
//    void insertEmployee(Employee emp) throws Exception;
//    boolean updateEmployee(Employee emp) throws Exception;
//    boolean deleteEmployeeById(String empId) throws Exception;
//    List<Employee> findAll() throws Exception;
//    Employee findById(String empId) throws Exception;
//}

public interface EmployeeDAO {
    void insertEmployee(Employee emp);
    boolean updateEmployee(Employee emp);
    boolean deleteEmployeeById(String empId);
    List<Employee> findAll();
    Employee findById(String empId);
    boolean existsById(String empId);
}