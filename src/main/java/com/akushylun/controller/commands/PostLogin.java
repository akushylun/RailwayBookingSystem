package com.akushylun.controller.commands;

import java.io.IOException;
import java.util.Optional;
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
import com.akushylun.model.dao.exceptions.ServiceException;
import com.akushylun.model.entities.Person;
import com.akushylun.model.services.PersonService;

public class PostLogin implements Command {

    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";

    // TODO
    private PersonService service = PersonService.getInstance();

    private Pattern emailPatern = RegexValidator.compileRegExpression(RegexValidator.EMAIL);
    private Pattern passwordPatern = RegexValidator.compileRegExpression(RegexValidator.PASSWORD);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException, ServiceException {

	String pageToGo = "";
	String email = request.getParameter(PARAM_LOGIN);
	String password = request.getParameter(PARAM_PASSWORD);

	boolean inputPersonParamsAreValid = validatePerson(email, password);

	if (!inputPersonParamsAreValid) {
	    pageToGo = PagePath.LOGIN;
	} else {
	    Optional<Person> person = service.login(email, password);
	    if (person.isPresent()) {
		HttpSession session = request.getSession(true);
		Authenticator authenticator = new AuthenticatorImpl(request);
		authenticator.setAttributeToSession(session, person.get());
		pageToGo = PagePath.INDEX;
	    }
	}
	return pageToGo;
    }

    private boolean validatePerson(String email, String password) {
	Matcher emailMatcher = emailPatern.matcher(email);
	Matcher passwordMatcher = passwordPatern.matcher(password);
	boolean isMatched = (emailMatcher.matches() && passwordMatcher.matches()) == true;
	return isMatched;
    }
}
