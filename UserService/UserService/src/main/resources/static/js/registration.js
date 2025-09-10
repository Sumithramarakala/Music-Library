function registerUser(event) {
    event.preventDefault();

    const userData = {
        name: document.getElementById("name").value,
        email: document.getElementById("email").value,
        phone: document.getElementById("phone").value,
		username: document.getElementById("username").value,
        password: document.getElementById("password").value
    };

    fetch("/api/users/register", {  // Correct endpoint
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(userData)
    })
    .then(response => {
        if (!response.ok) throw new Error("Registration failed!");
        return response.json();
    })
    .then(data => {
        document.getElementById("successMessage").innerText = "Registration successful! Redirecting to login...";
        document.getElementById("errorMessage").innerText = "";
        setTimeout(() => {
            window.location.href = "/user/login"; // Redirect to login page
        }, 2000);
    })
    .catch(error => {
        document.getElementById("errorMessage").innerText = error.message;
        document.getElementById("successMessage").innerText = "";
    });
}
