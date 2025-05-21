package com.fourthspring2005.service;


import com.fourthspring2005.exception.DataNotFoundException;
import com.fourthspring2005.exception.BusinessException;
import com.fourthspring2005.model.Employee;
import com.fourthspring2005.repository.EmployeeRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private static final String APP_TX_ID = UUID.randomUUID().toString();
    private static final long START_MS = System.currentTimeMillis();
    
    private final EmployeeRepository repository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void init() {
        MDC.put("txId", APP_TX_ID);
        logger.info(">>> Application start [{}] at {} ms since epoch", APP_TX_ID, START_MS);
    }

    @PreDestroy
    public void shutdown() {
        long elapsed = System.currentTimeMillis() - START_MS;
        MDC.put("txId", APP_TX_ID);
        logger.info("<<< Application stop [{}], total uptime = {} ms", APP_TX_ID, elapsed);
        MDC.clear();
    }

    @Override
    public void create(Employee emp) {
        if (repository.existsByEmpId(emp.getEmpId())) {
            throw new BusinessException("Employee ID already exists");
        }
        repository.save(emp);
    }

    @Override
    public boolean update(Employee emp) {
        if (!repository.existsByEmpId(emp.getEmpId())) {
            throw new DataNotFoundException("Employee not found with ID: " + emp.getEmpId());
        }
        repository.save(emp);
        return true;
    }

    @Override
    public boolean delete(String id) {
        if (!repository.existsByEmpId(id)) {
            throw new DataNotFoundException("Employee not found with ID: " + id);
        }
        repository.deleteById(id);
        return true;
    }

    @Override
    public List<Employee> getAll() {
        return repository.findAll();
    }

    @Override
    public Employee getById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Employee not found with ID: " + id));
    }

    @Override
    public boolean existsById(String empId) {
        return repository.existsByEmpId(empId);
    }
}