<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, kms.model.student" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Student Registration</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
  <link rel="stylesheet" href="listStudent.css">  <title>List of Students</title>
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
      <h3><%= session.getAttribute("parentName") %></h3>

      <p>Parent</p>
    </div>
    <a href="#">Dashboard</a>
    <a href="listStudent.jsp">Student Registration</a>
    <a href="#">Teachers</a>
    <a href="#">Accounts</a>
    <a href="#">Logout</a>
  </nav>

<div>
<h2>List of Students</h2>
  <table border="1" class="table table-striped">
        <thead class="table-dark">
            <tr>
                <th>Student ID</th>
                <th>Name</th>
                <th>Gender</th>
                <th>Age</th>
                <th>Birth Date</th>
                <th>Address</th>
                <th colspan="3">Action</th>
            </tr>
        </thead>
        <c:if test="${empty students}">
        <tr>
          <td colspan="9">No students found for this parent.</td>
        </tr>
      </c:if>
      
	  <tbody>
      <c:forEach var="stud" items="${students}" varStatus="students">
        <tr>
          <td><c:out value="${stud.studId}" /></td>
          <td><c:out value="${stud.studName}" /></td>
          <td><c:out value="${stud.studGender}" /></td>
          <td><c:out value="${stud.studAge}" /></td>
          <td><fmt:formatDate value="${stud.studBirthDate}" pattern="yyyy-MM-dd" /></td>
          <td><c:out value="${stud.studAddress}" /></td>
          <td><a class="btn btn-info" href="viewStudentController?studId=${stud.studId}">View</a></td>
          <td><a class="btn btn-warning" href="updateStudentController?studId=${stud.studId}">Update</a></td>
          <td><button class="btn btn-danger"id="<c:out value="${stud.studId}"/>" onclick="confirmDelete(${stud.studId})">Delete</button></td>
        </tr>
      </c:forEach>
        </tbody>
    </table>
</div>

<script>
  function toggleSidebar() {
    document.getElementById("sidebar").classList.toggle("show");
  }
  
  function confirmDelete(studId) {
	  console.log(studId);
	  var r = confirm("Are you sure you want to delete?");
	  if (r == true) {				 		  
		  location.href = 'deleteStudentController?studId=' + studId;
		  alert("Student successfully deleted");			
	  } else {				  
	      return false;	
      }
  }
  </script>
</body>
</html>
