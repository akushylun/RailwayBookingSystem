<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Trip page</title>
</head>
<body>
	<h1>Search train</h1>
	<form action="views/shedules" method="POST">
		<center>
			<table border="1" width="30%" cellpadding="3">
				<thead>
					<tr>
						<th colspan="2">Search train</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>Station From</td>
						<td><input type="text" name="to" /></td>
					</tr>
					<tr>
						<td>Station To</td>
						<td><input type="text" name="from" /></td>
					</tr>
					<tr>
						<td>Date</td>
						<td><input type="text" name="date" /></td>
					</tr>
					<tr>
						<td><input type="submit" value="Search" value="submit" /></td>
					</tr>
				</tbody>
			</table>
		</center>
		</form>
</body>
</html>