package com.example.sportalproject.controller;
import com.example.sportalproject.exceptions.UnauthorisedException;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public abstract class SessionValidator {

    public static final String LOGGED = "logged";
    public static final String LOGGED_FROM = "logged_from";
    public static final String USER_ID = "user_id";
    public static final String LOGGED_OUT = "logged out";

    public static void validateSession(HttpSession session, HttpServletRequest request) {
        boolean newSession = session.isNew();
        boolean logged = session.getAttribute(LOGGED) != null && ((Boolean) session.getAttribute(LOGGED));
        boolean sameIP = request.getRemoteAddr().equals(session.getAttribute(LOGGED_FROM));
        if (newSession || !logged || !sameIP) {
            throw new UnauthorisedException("Invalid session! Please, login!");
        }
    }




}

