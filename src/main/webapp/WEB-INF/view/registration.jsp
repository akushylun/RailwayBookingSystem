<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<center>
		<h1>Login with Email and Password</h1>
	</center>
	<form action="./registration" method="POST">
		<center>
			<table border="1" width="30%" cellpadding="3">
				<thead>
					<tr>
						<th colspan="2">Registration Here</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>Name</td>
						<td><input type="text" name="name" /></td>
					</tr>
					<tr>
						<td>Surname</td>
						<td><input type="text" name="surname" /></td>
					</tr>
					<tr>
						<td>Email</td>
						<td><input type="text" name="email" /></td>
					</tr>
					<tr>
						<td>Login</td>
						<td><input type="text" name="login" /></td>
					</tr>
					<tr>
						<td>Password</td>
						<td><input type="password" name="password" /></td>
					</tr>
					<tr>
						<td><input type="submit" value="Register" /></td>
					</tr>
				</tbody>
			</table>
		</center>
	</form>
</body>
</html>