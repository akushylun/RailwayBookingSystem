<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<!-- Bootstrap core CSS -->
<link href="<c:url value="/resources/css/bootstrap.min.css" />"
	rel="stylesheet">

<link href="<c:url value="/resources/css/header.css" />"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="<c:url value="/resources/css/grid.css" />" rel="stylesheet">

</head>
<body>
	<%@include file="parts/header.jsp"%>

	<div class="container">
		<center><h1>List Of Orders</h1></center>
		<table class="table">
			<thead>

				<tr>
					<th>#</th>
					<th>Price</th>
					<th>Date</th>
					<th>TicketInfo</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="booking" items="${bookingList}">
					<tr>
						<th scope="row"><c:out value="${booking_rowNum}"></c:out></th>
						<td><c:out value="${booking.price}" /></td>
						<td><c:out value="${booking.date}" /></td>
						<td><a href="./tickets/${booking.id}">Ticket Info</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>