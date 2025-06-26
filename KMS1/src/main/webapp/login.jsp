<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Login - Kindergarten System</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
<link rel="stylesheet" href="css/login.css" />
</head>
<body>

	<div class="container">
		<div class="left-panel">
			<img src="images/sign up.png" alt="Login Kids" />
		</div>

		<div class="right-panel">
			<h2>Login Account</h2>

			<!-- Show message from server if available -->
			<%
				String msg = request.getParameter("msg");
				if (msg != null && msg.equals("fail")) {
			%>
				<p style="color: red;">Invalid email or password.</p>
			<% } else if (msg != null && msg.equals("success")) { %>
				<p style="color: green;">Account created successfully. Please login.</p>
			<% } %>

			<form action="loginController" method="post">
				<div class="roles">
					<input type="radio" name="role" value="Admin" required>Admin 
					<input type="radio" name="role" value="Parent">Parents 
					<input type="radio" name="role" value="Teacher">Teacher
				</div>

				<label>Email</label> 
				<input type="email" name="email" placeholder="Enter your email" required> 

				<label>Password</label>
				<input type="password" name="password" placeholder="Enter your password" required>

				<button type="submit">Log In</button>

				<div class="register-link">
					Don't have an account? <a href="createAcc.html">Sign Up</a> now!
				</div>
			</form>
		</div>
	</div>

</body>
</html>
