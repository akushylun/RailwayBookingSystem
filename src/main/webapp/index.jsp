<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="../../favicon.ico">

<title>Railway Booking</title>

<!-- Custom styles for this template -->
<link href="<c:url value="/resources/css/starter-template.css" />"
	rel="stylesheet">

</head>

<body>
	<%@include file="WEB-INF/view/parts/header.jsp"%>
	<div class="container">

		<div class="starter-template">
			<h1>Railway booking office</h1>

			<p class="lead">
				Booking office where You can buy tickets<br>
			</p>
		</div>

	</div>
	<!-- /.container -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script>
		window.jQuery
				|| document
						.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')
	</script>
	<script src="<c:url value="/resources/js/bootstrap.min.js"/>">
		
	</script>

</body>
</html>
