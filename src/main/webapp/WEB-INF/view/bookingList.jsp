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
		<h1>List Of Bookings</h1>
	</center>
	<center>
		<table border="1" cellpadding="5">
			<tr>
				<th>Price</th>
				<th>Date</th>
				<th>TicketInfo</th>
			</tr>
			<c:forEach var="booking" items="${bookingList}">
				<tr>
					<td><c:out value="${booking.price}" /></td>
					<td><c:out value="${booking.date}" /></td>
					<td><a href="./tickets/${booking.id}">Ticket Info</a></td>
				</tr>
			</c:forEach>
		</table>
	</center>
</body>
</html>