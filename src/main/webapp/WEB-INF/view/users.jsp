<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<link href="<c:url value="/resources/css/header.css" />"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="<c:url value="/resources/css/grid.css" />" rel="stylesheet">

</head>
<body>
	<c:import url="parts/header.jsp" />

	<div class="page-header">
		<center>
			<h1>List Of Users</h1>
		</center>
	</div>
	<div class="container">
		<table class="table table-striped">
			<thead>
				<tr>
					<th>#</th>
					<th>Name</th>
					<th>Surname</th>
					<th>Role</th>
					<th>Login</th>
					<th>Update User</th>
				</tr>
			</thead>
			<tbody>
				<c:set var="count" value="0" scope="page" />
				<c:forEach var="person" items="${personList}">
					<c:set var="count" value="${count + 1}" scope="page" />
					<tr>
						<td><c:out value="${count}" /></td>
						<td><c:out value="${person.name}" /></td>
						<td><c:out value="${person.surname}" /></td>
						<td><c:out value="${person.role}" /></td>
						<td><c:out value="${person.login.email}" /></td>
						<td><form action="./user" method="POST">
								<button class="btn btn-warning" type="submit">Update</button>
								<input type="hidden" name="id" value="${person.login.id}" /> <input
									type="hidden" name="name" value="${person.name}" /> <input
									type="hidden" name="surname" value="${person.surname}" /> <input
									type="hidden" name="role" value="${person.role}" /> <input
									type="hidden" name="email" value="${person.login.email}" />
							</form></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

</body>
</html>