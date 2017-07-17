<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Railway booking</title>

<link href="<c:url value="/resources/css/signin.css" />"
	rel="stylesheet">

</head>

<body>
	<c:import url="parts/header.jsp" />

	<div class="container">

		<form class="form-signin" action="./login" method="POST">

			<h2 class="form-signin-heading">Please sign in</h2>

			<label for="inputEmail" class="sr-only">Email address</label> <input
				type="email" id="inputEmail" name="login" class="form-control"
				placeholder="Email" required autofocus>

			<c:if test="${not empty loginError}">
				<c:out value="${loginError}" />
			</c:if>

			<label for="inputPassword" class="sr-only">Password</label> <input
				type="password" id="inputPassword" name="password"
				class="form-control" placeholder="Password" required>

			<c:if test="${not empty passwordError}">
				<c:out value="${passwordError}" />
			</c:if>

			<div class="checkbox">
				<a href="view/registration">Register</a>
			</div>

			<button class="btn btn-lg btn-primary btn-block" type="submit">Sign
				in</button>
		</form>

	</div>
	<!-- /container -->

</body>
</html>
