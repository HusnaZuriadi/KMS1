<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Create Account - Teacher</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
  <link rel="stylesheet" href="cs/signUp.css" />
</head>
<body>

 <div class="container">
    <div class="left-panel">
      <img src="images/sign up.png" alt="Kids" />
    </div>

    <div class="right-panel">
      <h2>Create Account</h2>
      <form action="CreateAccTController" method="post" onsubmit="return validateForm()">
        
        <label>Full Name</label>
        <input type="text" name="name" placeholder="Full name" required>

        <label>Email</label>
        <input type="email" name="email" placeholder="abc@gmail.com" required>

        <div class="row1">
          <div class="role">
            <label>Role</label><br>
            <select id="roleSelect" name="role" onchange="showTeacherType()" required>
              <option value="">Select Role</option>
              <option value="Teacher">Teacher</option>
              <option value="Admin">Admin</option>
            </select>
          </div>

          <div>
            <label>Phone Number</label><br>
            <input type="text" name="phone" placeholder="Phone number" required>
          </div>
        </div>

        <!-- Only show if Teacher is selected -->
        <div id="teacherType" class="hidden">
          <label>Teacher Type</label> <br>
          <select name="teacherType">
            <option value="">Select Type</option>
            <option value="FullTime">Full-Time</option>
            <option value="PartTime">Part-Time</option>
          </select>
        </div>

        <!-- Optional Admin Assignment -->
        <div id="assignAdmin" class="hidden">
          <label>Admin</label> <br>
          <select name="adminId">
            <option value="">None</option>
            <c:forEach var="admin" items="${adminList}">
              <option value="${admin.teacherId}">${admin.teacherName}</option>
            </c:forEach>
          </select>
        </div>

        <label>Password</label>
        <input type="password" id="password" name="password" placeholder="Enter password" required>

        <label>Confirm Password</label>
        <input type="password" id="confirmPassword" placeholder="Confirm password" required>

        <button type="submit">Create Account</button>

        <div class="login">
          Already have an account? <a href="#">Log in</a>
        </div>
      </form>
    </div>
  </div>
  
  <script src="js/createAcct.js"></script>

</body>
</html>