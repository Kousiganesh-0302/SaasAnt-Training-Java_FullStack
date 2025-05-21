package com.fourthspring2005.dao;

import com.fourthspring2005.model.Employee;
import com.fourthspring2005.repo.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;

import java.util.List;



@Repository
public class EmployeeDAOImpl implements EmployeeDAO {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeDAOImpl.class);

    private final EmployeeRepository repository;

    @Autowired
    public EmployeeDAOImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Employee save(Employee employee) {
        logger.info("DAO: Saving employee");
        return repository.save(employee);
    }

    @Override
    public Employee update(Employee employee) {
        logger.info("DAO: Updating employee");
        return repository.save(employee); // JPA save handles both insert and update
    }

    @Override
    public void delete(String empId) {
        logger.info("DAO: Deleting employee");
        repository.deleteById(empId);
    }

    @Override
    public Employee findById(String empId) {
        logger.info("DAO: Finding employee by ID");
        return repository.findById(empId).orElse(null);
    }

    @Override
    public List<Employee> findAll() {
        logger.info("DAO: Finding all employees");
        return repository.findAll();
    }

    @Override
    public boolean existsById(String empId) {
        logger.info("DAO: Checking employee existence");
        return repository.existsByEmpId(empId);
    }
}