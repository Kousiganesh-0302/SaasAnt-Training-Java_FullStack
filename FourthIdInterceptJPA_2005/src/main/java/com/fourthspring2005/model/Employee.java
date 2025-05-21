package com.fourthspring2005.model;

import jakarta.persistence.*;

@Entity
@Table(name = "empformservlet")
public class Employee {
    
    @Id
    @Column(name = "emp_id", nullable = false)
    private String empId;
    
    @Column(name = "emp_name", nullable = false)
    private String empName;
    
    @Column(nullable = false)
    private String position;
    
    @Column(nullable = false)
    private String phone;

    // Getters and Setters
    public String getEmpId() { return empId; }
    public void setEmpId(String empId) { this.empId = empId; }
    public String getEmpName() { return empName; }
    public void setEmpName(String empName) { this.empName = empName; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}