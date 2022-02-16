package com.khamutov.servlets;



import com.khamutov.services.ProductService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class DeleteProductServlet extends HttpServlet {
    private ProductService service;
    public DeleteProductServlet(ProductService productService) {
        this.service = productService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        service.deleteById(Integer.parseInt(id));
        resp.sendRedirect("/products");
    }

}
