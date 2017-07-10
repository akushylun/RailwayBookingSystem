<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
			<label class="col-sm-5 control-label" for="name">Name</label> <input
				type="text" id="name" name="name">
				
			 <label class="col-sm-5 control-label" for="surname">Surname</label> <input
				type="text" id="surname" name="surname"> 
				
			<label class="col-sm-5 control-label" for="email">E-mail</label>
				<input type="text" id="email" name="email">

			<label class="col-sm-5 control-label" for="password">Password</label> <input
				type="password" id="password"  name="password">
			<br/></br>
			<button class="btn btn-success">Register</button>

		</form>
	</div>
</body>
</html>