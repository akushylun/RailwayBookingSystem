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
</head>
<body>
	<%@include file="parts/header.jsp"%>

	<div class="container">
		<form action="./bookings" method="POST">
			<div class="page-header">
				<center>
					<h1>List Of Finded Trains</h1>
				</center>
			</div>
			<table class="table table-striped">
				<thead>
					<tr>
						<th>Train</th>
						<th>Station start</th>
						<th>Station End</th>
						<th>Time departure</th>
						<th>Ticket Price</th>
						<th>Buy ticket</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="ticket" items="${trainList}">
						<tr>
							<td><c:out value="${ticket.train.name}" /></td>
							<td><c:out value="${ticket.train.stationList[0].name}" /></td>
							<td><c:out value="${ticket.train.stationList[1].name}" /></td>
							<td><c:out value="${ticket.train.departureList[0].dateTime}" /></td>
							<td><c:out value="${ticket.price}" /></td>
							<td><button class="btn btn-success btn-sm" type="submit">
									Buy ticket</button></td>
							<input type="hidden" id="thisField" name="ticketId"
								value="${ticket.train.id}" />
							<input type="hidden" name="ticketPrice" value="${ticket.price}" />
							<input type="hidden" name="ticketDate" value="${ticket.train.departureList[0].dateTime}" />
						</tr>

					</c:forEach>
				</tbody>
			</table>
		</form>
	</div>
</body>
</html>