package com.study.lab1.main;

import com.study.lab1.servlets.AllRequestsServlet;
import com.study.lab1.servlets.ProductDao;
import com.study.lab1.servlets.ProductService;
import com.study.lab1.servlets.ProductServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args) throws Exception {

        ProductDao productDao = new ProductDao();
        ProductService productService = new ProductService(productDao);
        ProductServlet productServlet = new ProductServlet(productService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(productServlet), "/product");
        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
    }
}
