<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Employee Form</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        .form-container {
            width: 100%;
            max-width: 400px;
            background: rgb(255, 128, 255);
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgb(255, 0, 255);
            margin: 20px auto;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="text-end mt-3">
        <a href="/employees/list" class="btn btn-sm btn-light">View All Employees</a>
    </div>
    
    <div class="form-container">
        <form action="/employees/save" method="post">
            <h2>Employee Details</h2>
            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>
            
            <div class="mb-3">
                <input type="text" name="empName" class="form-control" placeholder="Employee Name" required>
            </div>
            <div class="mb-3">
                <input type="text" name="empId" class="form-control" placeholder="Employee ID" required>
            </div>
            <div class="mb-3">
                <select name="position" class="form-control" required>
                    <option value="">-- Select Position --</option>
                    <option>Manager</option>
                    <option>Developer</option>
                    <option>Analyst</option>
                </select>
            </div>
            <div class="mb-3">
                <input type="text" name="phone" class="form-control" placeholder="Phone Number" required>
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
    </div>
</div>
</body>
</html>