document.getElementById('loginForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    // Dummy login validation
    if (username === "admin" && password === "admin") {
        window.location.href = "/admin/dashboard";
    } else {
        document.getElementById('errorMessage').innerText = "Invalid credentials!";
    }
});
