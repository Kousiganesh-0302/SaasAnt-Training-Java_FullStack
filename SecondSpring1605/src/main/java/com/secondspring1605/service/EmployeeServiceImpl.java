package com.secondspring1605.service;

import com.secondspring1605.dao.EmployeeDAO;
import com.secondspring1605.dao.EmployeeDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.secondspring1605.model.Employee;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDAO dao;

    @Autowired
    public EmployeeServiceImpl(EmployeeDAO dao) {
        this.dao = dao;
    }

    @Override
    public void create(Employee emp) {
        dao.insertEmployee(emp);
    }

    @Override
    public boolean update(Employee emp) {
        return dao.updateEmployee(emp);
    }

    @Override
    public boolean delete(String id) {
        return dao.deleteEmployeeById(id);
    }

    @Override
    public List<Employee> getAll() {
        return dao.findAll();
    }

    @Override
    public Employee getById(String id) {
        return dao.findById(id);
    }

    @Override
    public boolean existsById(String empId) {
        return dao.existsById(empId);
    }
}