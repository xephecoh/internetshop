package com.khamutov.servlets;

import com.khamutov.services.ProductService;
import com.khamutov.templater.PageGenerator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UpdateProductServlet extends HttpServlet {
    private ProductService service;

    public UpdateProductServlet(ProductService productService) {
        this.service = productService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String price = req.getParameter("price");
        Map<String,Object> pageVariables = new HashMap<>();
        pageVariables.put("id",id);
        pageVariables.put("name",name);
        pageVariables.put("price",price);
        String page = PageGenerator.instance().getPage("updateForm.html",pageVariables);
        resp.getWriter().println(page);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String price = req.getParameter("price");
        service.update(Integer.parseInt(id),name,Integer.parseInt(price));
        resp.sendRedirect("/products");
    }
}
