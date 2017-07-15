<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<header>
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="index.jsp">RailwayBooking</a>
			</div>

			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">

					<c:choose>
						<c:when test="${sessionScope.authToken.role eq 'USER'}">
							<li class="active"><a href="./view/bookings">Orders</a></li>
							<li><a href="view/ticket/search">Search Train</a></li>
						</c:when>
						<c:when test="${sessionScope.authToken.role eq 'ADMIN'}">
							<li class="active"><a href="./view/persons">Users</a></li>
							<li><a href="view/trainList">Trains</a></li>
						</c:when>
					</c:choose>
				</ul>

				<ul class="nav navbar-nav navbar-right">
					<li><a href="view/login">Login</a></li>
					<li><a href="view/logout">Logout</a></li>
				</ul>

			</div>
			<!--/.nav-collapse -->
		</div>
	</nav>
</header>
