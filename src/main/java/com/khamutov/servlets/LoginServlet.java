package com.khamutov.servlets;

import com.khamutov.services.CookiesService;
import com.khamutov.services.ProductService;
import com.khamutov.templater.PageGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

import java.util.Map;
import java.util.UUID;

public class LoginServlet extends HttpServlet {
    private ProductService productService;
    private CookiesService service;

    public LoginServlet(ProductService productService, CookiesService service) {
        this.productService = productService;
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
        if (name.equals("username") & pass.equals("password")) {
            UUID token = UUID.randomUUID();
            Cookie cookie = new Cookie("token", token.toString());
            System.out.println(cookie.getValue());
            resp.addCookie(cookie);
            CookiesService.getInstance().getTokenList().add(token.toString());
            System.out.println(CookiesService.getInstance().getTokenList().get(0));
            resp.sendRedirect("/products");
            return;
        }
        resp.sendRedirect("/login");
    }
}
