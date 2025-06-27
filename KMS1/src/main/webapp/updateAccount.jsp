<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page import="kms.model.*" %>
<%
    Object user = session.getAttribute("user");
    String role = (String) session.getAttribute("role");

    String name = "", email = "", phone = "", roleDisplay = "", teacherType = "";
    Double salary = null;
    Integer contract = null;
    int id = 0;

    if ("parent".equalsIgnoreCase(role) && user instanceof parent) {
        parent p = (parent) user;
        id = p.getParentId();
        name = p.getParentName();
        email = p.getParentEmail();
        phone = p.getParentPhone();
        roleDisplay = "Parent";
    }  else if ((role.equalsIgnoreCase("teacher") || role.equalsIgnoreCase("admin")) && user instanceof teacher) {
        teacher t = (teacher) user;
        id = t.getTeacherId();
        name = t.getTeacherName();
        email = t.getTeacherEmail();
        phone = t.getTeacherPhone();
        teacherType = t.getTeacherType();
        roleDisplay = role.equals("admin") ? "Administrator" : "Teacher";

        if ("FullTime".equalsIgnoreCase(teacherType) && t instanceof fullTime) {
            salary = ((fullTime) t).getSalary();
        } else if ("PartTime".equalsIgnoreCase(teacherType) && t instanceof partTime) {
            contract = ((partTime) t).getContract();
        }
    }
%>

<!DOCTYPE html>
<html>
<head>
 <meta charset="UTF-8">
  <title>Update Account</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
  <link rel="stylesheet" href="css/updateAccount.css">
</head>
<body>

<header>
		<button class="navSidebar" onclick="toggleSidebar()">
			<i class="fa-solid fa-bars"></i>
		</button>
		<div class="logo">
			<img src="images/LOGO-AL-KAUTHAR-EDUQIDS.png"
				alt="ALKAUTHAR EDUQIDS Logo">
		</div>
	</header>

	<nav class="sidebar" id="sidebar">
		<div class="profile">
			<a href="viewAccount.jsp">  <img src="PhotoServlet?role=<%= role %>&type=photo&id=<%= 
        role.equals("parent") ? ((parent)user).getParentId() : ((teacher)user).getTeacherId() %>" ></a>
			<h3><%=name%></h3>

			<p><%= roleDisplay %></p>
		</div>
		<a href="#">Dashboard</a> 
		<a href="ListStudentController">Student Registration</a> 
		<a href="#">Teachers</a> 
		<a href="#">Accounts</a> 
		<a href="#">Logout</a>
	</nav>
	
	<main class="account-container">
  <div class="account-card">
    <h1>Edit My Account</h1>
    
<form action="UpdateAccountController" method="post" enctype="multipart/form-data">
      <label>Name</label>
      <input type="text" name="name" value="<%= name %>" required>

      <label>Email</label>
      <input type="email" name="email" value="<%= email %>" required>

      <label>Phone Number</label>
      <input type="text" name="phone" value="<%= phone %>" required>
      
      <label>Upload Photo</label>
<input type="file" name="photo" accept="image/*" onchange="previewImage(event)">
<img id="preview" src="#" alt="Image preview" style="height: 200px; width:200px; display: none; margin-top: 10px;">

 <!-- Only show these fields if user is FullTime or PartTime teacher -->
            <% if ("FullTime".equalsIgnoreCase(teacherType)) { %>
                <label>Salary (RM)</label>
                <input type="number" step="0.01" name="salary" value="<%= salary %>" required>
            <% } else if ("PartTime".equalsIgnoreCase(teacherType)) { %>
                <label>Contract Hours</label>
                <input type="number" name="contract" value="<%= contract %>" required>
            <% } %>

            <input type="hidden" name="role" value="<%= role %>">
            <input type="hidden" name="id" value="<%= id %>">

      <button type="submit">Update Info</button>
    </form>

    <hr>

    <h2>Change Password</h2>
    <form action="ChangePasswordController" method="post">
      <label>Current Password</label>
      <input type="password" name="currentPassword" required>

      <label>New Password</label>
      <input type="password" name="newPassword" required>

      <label>Confirm New Password</label>
      <input type="password" name="confirmPassword" required>

      <button type="submit">Change Password</button>
    </form>
    <p><a href="forgotPassword.jsp">Forgot password?</a></p>

    <hr>

    <h2>Delete My Account</h2>
    <form action="<%= role.equals("admin") ? "DeleteAccountController" : "DeleteRequestController" %>" method="post"
          onsubmit="return confirm('Are you sure you want to delete your account?');">
      <input type="hidden" name="id" value="<%= id %>">
      <input type="hidden" name="role" value="<%= role %>">
      <button type="submit"><%= role.equals("admin") ? "Delete Now" : "Request Deletion" %></button>
      <c:if test="${not role.equals('admin')}">
        <p style="font-size: 0.9em; color: gray;">(Admin approval required)</p>
      </c:if>
    </form>

  </div>
</main>

<script>
  function toggleSidebar() {
    document.getElementById("sidebar").classList.toggle("show");
  }
  
  function previewImage(event) {
	    const reader = new FileReader();
	    reader.onload = function () {
	      const preview = document.getElementById('preview');
	      preview.src = reader.result;
	      preview.style.display = 'block';
	    };
	    reader.readAsDataURL(event.target.files[0]);
	  }
</script>

</body>
</html>
