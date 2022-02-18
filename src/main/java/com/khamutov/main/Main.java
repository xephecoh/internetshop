package com.khamutov.main;

import com.khamutov.services.ProductDao;
import com.khamutov.services.ProductService;
import com.khamutov.servlets.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args) throws Exception {

        ProductDao productDao = new ProductDao();
        ProductService productService = new ProductService(productDao);
        ProductServlet productServlet = new ProductServlet(productService);
        DeleteProductServlet deleteProductServlet = new DeleteProductServlet(productService);
        UpdateProductServlet updateProductServlet = new UpdateProductServlet(productService);
        ResourcesServlet resourcesServlet = new ResourcesServlet();

        AddProductServlet addProductServlet = new AddProductServlet(productService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(productServlet), "/products");
        context.addServlet(new ServletHolder(deleteProductServlet), "/delete");
        context.addServlet(new ServletHolder(addProductServlet), "/add");
        context.addServlet(new ServletHolder(updateProductServlet), "/update");
        context.addServlet(new ServletHolder(resourcesServlet), "/script/*");
        Server server = new Server(8081);
        server.setHandler(context);
        server.start();
    }
}
