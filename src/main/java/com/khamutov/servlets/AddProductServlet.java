package com.khamutov.servlets;

import com.khamutov.services.ProductService;
import com.khamutov.templater.PageGenerator;

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
        service.save(Integer.parseInt(id), name, Integer.parseInt(price));
        resp.sendRedirect("/products");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        Map<String, Object> pageVariables = new HashMap<>();
        String page = pageGenerator.getPage("createProductForm.html", pageVariables);
        resp.getWriter().println(page);
    }
}
