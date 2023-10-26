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

document.querySelector('form').addEventListener('submit', function(e) {
    e.preventDefault();
    var email = document.getElementById('email').value;
    var password = document.getElementById('password').value;

    fetch('http://localhost:8080/api/v1/auth/authenticate', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            email: email,
            password: password
        }),
    })
    .then(response => {
        if (!response.ok) {
            return response.json().then(errorData => {
                return Promise.reject(errorData);
            });
        }
        return response.json();
    })
    .then(data => {
        console.log('Success:', data);
        // Store the token in localStorage
        localStorage.setItem('token', data.token);
        // Redirect to the register page
        window.location.href = data.redirectUrl;
    })    
    .catch((error) => {
        console.error('Error:', error);
        showToast(error.message, true);
    });
});
