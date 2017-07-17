package com.akushylun.controller.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.akushylun.controller.util.PagePath;

public class GetRegistration implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

	return PagePath.REGISTRATION;

    }
}
