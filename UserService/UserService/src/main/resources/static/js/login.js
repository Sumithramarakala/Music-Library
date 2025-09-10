function loginUser(event) {
    event.preventDefault();

    const loginData = {
        username: document.getElementById("username").value,
        password: document.getElementById("password").value
    };

    fetch("/api/users/login", {  // Correct endpoint
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(loginData)
    })
    .then(response => {
        if (!response.ok) throw new Error("Invalid username or password!");
        return response.json();
    })
    .then(data => {
        // Save user info in sessionStorage
        sessionStorage.setItem("user", JSON.stringify(data.user));
        window.location.href = "/user/dashboard"; // Redirect to dashboard
    })
    .catch(error => {
        document.getElementById("errorMessage").innerText = error.message;
    });
}
