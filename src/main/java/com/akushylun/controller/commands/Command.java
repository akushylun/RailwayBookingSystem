package com.akushylun.controller.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    /**
     * 
     * @param request
     *            HTTP request from Servlet
     * @param response
     *            HTTP response from Servlet
     * @return page path to which request will be forwarded
     */
    String execute(HttpServletRequest request, HttpServletResponse response);
}
