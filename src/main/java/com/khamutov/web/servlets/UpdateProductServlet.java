package com.khamutov.web.servlets;

import com.khamutov.entities.Product;
import com.khamutov.web.security.SecurityService;
import com.khamutov.services.ProductService;
import com.khamutov.web.RequestBodyParser;
import com.khamutov.templater.PageGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class UpdateProductServlet extends HttpServlet {
    private final ProductService service;
    private final RequestBodyParser requestBodyParser;

    public UpdateProductServlet(ProductService productService) {
        this.service = productService;
        requestBodyParser = new RequestBodyParser();

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String price = req.getParameter("price");
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("id", id);
        pageVariables.put("name", name);
        pageVariables.put("price", price);
        PageGenerator.getPage("updateForm.html", pageVariables, resp.getWriter());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String body = req.getReader().lines().collect(Collectors.joining());
        Product product = requestBodyParser.parseRequest(body).getProduct();
        service.update(product);
    }

}
