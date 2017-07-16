<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- Bootstrap core CSS -->
<link href="<c:url value="/resources/css/bootstrap.min.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/registration.css" />"
	rel="stylesheet">
</head>
<body>
	<c:import url="parts/header.jsp" />

	<div class="container">
		<form class="form-register" action="./updatePerson" method="POST">
		
			<h2 class="form-signin-heading">Update person</h2>

			<label for="name">Name</label> <input type="text" id="name"
				name="name" class="form-control" value ="${person.name}" placeholder="Name" required
				autofocus> 
				
			<label for="surname">Surname</label> <input
				type="text" id="surname" name="surname" class="form-control" value ="${person.surname}"
				placeholder="Surname" required> 
				
			<label for="email">E-mail</label>
			<input type="text" id="email" name="email" class="form-control" value ="${person.login.email}"
				placeholder="Email address" required> 
				
			<label for="email">Role</label>
			<input type="text" id="email" name="role" class="form-control" value ="${person.role}"
				placeholder="Email address" required> <br/>
			
			<input type="hidden" name="id" value="${person.login.id}" />	
			<button class="btn btn-success">Update</button>

		</form>
	</div>
</body>
</html>