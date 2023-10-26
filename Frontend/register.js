function showToast(message, isError) {
    var toastEl = document.getElementById('myToast');
    var toast = new bootstrap.Toast(toastEl);

    toastEl.querySelector('.toast-body').textContent = message;

    if (isError) {
        toastEl.classList.add('bg-danger');
    } else {
        toastEl.classList.add('bg-success');
    }

    toast.show();

    setTimeout(() => {
        toastEl.classList.remove('bg-danger', 'bg-success');
    }, 5000);
    
}

function clearText() {
    document.getElementById('firstname').value = "";
    document.getElementById('lastname').value = "";
    document.getElementById('email').value = "";
    document.getElementById('password').value = "";
}

document.getElementById('register-form').addEventListener('submit', function(e) {
    e.preventDefault();

    var firstname = document.getElementById('firstname').value;
    var lastname = document.getElementById('lastname').value;
    var email = document.getElementById('email').value;
    var password = document.getElementById('password').value;

    fetch('http://localhost:8080/api/v1/auth/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('token')  // Include the token from localStorage
        },
        body: JSON.stringify({
            firstname: firstname,
            lastname: lastname,
            email: email,
            password: password
        }),
    })
    .then(response => {
        if(!response.ok) {
            return response.json().then(data => Promise.reject(data));
        }
        return response.json();
    })
    .then(data => {
        console.log('Success:', data);
        clearText();
        showToast("Created new user successfully", false);
    })
    .catch((error) => {
        console.error('Error:', error);
        showToast(error.message, true);
        
    });
});
