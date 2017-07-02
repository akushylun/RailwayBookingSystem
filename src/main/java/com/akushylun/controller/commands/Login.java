package com.akushylun.controller.commands;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.akushylun.controller.security.Authenticator;
import com.akushylun.controller.security.AuthenticatorImpl;
import com.akushylun.model.entities.Person;
import com.akushylun.model.services.PersonService;

public class Login implements Command {

    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";
    private PersonService service = PersonService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

	Authenticator authenticator = new AuthenticatorImpl(request);
	String pageToGo = "";
	String email = request.getParameter(PARAM_LOGIN);
	String password = request.getParameter(PARAM_PASSWORD);
	Optional<Person> person = service.login(email, password);

	if (person.isPresent()) {
	    HttpSession session = authenticator.getSession();
	    authenticator.setAttributeToSession(session, person.get());
	    pageToGo = "index.jsp";
	} else
	    pageToGo = "/WEB-INF/view/login.jsp";
	return pageToGo;

    }
}
