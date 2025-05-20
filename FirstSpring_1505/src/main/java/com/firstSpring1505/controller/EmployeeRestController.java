package com.firstSpring1505.controller;

import com.firstSpring1505.model.Employee;
import com.firstSpring1505.service.EmployeeService;
import com.firstSpring1505.service.EmployeeServiceImpl;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeRestController {
    private final EmployeeService employeeService = new EmployeeServiceImpl();

    @PostMapping
    public String createEmployee(@RequestBody Employee emp) throws Exception {
        if (employeeService.existsById(emp.getEmpId())) {
            return "Employee ID already exists";
        }
        employeeService.create(emp);
        return "Employee created successfully";
    }

    @GetMapping
    public List<Employee> getAllEmployees() throws Exception {
        return employeeService.getAll();
    }

    @GetMapping("/{empId}")
    public Object getEmployeeById(@PathVariable String empId) throws Exception {
        Employee employee = employeeService.getById(empId);
        if (employee == null) {
            return "Employee not found";
        }
        return employee;
    }


    @PutMapping("/{empId}")
    public String updateEmployee(@PathVariable String empId, @RequestBody Employee emp) throws Exception {
        emp.setEmpId(empId);
        return employeeService.update(emp) ? "Employee updated" : "Update failed";
    }

    @DeleteMapping("/{empId}")
    public String deleteEmployee(@PathVariable String empId) throws Exception {
        return employeeService.delete(empId) ? "Employee deleted" : "Delete failed";
    }

    @GetMapping("/exists/{empId}")
    public boolean checkEmployeeExists(@PathVariable String empId) throws Exception {
        return employeeService.existsById(empId);
    }

    // Helper method to convert Employee object to JSON string
    private String convertToJson(Employee emp) {
        return String.format(
            "{\"empId\":\"%s\",\"empName\":\"%s\",\"position\":\"%s\",\"phone\":\"%s\"}",
            emp.getEmpId(), emp.getEmpName(), emp.getPosition(), emp.getPhone()
        );
    }
}