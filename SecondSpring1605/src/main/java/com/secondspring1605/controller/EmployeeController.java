package com.secondspring1605.controller;

import com.secondspring1605.model.Employee;
import com.secondspring1605.service.EmployeeService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAll());
        return "employee/list";
    }

    @GetMapping({"/add", "/edit/{id}"})
    public String showForm(@PathVariable(required = false) String id, Model model) {
        if (id != null) {
            model.addAttribute("employee", employeeService.getById(id));
        } else {
            model.addAttribute("employee", new Employee());
        }
        // add list of positions
        model.addAttribute("positions", List.of("Manager", "Developer", "Analyst"));
        return "employee/form";
    }


    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute Employee employee, 
                             RedirectAttributes redirectAttributes) {
        if (employeeService.existsById(employee.getEmpId())) {
            redirectAttributes.addFlashAttribute("error", "Employee ID already exists");
            return "redirect:/employees/add";
        }
        
        employeeService.create(employee);
        redirectAttributes.addFlashAttribute("employee", employee);
        return "redirect:/employees/success";
    }

    @GetMapping("/success")
    public String showSuccessPage() {
        return "employee/success";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Employee employee = employeeService.getById(id);
        if (employee == null) {
            return "redirect:/employees";
        }
        model.addAttribute("employee", employee);
        return "employee/form";
    }

    @PostMapping("/update")
    public String updateEmployee(@ModelAttribute Employee employee, 
                               RedirectAttributes redirectAttributes) {
        boolean updated = employeeService.update(employee);
        if (updated) {
            redirectAttributes.addFlashAttribute("message", "Employee updated successfully");
        } else {
            redirectAttributes.addFlashAttribute("error", "Update failed");
        }
        return "redirect:/employees";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable String id, RedirectAttributes redirectAttributes) {
        boolean deleted = employeeService.delete(id);
        if (deleted) {
            redirectAttributes.addFlashAttribute("message", "Employee deleted successfully");
        } else {
            redirectAttributes.addFlashAttribute("error", "Delete failed");
        }
        return "redirect:/employees";
    }

    @GetMapping("/check-id")
    @ResponseBody
    public boolean checkEmployeeId(@RequestParam String empId) {
        return employeeService.existsById(empId);
    }
}