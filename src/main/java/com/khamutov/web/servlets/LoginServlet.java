package com.khamutov.web.servlets;

import com.khamutov.main.ServiceLocator;
import com.khamutov.web.security.SecurityService;
import com.khamutov.web.templater.PageGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {
    private final SecurityService securityService = ServiceLocator.get(SecurityService.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        PageGenerator.getPage("login.html", pageVariables, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("username");
        String pass = req.getParameter("password");
        boolean login = securityService.login(name, pass);
        Map<String, Object> pageVariables = new HashMap<>();
        if (!login) {
            pageVariables.put("rejected", "true");
            PageGenerator.getPage("login.html", pageVariables, resp.getWriter());
            return;
        }
        Cookie cookieWithToken = securityService.generateCookie(name);
        Cookie userName = new Cookie("userName", name);
        resp.addCookie(userName);
        resp.addCookie(cookieWithToken);
        resp.sendRedirect("/products");
    }
}
