<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List, com.servletdb0905.model.Employee" %>
<%
    // Assume 'employees' attribute is set by a servlet:
    List<Employee> employees = (List<Employee>) request.getAttribute("employees");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Employee List</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-4">
    <h2>Employees</h2>
    <table class="table table-striped" id="empTable">
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
        <% for (Employee e : employees) { %>
            <tr id="row-<%= e.getEmpId() %>">
                <td class="empId"><%= e.getEmpId() %></td>
                <td class="empName"><%= e.getEmpName() %></td>
                <td class="position"><%= e.getPosition() %></td>
                <td class="phone"><%= e.getPhone() %></td>
                <td>
                    <button class="btn btn-sm btn-primary editBtn"
                            data-empid="<%= e.getEmpId() %>"
                            data-name="<%= e.getEmpName() %>"
                            data-position="<%= e.getPosition() %>"
                            data-phone="<%= e.getPhone() %>">
                        Edit
                    </button>
                    <button class="btn btn-sm btn-danger deleteBtn" data-empid="<%= e.getEmpId() %>">Delete</button>
                </td>
            </tr>
        <% } %>
        </tbody>
    </table>
</div>

<!-- Edit Modal -->
<div class="modal fade" id="editModal" tabindex="-1" aria-labelledby="editModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="editModalLabel">Edit Employee</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <form id="editForm">
          <input type="hidden" id="editEmpId" name="empId">
          <div class="mb-3">
            <label for="editName" class="form-label">Name</label>
            <input type="text" class="form-control" id="editName" name="empName" required>
          </div>
          <div class="mb-3">
            <label for="editPosition" class="form-label">Position</label>
            <select class="form-select" id="editPosition" name="position" required>
              <option value="Manager">Manager</option>
              <option value="Developer">Developer</option>
              <option value="Analyst">Analyst</option>
            </select>
          </div>
          <div class="mb-3">
            <label for="editPhone" class="form-label">Phone</label>
            <input type="text" class="form-control" id="editPhone" name="phone" required>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
        <button type="button" class="btn btn-primary" id="saveEditBtn">Save changes</button>
      </div>
    </div>
  </div>
</div>

<!-- JS: Bootstrap Bundle with Popper + jQuery -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<script>
$(document).ready(function() {
    // Edit button click -> populate & show modal
    $('.editBtn').click(function() {
        const btn = $(this);
        $('#editEmpId').val(btn.data('empid'));
        $('#editName').val(btn.data('name'));
        $('#editPosition').val(btn.data('position'));
        $('#editPhone').val(btn.data('phone'));
        var modal = new bootstrap.Modal(document.getElementById('editModal'));
        modal.show();
    });

    // Save changes -> AJAX submit
    $('#saveEditBtn').click(function() {
        const formData = $('#editForm').serialize();
        $.ajax({
            url: 'UpdateEmployeeServlet',
            method: 'POST',
            data: formData,
            success: function(response) {
                if (response.success) {
                    // Update row in table
                    const id = $('#editEmpId').val();
                    const row = $('#row-' + id);
                    row.find('.empName').text($('#editName').val());
                    row.find('.position').text($('#editPosition').val());
                    row.find('.phone').text($('#editPhone').val());
                    // Hide modal
                    bootstrap.Modal.getInstance(document.getElementById('editModal')).hide();
                } else {
                    alert('Update failed');
                }
            },
            error: function() {
                alert('Server error during update');
            }
        });
    });

    // Delete button click -> AJAX delete
    $('.deleteBtn').click(function() {
        if (!confirm('Are you sure you want to delete this employee?')) return;
        const empId = $(this).data('empid');
        $.ajax({
            url: 'DeleteEmployeeServlet',
            method: 'POST', // or GET if your servlet uses GET
            data: { empId: empId },
            success: function(response) {
                if (response.success) {
                    $('#row-' + empId).remove();
                } else {
                    alert('Delete failed');
                }
            },
            error: function() {
                alert('Server error during delete');
            }
        });
    });
});
</script>
