package com.khamutov.web.servlets;

import com.khamutov.entities.Product;
import com.khamutov.main.ServiceLocator;
import com.khamutov.services.ProductService;
import com.khamutov.web.templater.PageGenerator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddProductServlet extends HttpServlet {
    private final ProductService service = ServiceLocator.get(ProductService.class);


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String name = req.getParameter("name");
        int price = Integer.parseInt(req.getParameter("price"));
        Product newProduct = new Product(price,name);
        service.save(newProduct);
        resp.sendRedirect("/products");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        PageGenerator.getPage("createProductForm.html", pageVariables, resp.getWriter());
    }
}
