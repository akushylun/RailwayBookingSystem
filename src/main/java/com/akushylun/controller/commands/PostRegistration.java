package com.akushylun.controller.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.akushylun.controller.security.Authenticator;
import com.akushylun.controller.security.AuthenticatorImpl;
import com.akushylun.model.dao.exceptions.ServiceException;
import com.akushylun.model.entities.Login;
import com.akushylun.model.entities.Person;
import com.akushylun.model.entities.Person.Role;
import com.akushylun.model.services.PersonService;

public class PostRegistration implements Command {

    private static final String PARAM_NAME = "name";
    private static final String PARAM_SURNAME = "surname";
    private static final String PARAM_EMAIL = "email";
    private static final String PARAM_PASSWORD = "password";
    private PersonService service = PersonService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException, ServiceException {

	String pageToGo = null;
	Person person = null;
	Login login = null;
	String name = request.getParameter(PARAM_NAME);
	String surname = request.getParameter(PARAM_SURNAME);
	String email = request.getParameter(PARAM_EMAIL);
	String password = request.getParameter(PARAM_PASSWORD);

	Authenticator authenticator = new AuthenticatorImpl(request);

	login = new Login.Builder().withEmail(email).withPassword(password).build();
	person = new Person.Builder().withName(name).withSurname(surname).withPersonLogin(login).withRole(Role.USER)
		.build();
	service.create(person);

	HttpSession session = request.getSession(true);
	authenticator.setAttributeToSession(session, person);
	pageToGo = "index.jsp";

	return pageToGo;

    }
}
