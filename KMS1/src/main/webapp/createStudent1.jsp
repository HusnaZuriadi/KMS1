<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Register Student</title>
  <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Poppins:wght@200;300;400;500;600;700;800&display=swap">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
  <style>
    @charset "UTF-8";
    html {
      scroll-behavior: smooth;
    }
    *, *::before, *::after {
      padding: 0;
      margin: 0;
      box-sizing: border-box;
    }
    body, input {
      font-family: "Poppins", sans-serif;
      padding-top: 80px;
    }

    header {
      background-color: #88c34e;
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 10px 20px;
      position: fixed;
      top: 0;
      width: 100%;
      z-index: 2;
    }

    .logo {
      display: flex;
      align-items: center;
      justify-content: center;
    }

    .logo img {
      height: 40px;
      width: auto;
    }

    .navSidebar {
      font-size: 24px;
      cursor: pointer;
      background: none;
      border: none;
      color: #fff;
    }

    .sidebar {
      position: fixed;
      top: 0;
      left: -250px;
      height: 100%;
      width: 250px;
      background-color: #88c34e;
      display: flex;
      flex-direction: column;
      padding-top: 80px;
      transition: left 0.3s ease;
      z-index: 1;
    }

    .sidebar.show {
      left: 0;
    }

    .sidebar img {
      border-radius: 50%;
      width: 80px;
      height: 80px;
    }

    .profile {
      margin: 10px;
      text-align: center;
    }

    .sidebar h3 {
      margin: 10px 0 5px;
      font-size: 16px;
    }

    .sidebar p {
      margin: 0;
      color: black;
      font-size: 14px;
    }

    .sidebar a {
      color: white;
      padding: 15px 20px;
      text-decoration: none;
      display: block;
    }

    .sidebar a:hover {
      background-color: #578a2a;
    }

    .container {
      max-width: 800px;
      margin: auto;
      background: #fff;
      padding: 25px;
      border-radius: 12px;
      box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
    }

    h2 {
      text-align: center;
      color: #2c3e50;
      margin-bottom: 20px;
    }

    label {
      display: block;
      margin-top: 10px;
      font-weight: bold;
    }

    input, select, textarea {
      width: 100%;
      padding: 8px;
      margin-top: 4px;
      border: 1px solid #ccc;
      border-radius: 5px;
      box-sizing: border-box;
    }

    .btn {
      background-color: #3498db;
      color: white;
      padding: 10px 20px;
      border: none;
      border-radius: 6px;
      cursor: pointer;
      font-size: 14px;
      margin-top: 20px;
    }

    .btn:hover {
      background-color: #2980b9;
    }

    #photoPreview {
      margin-top: 10px;
      width: 100px;
      height: auto;
      display: none;
      border: 1px solid #ccc;
      border-radius: 6px;
    }
  </style>
</head>
<body>

<!-- HEADER -->
<header>
  <button class="navSidebar" onclick="toggleSidebar()"><i class="fa-solid fa-bars"></i></button>
  <div class="logo">
    <img src="images/LOGO-AL-KAUTHAR-EDUQIDS.png" alt="ALKAUTHAR EDUQIDS Logo">
  </div>
</header>

<!-- SIDEBAR -->
<nav class="sidebar" id="sidebar">
  <div class="profile">
    <img src="images/admin.jpg" alt="Admin Profile Photo">
    <h3><%= session.getAttribute("parentName") != null ? session.getAttribute("parentName") : "Parent" %></h3>
    <p>Parent</p>
  </div>
  <a href="#">Dashboard</a>
  <a href="createStudent.jsp">Student Registration</a>
  <a href="#">Teachers</a>
  <a href="#">Accounts</a>
  <a href="logout.jsp">Logout</a>
</nav>

<!-- FORM -->
<div class="container">
  <h2>Register New Student</h2>
  <form action="createStudentController" method="post" enctype="multipart/form-data">
    <label for="name">Full Name:</label>
    <input type="text" id="name" name="name" required>

    <label for="age">Age:</label>
    <input type="number" id="age" name="age" required min="1">

    <label for="gender">Gender:</label>
    <select id="gender" name="gender" required>
      <option value="">--Select Gender--</option>
      <option value="Male">Male</option>
      <option value="Female">Female</option>
    </select>

    <label for="address">Address:</label>
    <textarea id="address" name="address" rows="3" required></textarea>

    <label for="dob">Date of Birth:</label>
    <input type="date" id="dob" name="dob" required>

    <label for="photo">Student Photo (JPEG/PNG):</label>
    <input type="file" id="photo" name="photo" accept="image/*" required>
    <img id="photoPreview" alt="Photo Preview" />

    <label for="birthCert">Birth Certificate (PDF or Image):</label>
    <input type="file" id="birthCert" name="birthCert" accept=".pdf,image/*" required>

    <button class="btn" type="submit">Submit</button>
  </form>
</div>

<!-- SCRIPT -->
<script>
  function toggleSidebar() {
    const sidebar = document.getElementById("sidebar");
    sidebar.classList.toggle("show");
  }

  document.getElementById("photo").addEventListener("change", function(event) {
    const preview = document.getElementById("photoPreview");
    const file = event.target.files[0];
    if (file && file.type.startsWith("image/")) {
      const reader = new FileReader();
      reader.onload = function(e) {
        preview.src = e.target.result;
        preview.style.display = "block";
      };
      reader.readAsDataURL(file);
    } else {
      preview.style.display = "none";
    }
  });
</script>

</body>
</html>
