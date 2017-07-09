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
		<h1>List Of FindedShedules</h1>
	</center>
	<center>
		<table border="1" cellpadding="5">
			<tr>
				<th>Time start</th>
				<th>Time end</th>
			</tr>
			<c:forEach var="shedule" items="${sheduleList}">
				<tr>
					<td><c:out value="${shedule.start}" /></td>
					<td><c:out value="${shedule.end}" /></td>
				</tr>
			</c:forEach>
		</table>
	</center>
</body>
</html>