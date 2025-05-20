<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Success</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        .success-message {
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
            background: white;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
    </style>
</head>
<body>
<div class="container">
    <div class="success-message">
        <h2 class="text-success">Submission Successful!</h2>
        <table class="table table-bordered">
            <tr>
                <th>Field</th>
                <th>Value</th>
            </tr>
            <tr>
                <td>Employee Name</td>
                <td>${employee.empName}</td>
            </tr>
            <tr>
                <td>Employee ID</td>
                <td>${employee.empId}</td>
            </tr>
            <tr>
                <td>Position</td>
                <td>${employee.position}</td>
            </tr>
            <tr>
                <td>Phone Number</td>
                <td>${employee.phone}</td>
            </tr>
        </table>
        <a href="/employees/form" class="btn btn-primary">Add Another Employee</a>
        <a href="/employees/list" class="btn btn-secondary">View All Employees</a>
    </div>
</div>
</body>
</html>