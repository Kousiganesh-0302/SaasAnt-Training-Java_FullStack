package com.fourthspring2005.service;

import com.fourthspring2005.dao.EmployeeDAO;
import com.fourthspring2005.exception.BusinessException;
import com.fourthspring2005.exception.DataNotFoundException;
import com.fourthspring2005.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeDAO employeeDAO;

    @Autowired
    public EmployeeServiceImpl(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        if (employeeDAO.existsById(employee.getEmpId())) {
            throw new BusinessException("Employee ID already exists");
        }
        return employeeDAO.save(employee);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        if (!employeeDAO.existsById(employee.getEmpId())) {
            throw new DataNotFoundException("Employee not found");
        }
        return employeeDAO.update(employee);
    }

    @Override
    public void deleteEmployee(String empId) {
        if (!employeeDAO.existsById(empId)) {
            throw new DataNotFoundException("Employee not found");
        }
        employeeDAO.delete(empId);
    }

    @Override
    public Employee getEmployeeById(String empId) {
        Employee employee = employeeDAO.findById(empId);
        if (employee == null) {
            throw new DataNotFoundException("Employee not found");
        }
        return employee;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeDAO.findAll();
    }

    @Override
    public boolean employeeExists(String empId) {
        return employeeDAO.existsById(empId);
    }
}