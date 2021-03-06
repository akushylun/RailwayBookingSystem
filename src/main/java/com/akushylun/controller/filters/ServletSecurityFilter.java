package com.akushylun.controller.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.akushylun.controller.util.Authenticator;
import com.akushylun.controller.util.AuthenticatorImpl;
import com.akushylun.controller.util.PagePath;

/**
 * ServletSecurityFilter class checks if the Person has a session. If true, the
 * person can move free, if false the Person must logIn
 */
@WebFilter(urlPatterns = { "/controllers" }, servletNames = { "frontController" })
public class ServletSecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	    throws IOException, ServletException {

	HttpServletRequest req = (HttpServletRequest) request;
	HttpServletResponse resp = (HttpServletResponse) response;
	Authenticator auth = new AuthenticatorImpl(req);

	String loginURI = "/login";
	String registationURI = "/registration";
	String requestURI = req.getRequestURI().replaceAll(".*/view", "");
	boolean allowedURI = requestURI.equals(loginURI) || requestURI.equals(registationURI);

	if (!auth.isLoggedIn() && !allowedURI) {
	    RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(PagePath.LOGIN);
	    dispatcher.forward(req, resp);
	    return;
	}
	chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
