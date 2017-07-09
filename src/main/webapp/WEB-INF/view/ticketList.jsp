<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="<c:url value="/resources/css/bootstrap.min.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/header.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/grid.css" />" rel="stylesheet">
</head>
<body>
	<%@include file="parts/header.jsp"%>

	<center>
		<h1>List of tickets</h1>
	</center>
	<center>
		<form action="./tickets" method="GET">
			<div class="container">
				<table class="table">
					<tr>
						<th>#</th>
						<th>Ticket Price</th>
						<th>Train</th>
						<th>Departure time</th>
						<th>Station from</th>
						<th>Station to</th>
					</tr>
					<c:forEach var="ticket" items="${ticketList}">
						<tr>
							<th scope="row"><c:out value="${ticket_rowNum}"></c:out></th>
							<td><c:out value="${ticket.price}" /></td>
							<td><c:out value="${ticket.train.name}" /></td>
							<td><c:out value="${ticket.train.departureList[0].dateTime}" /></td>
							<td><c:out value="${ticket.train.stationList[0].name}" /></td>
							<td><c:out value="${ticket.train.stationList[1].name}" /></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</form>
	</center>
</body>
</html>