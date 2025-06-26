<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="kms.model.parent" %>
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
  <title>View Student</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
  <link rel="stylesheet" href="css/viewStudent.css">
 
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
    <a href="#">Logout</a>
  </nav>

<div class="container">
  <h2>Student Details</h2>
  
  <div style="text-align:center;">
  <label>Student Photo</label><br/>

  <c:choose>
    <c:when test="${not empty student.studPhoto}">
      <img src="PhotoServlet?role=student&type=photo&id=${student.studId}" width="300" height="300">


    </c:when>
    <c:otherwise>
      <div class="value">No photo uploaded</div>
    </c:otherwise>
  </c:choose>
</div>


  <label>Full Name</label>
  <div class="value"><c:out value="${student.studName}"/> </div>

  <label>Gender</label>
  <div class="value"><c:out value="${student.studGender}"/> </div>

  <label>Age</label>
  <div class="value"><c:out value="${student.studAge}"/> </div>

  <label>Date of Birth</label>
  <div class="value"><fmt:formatDate value="${student.studBirthDate}" pattern="dd-MM-yyyy"/></div>

  <label>Address</label>
  <div class="value"><c:out value="${student.studAddress}"/></div>

  <label>Birth Certificate</label>
  <c:choose>
    <c:when test="${not empty student.studBirthCert}">
      <div class="value">
        <a href="PhotoServlet?id=${student.studId}&type=cert&role=student" target="_blank">View Birth Certificate</a>
      </div>
    </c:when>
    <c:otherwise>
      <div class="value">No certificate uploaded</div>
    </c:otherwise>
  </c:choose>

  <div class="back-btn">
    <a href="ListStudentController">← Back to Student List</a>
  </div>
</div>
<script >function toggleSidebar() {
    document.getElementById("sidebar").classList.toggle("show");
}</script>
</body>
</html>
