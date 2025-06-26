<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, kms.model.student"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin List</title>
</head>
<body>
	<h2>List of Teachers</h2>

	<table border="1" cellpadding="8" cellspacing="0">
		<thead>
			<tr>
				<th>ID</th>
				<th>Name</th>
				<th>Email</th>
				<th>Phone</th>
				<th>Role</th>
				<th>Type</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="teacher" items="${teacherList}">
				<tr>
					<td>${teacher.teacherId}</td>
					<td>${teacher.teacherName}</td>
					<td>${teacher.teacherEmail}</td>
					<td>${teacher.teacherPhone}</td>
					<td>${teacher.teacherRole}</td>
					<td>${teacher.teacherType}</td>
				</tr>
			</c:forEach>

			<c:if test="${empty teacherList}">
				<tr>
					<td colspan="6" style="text-align: center;">No teachers found.</td>
				</tr>
			</c:if>
		</tbody>
	</table>

	<br />

</body>
</html>
