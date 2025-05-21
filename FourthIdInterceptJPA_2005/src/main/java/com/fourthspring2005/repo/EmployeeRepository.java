package com.fourthspring2005.repo;

import com.fourthspring2005.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    // Custom query method
    boolean existsByEmpId(String empId);
}