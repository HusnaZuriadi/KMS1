<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="kms.model.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>View Account</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
<link rel="stylesheet" href="css/viewAccount.css">
</head>

<body>
	<%
	Object user = session.getAttribute("user");
	String role = (String) session.getAttribute("role");
	
	String name = "", email = "", phone = "", roleDisplay = "",teacherType = "";
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
        
    } else if ("teacher".equalsIgnoreCase(role) && user instanceof teacher) {
        teacher t = (teacher) user;
        id = t.getTeacherId();
        name = t.getTeacherName();
        email = t.getTeacherEmail();
        phone = t.getTeacherPhone();
        roleDisplay = "Teacher";
        teacherType = t.getTeacherType();
        
        if ("FullTime".equalsIgnoreCase(teacherType) && user instanceof fullTime) {
            salary = ((fullTime) user).getSalary();
        } else if ("PartTime".equalsIgnoreCase(teacherType) && user instanceof partTime) {
            contract = ((partTime) user).getContract();
        }
        
    } else if ("admin".equalsIgnoreCase(role) && user instanceof teacher) {
        teacher a = (teacher) user;
        id = a.getTeacherId();
        name = a.getTeacherName();
        email = a.getTeacherEmail();
        phone = a.getTeacherPhone();
        roleDisplay = "Administrator";
    }
	
	%>
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
			<h1>My Account</h1>
			<button class="edit-btn">Edit</button>
			<div class="profile-info">
				<div class="profile-img">
					<img src="PhotoServlet?role=<%= role %>&type=photo&id=<%= id %>" alt="User Photo">
				</div>
				<div class="details">
					<div class="column">
						<p>
							<strong>Name</strong><br><%=name %>
						</p>
						
						<p>
							<strong>E-mail</strong><br><%=email %>
						</p>
						<p>
							<strong>Phone Number</strong><br><%=phone %>
						</p>
						
						<p>
							<strong>Role</strong><br><%=role %>
						</p>
					</div>
					<div class="column">
						
						<% if ("FullTime".equalsIgnoreCase(teacherType)) { %>
            <p><strong>Salary:</strong><br>RM <%= salary %></p>
          <% } else if ("PartTime".equalsIgnoreCase(teacherType)) { %>
            <p><strong>Contract (Hours):</strong><br><%= contract %></p>
          <% } %>
					</div>
				</div>
			</div>
		</div>
	</main>
	 <script>
    function toggleSidebar() {
      const sidebar = document.getElementById("sidebar");
      const dashboard = document.getElementById("dashboard");
      sidebar.classList.toggle("show");
      dashboard.classList.toggle("shifted");
    }
  </script>
</body>
</html>
