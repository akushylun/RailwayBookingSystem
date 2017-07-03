<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<center>
		<h1>List of tickets</h1>
	</center>
	<center>
		<form action="./tickets" method="GET">
			<table border="1" cellpadding="5">
				<tr>
					<th>Ticket Price</th>
					<th>Train</th>
					<th>Station From</th>
					<th>Station To</th>
					<th>Time from</th>
					<th>Time to</th>
				</tr>
				<c:forEach var="ticket" items="${ticketList}">
					<tr>
						<td><c:out value="${ticket.price}" /></td>
						<td><c:out value="${ticket.shedule.train.name}" /></td>
						<td><c:out value="${ticket.shedule.station.from}" /></td>
						<td><c:out value="${ticket.shedule.station.to}" /></td>
						<td><c:out value="${ticket.shedule.start}" /></td>
						<td><c:out value="${ticket.shedule.end}" /></td>
					</tr>
				</c:forEach>
			</table>
		</form>
	</center>
</body>
</html>