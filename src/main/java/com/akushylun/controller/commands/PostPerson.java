package com.akushylun.controller.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.akushylun.controller.util.PagePath;
import com.akushylun.model.dao.DaoFactory;
import com.akushylun.model.dao.exceptions.DaoException;
import com.akushylun.model.entities.Login;
import com.akushylun.model.entities.Person;
import com.akushylun.model.entities.Person.Role;
import com.akushylun.model.services.PersonService;

public class PostPerson implements Command {
    PersonService service = new PersonService(DaoFactory.getInstance());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException, DaoException {

	int id = Integer.parseInt(request.getParameter("id"));
	String name = request.getParameter("name");
	String surname = request.getParameter("surname");
	String role = request.getParameter("role");
	String email = request.getParameter("email");

	Login login = new Login.Builder().withEmail(email).build();
	Person person = new Person.Builder().withId(id).withName(name).withSurname(surname)
		.withRole(Role.valueOf(role.toUpperCase())).withPersonLogin(login).build();
	service.update(person);
	return PagePath.PERSON_GET;
    }
}