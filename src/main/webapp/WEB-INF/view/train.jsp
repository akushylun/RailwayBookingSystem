<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="<c:url value="/resources/css/bootstrap.min.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/header.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/datepicker.css" />"
	rel="stylesheet">
<script src="<c:url value="/resources/js/datepicker.js" />"></script>
</head>
<body>
	<%@include file="parts/header.jsp"%>

	<form action="./shedule" method="POST">

		<center>
			<h1>Find Train Here</h1>
		</center>

		<center>
			Station From <select name="stationFrom">
				<c:forEach var="station" items="${stationList}">
					<option><c:out value="${station.name}"></c:out></option>
				</c:forEach>
			</select> <br /> Station To <select name="stationTo">
				<c:forEach var="station" items="${stationList}">
					<option><c:out value="${station.name}"></c:out></option>
				</c:forEach>
			</select><br />

			<p>
				Start Date/Time: <input id="start_dt" name="date" class='datepicker'
					size='11' />
			</p>
			<br /> <input type="submit" value="Submit" />

		</center>
	</form>

</body>
</html>