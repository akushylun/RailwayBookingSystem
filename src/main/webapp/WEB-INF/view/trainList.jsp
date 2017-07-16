<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
</head>
<body>
	<c:import url="parts/header.jsp" />
		<div class="page-header">
			<center>
				<h1>List Of Routes</h1>
			</center>
		</div>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Train Id</th>
					<th>Train</th>
					<th>Station From</th>
					<th>Station To</th>
					<th>Departure</th>
					<th>Arrival</th>
					<th>Ticket Price</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="train" items="${trainList}">

					<tr>
						<td><c:out value="${train.id}" /></td>
						<td><c:out value="${train.name}" /></td>
						<td><c:out value="${train.stationList[0].station.name}" /></td>
						<td><c:out value="${train.stationList[1].station.name}" /></td>
						<td><c:out value="${train.stationList[0].datetime}" /></td>
						<td><c:out value="${train.stationList[1].datetime}" /></td>
						<td><c:out
								value="${train.stationList[1].cost_price - train.stationList[0].cost_price}" /></td>
					</tr>
					<input type="hidden" name="train" value="${trainList}">
				</c:forEach>
			</tbody>
		</table>
</body>
</html>