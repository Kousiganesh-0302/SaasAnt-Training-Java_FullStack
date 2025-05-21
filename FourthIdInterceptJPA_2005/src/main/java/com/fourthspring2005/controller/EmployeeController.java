package com.fourthspring2005.controller;

import com.fourthspring2005.exception.BusinessException;
import com.fourthspring2005.exception.DataNotFoundException;
import com.fourthspring2005.model.Employee;
import com.fourthspring2005.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "employee/list";
    }

    @GetMapping({"/add", "/edit/{id}"})
    public String showForm(@PathVariable(required = false) String id, Model model) {
        if (id != null) {
            model.addAttribute("employee", employeeService.getEmployeeById(id));
        } else {
            model.addAttribute("employee", new Employee());
        }
        model.addAttribute("positions", List.of("Manager", "Developer", "Analyst"));
        return "employee/form";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute Employee employee, RedirectAttributes redirectAttributes) {
        try {
            Employee savedEmployee = employeeService.createEmployee(employee);
            redirectAttributes.addFlashAttribute("employee", savedEmployee);
            return "redirect:/employees/success";
        } catch (BusinessException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/employees/add";
        }
    }

    @GetMapping("/success")
    public String showSuccessPage() {
        return "employee/success";
    }

    @PostMapping("/update")
    public String updateEmployee(@ModelAttribute Employee employee, RedirectAttributes redirectAttributes) {
        try {
            Employee updatedEmployee = employeeService.updateEmployee(employee);
            redirectAttributes.addFlashAttribute("message", "Employee updated successfully");
        } catch (DataNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/employees";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            employeeService.deleteEmployee(id);
            redirectAttributes.addFlashAttribute("message", "Employee deleted successfully");
        } catch (DataNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/employees";
    }

    @GetMapping("/check-id")
    @ResponseBody
    public boolean checkEmployeeId(@RequestParam String empId) {
        return employeeService.employeeExists(empId);
    }
}