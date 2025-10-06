// Production API URL
const API_URL = "https://alzaaspr-production.up.railway.app/api";

async function signup() {
  const username = document.getElementById("signup-username").value;
  const email = document.getElementById("signup-email").value;
  const password = document.getElementById("signup-password").value;
  
  const res = await fetch(`${API_URL}/auth/register`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ username, email, password })
  });
  
  const data = await res.json();
  if (data.success) {
    localStorage.setItem("authToken", data.data.token);
    window.location.href = "dashboard.html";
  } else {
    alert("Signup failed: " + data.message);
  }
}

async function login() {
  const email = document.getElementById("login-email").value;
  const password = document.getElementById("login-password").value;
  
  const res = await fetch(`${API_URL}/auth/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ email, password })
  });
  
  const data = await res.json();
  if (data.success) {
    localStorage.setItem("authToken", data.data.token);
    window.location.href = "dashboard.html";
  } else {
    alert("Login failed: " + data.message);
  }
}