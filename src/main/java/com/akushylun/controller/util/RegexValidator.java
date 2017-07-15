package com.akushylun.controller.util;

import java.util.regex.Pattern;

public final class RegexValidator {

    public static final String NAME = "^[a-zA-Z0-9_-]{3,15}$";
    public static final String SURNAME = "^[a-zA-Z0-9_-]{3,15}$";;
    public static final String EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String PASSWORD = "((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{2,})";

    public static final String STATION = "^[a-zA-Z]+$";
    public static final String DATE = "((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])";

    /**
     * Compiles the given regular expression into Pattern
     * 
     * @param regex
     *            The expression to be compiled
     * @return the given regular expression returned into Pattern
     */
    public static Pattern compileRegExpression(String regex) {
	Pattern pattern = Pattern.compile(regex);
	return pattern;
    }

}
