package com.akushylun.controller.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.akushylun.controller.util.PagePath;
import com.akushylun.model.dao.DaoFactory;
import com.akushylun.model.entities.Login;
import com.akushylun.model.entities.Person;
import com.akushylun.model.entities.Person.Role;
import com.akushylun.model.services.PersonService;

public class GetUser implements Command {

    PersonService service = new PersonService(DaoFactory.getInstance());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

	int id = Integer.parseInt(request.getParameter("id"));
	String name = request.getParameter("name");
	String surname = request.getParameter("surname");
	String role = request.getParameter("role");
	String email = request.getParameter("email");

	Login login = new Login.Builder().withId(id).withEmail(email).build();
	Person person = new Person.Builder().withName(name).withSurname(surname).withRole(Role.valueOf(role))
		.withPersonLogin(login).build();
	request.setAttribute("person", person);
	return PagePath.USER_GET;
    }
}
