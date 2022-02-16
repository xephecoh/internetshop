package com.study.lab1.servlets;

import com.study.lab1.templater.PageGenerator;

import javax.servlet.ServletException;
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


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String parameter = req.getParameter("product.id");
        service.deleteById(Integer.parseInt(parameter));
    }
}
