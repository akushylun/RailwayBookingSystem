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
<link href="<c:url value="/resources/css/datepicker.css" />"
	rel="stylesheet">
<script src="<c:url value="/resources/js/datepicker.js" />"></script>
</head>
<body>
	<c:import url="parts/header.jsp" />

	<div class="container">
		<form action="./search/findedList" method="POST">
			<div class="col-xs-3 col-xs-offset-5">
				<div class="page-header">
					<center>
						<h2>Find Train Here</h2>
					</center>
				</div>

				<div class="form-group">

					<label for="station from">Station From</label> <select
						class="form-control" id="inputStationFrom" name="stationFrom">
						<c:forEach var="station" items="${stationList}">
							<option><c:out value="${station.name}"></c:out></option>
						</c:forEach>
					</select>
				</div>


				<div class="form-group">
					<label for="station to">Station to</label> <select
						class="form-control" id="inputStationTo" name="stationTo">
						<c:forEach var="station" items="${stationList}">
							<option><c:out value="${station.name}"></c:out></option>
						</c:forEach>
					</select>
				</div>

				<div class="form-group">
					<label for="date">Start Date</label> <input id="start_dt"
						name="date" class='datepicker' size='10' />
				</div>
				<button class="btn btn-lg btn-primary btn-block" type="submit">Search</button>
			</div>
		</form>
	</div>
</body>
</html>