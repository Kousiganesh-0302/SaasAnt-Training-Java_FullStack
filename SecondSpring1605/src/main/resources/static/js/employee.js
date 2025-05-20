// Check if employee ID exists
document.getElementById('empId').addEventListener('blur', async e => {
    const id = e.target.value.trim();
    const status = document.getElementById('idStatus');
    const btn = document.querySelector('button[type="submit"]');
    
    if (!id) { 
        status.textContent = ''; 
        btn.disabled = false; 
        return; 
    }
    
    try {
        const response = await fetch(`/api/employees/exists/${id}`);
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        
        const data = await response.json();
        
        // Fix: Check the actual response structure
        if (data.exists || (typeof data === 'boolean' && data)) {
            status.textContent = '✖ ID taken'; 
            status.style.color = 'red';
            btn.disabled = true;
        } else {
            status.textContent = '✔ ID available'; 
            status.style.color = 'green';
            btn.disabled = false;
        }
    } catch (error) {
        console.error('Error checking ID:', error);
        status.textContent = '⚠ Check failed'; 
        status.style.color = 'orange';
        btn.disabled = false;
    }
});

// Form validation
function validateForm() {
    const name = document.getElementById('empName').value.trim();
    const id = document.getElementById('empId').value.trim();
    const pos = document.getElementById('position').value;
    const ph = document.getElementById('phone').value.trim();
    
    if (!/^[A-Za-z ]{2,}$/.test(name)) {
        alert('Enter a valid name.'); 
        return false;
    }
    if (!/^[0-9]+$/.test(id)) {
        alert('Employee ID must be numeric.'); 
        return false;
    }
    if (pos === '') {
        alert('Select a position.'); 
        return false;
    }
    if (!/^[0-9]{10}$/.test(ph)) {
        alert('Phone must be 10 digits.'); 
        return false;
    }
    return true;
}

// Form submission
async function submitEmployeeForm(event) {
    event.preventDefault();
    
    if (!validateForm()) {
        return false;
    }
    
    const employee = {
        empId: document.getElementById('empId').value.trim(),
        empName: document.getElementById('empName').value.trim(),
        position: document.getElementById('position').value,
        phone: document.getElementById('phone').value.trim()
    };
    
    try {
        const response = await fetch('/api/employees', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(employee)
        });
        
        if (response.ok) {
            // Redirect to success page or show success message
            window.location.href = '/success.html?' + 
                `name=${encodeURIComponent(employee.empName)}&` +
                `id=${encodeURIComponent(employee.empId)}&` +
                `position=${encodeURIComponent(employee.position)}&` +
                `phone=${encodeURIComponent(employee.phone)}`;
        } else {
            alert('Failed to save employee. Please try again.');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('An error occurred while saving the employee.');
    }
}