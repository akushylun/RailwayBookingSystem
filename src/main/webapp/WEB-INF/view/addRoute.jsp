<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
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
	<c:import url="parts/header.jsp" />
	<div class="container">
		<form action="./route" method="POST">

			<h2 class="form-signin-heading">Add route</h2>

			<label for="trainName">Train name</label> 
			<input type="text" id="name"
				name="trainName" class="form-control" placeholder="train name" required
				autofocus> 
				
			
				
			<label for="station to">Station to</label> <select
			class="form-control" id="inputStationTo" name="stationTo">
					<c:forEach var="station" items="${stationList}">
						<option><c:out value="${station.name}"></c:out></option>
					</c:forEach>
				</select>
				
			<label for="station from">Station From</label> <select
			class="form-control" id="inputStationFrom" name="stationFrom">
					<c:forEach var="station" items="${stationList}">
						<option><c:out value="${station.name}"></c:out></option>
					</c:forEach>
				</select>
				
			<label for="costPrice">Cost Price</label> 
			<input type="text" id="costPrice" name="costPrice" class="form-control"
				placeholder="Cost Price" required> 
				
			<label for="costTime">Cost time</label> 
			<input type="text" id="costTime" name="costTime" class="form-control"
				placeholder="Cost time" required> 
				
			<label for="date">Departure date</label> <input id="start_dt"
						name="date" class='datepicker' size='10' />
			<br/>
			<button class="btn btn-success">Add train route</button>

		</form>
	</div>
</body>
</html>