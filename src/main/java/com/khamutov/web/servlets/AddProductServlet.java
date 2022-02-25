package com.khamutov.web.servlets;

import com.khamutov.entities.Product;
import com.khamutov.web.security.SecurityService;
import com.khamutov.services.ProductService;
import com.khamutov.templater.PageGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddProductServlet extends HttpServlet {
    private ProductService service;

    public AddProductServlet(ProductService productService) {
        this.service = productService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String price = req.getParameter("price");
        Product newProduct = new Product(Integer.parseInt(id), name, Integer.parseInt(price));
        service.save(newProduct);
        resp.sendRedirect("/products");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        PageGenerator.getPage("createProductForm.html", pageVariables, resp.getWriter());
    }
}
