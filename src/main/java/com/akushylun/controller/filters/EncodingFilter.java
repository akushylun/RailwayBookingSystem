package com.akushylun.controller.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * Transform encoding of request, response on UTF-8 encoding
 */
@WebFilter(urlPatterns = { "/*" }, initParams = {
	@WebInitParam(name = "encoding", value = "UTF-8", description = "Encoding Param") })
public class EncodingFilter implements Filter {

    private String code;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
	code = filterConfig.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	    throws IOException, ServletException {

	String codeRequest = request.getCharacterEncoding();
	if (code != null && !code.equalsIgnoreCase(codeRequest)) {
	    request.setCharacterEncoding(code);
	    response.setCharacterEncoding(code);
	}
	chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
	code = null;
    }

}
