package com.akushylun.controller.commands;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.akushylun.controller.constants.PagePath;
import com.akushylun.controller.security.Authenticator;
import com.akushylun.controller.security.AuthenticatorImpl;
import com.akushylun.model.dao.exceptions.ServiceException;
import com.akushylun.model.entities.Person;
import com.akushylun.model.services.PersonService;

public class PostLogin implements Command {

    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";
    private PersonService service = PersonService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException, ServiceException {

	Authenticator authenticator = new AuthenticatorImpl(request);
	String pageToGo = "";
	String email = request.getParameter(PARAM_LOGIN);
	String password = request.getParameter(PARAM_PASSWORD);
	Optional<Person> person = service.login(email, password);

	if (person.isPresent()) {
	    HttpSession session = request.getSession(true);
	    authenticator.setAttributeToSession(session, person.get());
	    pageToGo = PagePath.INDEX;
	} else
	    pageToGo = PagePath.LOGIN;
	return pageToGo;

    }
}
