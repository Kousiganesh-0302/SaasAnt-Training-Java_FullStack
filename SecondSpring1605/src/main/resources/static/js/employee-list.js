$(document).ready(function() {
    loadEmployees();
    
    // Search functionality
    $('#searchBox').on('input', function() {
        const searchText = $(this).val().toLowerCase();
        $('#employeeTableBody tr').each(function() {
            const empId = $(this).find('.empId').text().toLowerCase();
            $(this).toggle(empId.includes(searchText));
        });
    });

    function loadEmployees() {
        $.get('/api/employees', function(employees) {
            const tableBody = $('#employeeTableBody');
            tableBody.empty();
            
            if (employees && employees.length > 0) {
                employees.forEach(emp => {
                    tableBody.append(`
                        <tr id="row-${emp.empId}">
                            <td class="empId">${emp.empId}</td>
                            <td class="empName">${emp.empName}</td>
                            <td class="position">${emp.position}</td>
                            <td class="phone">${emp.phone}</td>
                            <td>
                                <button class="btn btn-sm btn-primary editBtn"
                                        data-empid="${emp.empId}"
                                        data-name="${emp.empName}"
                                        data-position="${emp.position}"
                                        data-phone="${emp.phone}">
                                    Edit
                                </button>
                                <button class="btn btn-sm btn-danger deleteBtn" 
                                        data-empid="${emp.empId}">
                                    Delete
                                </button>
                            </td>
                        </tr>
                    `);
                });
                
                // Attach event handlers
                $('.editBtn').click(function() {
                    $('#editEmpId').val($(this).data('empid'));
                    $('#editName').val($(this).data('name'));
                    $('#editPosition').val($(this).data('position'));
                    $('#editPhone').val($(this).data('phone'));
                    $('#editModal').modal('show');
                });
                
                $('.deleteBtn').click(function() {
                    if (confirm('Delete this employee?')) {
                        const empId = $(this).data('empid');
                        $.ajax({
                            url: `/api/employees/${empId}`,
                            type: 'DELETE',
                            success: function(response) {
                                $(`#row-${empId}`).remove();
                                alert(response); // Shows "Employee deleted" message
                            },
                            error: function() {
                                alert('Delete failed');
                            }
                        });
                    }
                });
            } else {
                tableBody.append('<tr><td colspan="5">No employees found</td></tr>');
            }
        }).fail(function() {
            alert('Failed to load employees');
        });
    }
    
    $('#saveEditBtn').click(function() {
        const employee = {
            empId: $('#editEmpId').val(),
            empName: $('#editName').val(),
            position: $('#editPosition').val(),
            phone: $('#editPhone').val()
        };
        
        $.ajax({
            url: `/api/employees/${employee.empId}`,
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(employee),
            success: function(response) {
                const row = $(`#row-${employee.empId}`);
                row.find('.empName').text(employee.empName);
                row.find('.position').text(employee.position);
                row.find('.phone').text(employee.phone);
                $('#editModal').modal('hide');
                alert(response); // Shows "Employee updated" message
            },
            error: function() {
                alert('Update failed');
            }
        });
    });
});