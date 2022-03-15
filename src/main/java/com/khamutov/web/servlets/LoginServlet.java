package com.khamutov.web.servlets;

import com.khamutov.web.security.SecurityService;
import com.khamutov.templater.PageGenerator;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {
    private final SecurityService service;


    public LoginServlet(SecurityService service) {
        this.service = service;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        PageGenerator.getPage("login.html", pageVariables, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("username");
        String pass = req.getParameter("password");
        if (service.validateUser(name,pass)) {
            String userRole = service.getUserRole(name);
            Cookie cookieWithToken = service.generateCookie();
            Cookie cookieWithRole = service.addUserRole(userRole);
            Cookie userName = new Cookie("userName", name);
            resp.addCookie(userName);
            resp.addCookie(cookieWithToken);
            resp.addCookie(cookieWithRole);
            resp.sendRedirect("/products");
            return;
        }
        resp.sendRedirect("/login");
    }
}
