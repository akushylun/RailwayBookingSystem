package com.akushylun.controller.commands;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.akushylun.controller.security.Authenticator;
import com.akushylun.controller.security.AuthenticatorImpl;
import com.akushylun.controller.util.PagePath;
import com.akushylun.controller.util.RegexValidator;
import com.akushylun.model.dao.DaoFactory;
import com.akushylun.model.dao.exceptions.DaoException;
import com.akushylun.model.entities.Login;
import com.akushylun.model.entities.Person;
import com.akushylun.model.entities.Person.Role;
import com.akushylun.model.services.PersonService;

public class PostRegistration implements Command {

    private static final String PARAM_NAME = "name";
    private static final String PARAM_SURNAME = "surname";
    private static final String PARAM_EMAIL = "email";
    private static final String PARAM_PASSWORD = "password";

    private PersonService service = new PersonService(DaoFactory.getInstance());

    private Pattern namePatern = RegexValidator.compileRegExpression(RegexValidator.NAME);
    private Pattern surnamePatern = RegexValidator.compileRegExpression(RegexValidator.SURNAME);
    private Pattern emailPatern = RegexValidator.compileRegExpression(RegexValidator.EMAIL);
    private Pattern passwordPatern = RegexValidator.compileRegExpression(RegexValidator.PASSWORD);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException, DaoException {

	String name = request.getParameter(PARAM_NAME);
	String surname = request.getParameter(PARAM_SURNAME);
	String email = request.getParameter(PARAM_EMAIL);
	String password = request.getParameter(PARAM_PASSWORD);

	boolean inputPersonParamsAreValid = isValidPerson(name, surname, email, password);
	if (inputPersonParamsAreValid) {

	    Login login = new Login.Builder().withEmail(email).withPassword(password).build();
	    Person person = new Person.Builder().withName(name).withSurname(surname).withPersonLogin(login)
		    .withRole(Role.USER).build();

	    service.create(person);

	    HttpSession session = request.getSession(true);
	    Authenticator authenticator = new AuthenticatorImpl(request);
	    authenticator.setAttributeToSession(session, person);
	}

	return PagePath.INDEX;

    }

    private boolean isValidPerson(String name, String surname, String email, String password) {
	Matcher emailMatcher = emailPatern.matcher(email);
	Matcher passwordMatcher = passwordPatern.matcher(password);
	Matcher nameMatcher = namePatern.matcher(name);
	Matcher surnameMatcher = surnamePatern.matcher(surname);
	boolean isMatched = (emailMatcher.matches() && passwordMatcher.matches() && nameMatcher.matches()
		&& surnameMatcher.matches()) == true;
	return isMatched;
    }
}
