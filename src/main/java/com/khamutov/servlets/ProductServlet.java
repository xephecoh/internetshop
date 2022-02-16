package com.khamutov.servlets;

import com.khamutov.entities.Product;
import com.khamutov.services.ProductService;
import com.khamutov.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductServlet extends HttpServlet {
    private ProductService service;
    public ProductServlet(ProductService productService) {
        this.service = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        List<Product> productList = service.getProductList();
        PageGenerator pageGenerator = PageGenerator.instance();
        Map<String,Object> pageVariables = new HashMap<>();
        pageVariables.put("products",productList);
        String page = pageGenerator.getPage("products.html",pageVariables);
        resp.getWriter().println(page);
    }

}
