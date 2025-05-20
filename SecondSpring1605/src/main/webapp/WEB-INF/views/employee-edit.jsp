<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Employee</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-4">
    <h2>Edit Employee</h2>
    
    <form action="/employees/update" method="post">
        <input type="hidden" name="empId" value="${employee.empId}">
        
        <div class="mb-3">
            <label class="form-label">Name</label>
            <input type="text" name="empName" class="form-control" value="${employee.empName}" required>
        </div>
        
        <div class="mb-3">
            <label class="form-label">Position</label>
            <select name="position" class="form-control" required>
                <option value="Manager" ${employee.position == 'Manager' ? 'selected' : ''}>Manager</option>
                <option value="Developer" ${employee.position == 'Developer' ? 'selected' : ''}>Developer</option>
                <option value="Analyst" ${employee.position == 'Analyst' ? 'selected' : ''}>Analyst</option>
            </select>
        </div>
        
        <div class="mb-3">
            <label class="form-label">Phone</label>
            <input type="text" name="phone" class="form-control" value="${employee.phone}" required>
        </div>
        
        <button type="submit" class="btn btn-primary">Save Changes</button>
        <a href="/employees/list" class="btn btn-secondary">Cancel</a>
    </form>
</div>
</body>
</html>