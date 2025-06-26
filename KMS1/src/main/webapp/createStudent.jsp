<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="kms.model.*" %>
<%
  Object user = session.getAttribute("user");
  String parentName = "";

  if (user instanceof parent) {
      parent p = (parent) user;
      parentName = p.getParentName();
  }
%>
<!DOCTYPE html>
<html lang="ms">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Student Registration</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
  <link rel="stylesheet" href="css/StudentP.css">
</head>
<body>

  <header>
    <button class="navSidebar" onclick="toggleSidebar()"><i class="fa-solid fa-bars"></i></button>
    <div class="logo">
      <img src="images/LOGO-AL-KAUTHAR-EDUQIDS.png" alt="ALKAUTHAR EDUQIDS Logo">
    </div>
  </header>

  <nav class="sidebar" id="sidebar">
    <div class="profile">
      <img src="images/admin.jpg" alt="Admin Profile Photo">
      <h3><%= parentName %></h3>

      <p>Parent</p>
    </div>
    <a href="#">Dashboard</a>
    <a href="listStudent.jsp">Student Registration</a>
    <a href="#">Teachers</a>
    <a href="#">Accounts</a>
    <a href="#">Logout</a>
  </nav>

<!-- Main Container -->
<div class="container">
  <h2>Register New Student</h2>
  <form action="createStudentController" method="post" enctype="multipart/form-data">
    <label for="name">Full Name:</label>
    <input type="text" id="name" name="name" required>

    <label for="age">Age:</label>
    <input type="number" id="age" name="age" required min="1">

    <label for="gender">Gender:</label>
    <select id="gender" name="gender" required>
      <option value="">Select Gender</option>
      <option value="Male">Male</option>
      <option value="Female">Female</option>
    </select>

    <label for="address">Address:</label>
    <textarea id="address" name="address" rows="3" required></textarea>

    <label for="dob">Date of Birth:</label>
    <input type="date" id="dob" name="dob" required>

    <label for="photo">Student Photo (JPEG/PNG):</label>
    <input type="file" id="photo" name="photo" accept="image/*" required>
    
    <div class="photo-container">
  <img id="photoPreview" alt="Photo Preview" />
</div>


    <label for="birthCert">Birth Certificate (PDF or Image):</label>
    <input type="file" id="birthCert" name="birthCert" accept=".pdf,image/*" required>
    
    <label>Parent Name</label>
      <input type="text" value="<%= parentName %>" readonly>

    <button class="btn" type="submit">Submit</button>
  </form>
</div>

<script>
  function toggleSidebar() {
    document.getElementById("sidebar").classList.toggle("show");
  }

  document.getElementById("photo").addEventListener("change", function(event) {
	  const preview = document.getElementById("photoPreview");
	  const container = document.querySelector(".photo-container");
	  const file = event.target.files[0];

	  if (file && file.type.startsWith("image/")) {
	    const reader = new FileReader();
	    reader.onload = function(e) {
	      preview.src = e.target.result;
	      container.style.display = "block";
	    };
	    reader.readAsDataURL(file);
	  } else {
	    container.style.display = "none";
	  }
	});

</script>

</body>
</html>
