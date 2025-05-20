<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Employee List</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-4">
    <h2>Employees</h2>
    
    <!-- Search Form -->
    <form class="mb-3">
        <div class="input-group">
            <input type="text" name="search" class="form-control" placeholder="Search by ID...">
            <button type="submit" class="btn btn-primary">Search</button>
        </div>
    </form>
    
    <table class="table table-striped">
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Position</th>
                <th>Phone</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${employees}" var="emp">
                <tr>
                    <td>${emp.empId}</td>
                    <td>${emp.empName}</td>
                    <td>${emp.position}</td>
                    <td>${emp.phone}</td>
                    <td>
                        <a href="/employees/edit/${emp.empId}" class="btn btn-sm btn-primary">Edit</a>
                        <a href="/employees/delete/${emp.empId}" class="btn btn-sm btn-danger" 
                           onclick="return confirm('Are you sure?')">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>