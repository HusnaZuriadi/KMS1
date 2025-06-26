<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
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
  <title>Update Student</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
  <link rel="stylesheet" href="css/updateStudent.css">
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
  <a href="ListStudentController">Student Registration</a>
  <a href="#">Teachers</a>
  <a href="#">Accounts</a>
  <a href="logout.jsp">Logout</a>
</nav>

<div class="update-container">
    <h2>Update Student Info</h2>
    <form action="updateStudentController" method="post" enctype="multipart/form-data">
        <input type="hidden" name="studId" value="${student.studId}" />

        <div class="form-group">
            <label>Full Name</label>
            <input type="text" name="name" value="${student.studName}" required>
        </div>

        <div class="form-group">
            <label>Gender</label>
            <select name="gender" required>
               <option value="Male" <c:if test="${student.studGender == 'Male'}">selected</c:if>>Male</option>
<option value="Female" <c:if test="${student.studGender == 'Female'}">selected</c:if>>Female</option>

            </select>
        </div>

        <div class="form-group">
            <label>Age</label>
            <input type="number" name="age" value="${student.studAge}" min="1" max="20" required>
        </div>

        <div class="form-group">
            <label>Date of Birth</label>
            <input type="date" name="dob" value="${student.studBirthDate}" required>
        </div>

        <div class="form-group">
            <label>Address</label>
            <textarea name="address" rows="3">${student.studAddress}</textarea>
        </div>

        <div class="form-group">
            <label>Student Photo</label>
            <c:if test="${not empty student.studPhoto}">
                <img id="photoPreview" src="PhotoServlet?role=student&id=${student.studId}&type=photo" class="preview-img" alt="Student Photo">
            </c:if>
            <!-- Input baru -->
    <input type="file" name="photo" id="photoInput" accept="image/*">
        </div>

        <div class="form-group">
            <label>Birth Certificate (PDF or Image)</label>
            <c:if test="${not empty student.studBirthCert}">
                <a href="PhotoServlet?role=student&id=${student.studId}&type=cert" target="_blank">View Current Certificate</a>
            </c:if>
            <input type="file" name="birthCert" accept="application/pdf,image/*">
        </div>

        <div class="form-actions">
            <button type="submit">Update Student</button>
            <a href="ListStudentController">Cancel</a>
        </div>
    </form>
</div>

<script>
function toggleSidebar() {
  document.getElementById("sidebar").classList.toggle("show");
  
}
document.addEventListener("DOMContentLoaded", function () {
	  const photoInput = document.getElementById("photoInput");
	  const photoPreview = document.getElementById("photoPreview");

	  if (photoInput && photoPreview) {
	    photoInput.addEventListener("change", function () {
	      const file = this.files[0];
	      if (file && file.type.startsWith("image/")) {
	        const reader = new FileReader();
	        reader.onload = function (e) {
	          photoPreview.src = e.target.result;  // ✅ override gambar lama
	        };
	        reader.readAsDataURL(file);
	      }
	    });
	  }
	});
</script>

</body>
</html>
