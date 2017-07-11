<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

	<%@include file="parts/header.jsp"%>
	
	<div class="container">
		<form class="form-register" action="./registration" method="POST">

			<h2 class="form-signin-heading">Please register</h2>
			
			<label for="name">Name</label> 
			<input type="text" id="name" name="name" class="form-control" placeholder="Name" required autofocus>
				
			 <label for="surname">Surname</label>
				<input type="text" id="surname" name="surname" class="form-control" placeholder="Surname" required>
				
			<label for="email">E-mail</label>
				<input type="text" id="email" name="email" class="form-control" placeholder="Email address" required>

			<label for="password">Password</label> <input
				type="password" id="password"  name="password" class="form-control" placeholder="Password" required>
				
			<br/>
			<button class="btn btn-success">Register</button>

		</form>
	</div>
</body>
</html>