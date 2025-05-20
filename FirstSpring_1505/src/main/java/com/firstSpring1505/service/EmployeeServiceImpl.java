package com.firstSpring1505.service;

import com.firstSpring1505.dao.EmployeeDAO;
import com.firstSpring1505.dao.EmployeeDAOImpl;
import com.firstSpring1505.model.Employee;
import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeDAO dao = new EmployeeDAOImpl();

    @Override
    public void create(Employee emp) throws Exception {
        dao.insertEmployee(emp);
    }

    @Override
    public boolean update(Employee emp) throws Exception {
        return dao.updateEmployee(emp);
    }

    @Override
    public boolean delete(String id) throws Exception {
        return dao.deleteEmployeeById(id);
    }

    @Override
    public List<Employee> getAll() throws Exception {
        return dao.findAll();
    }

    @Override
    public Employee getById(String id) throws Exception {
        return dao.findById(id);
    }

    @Override
    public boolean existsById(String empId) throws Exception {
        return dao.existsById(empId);
    }
}