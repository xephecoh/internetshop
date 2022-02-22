package com.khamutov.main;

import com.khamutov.configuration.PropertiesReader;
import com.khamutov.dao.ConnectionFactory;
import com.khamutov.dao.MyH2DataSource;
import com.khamutov.jdbc.JdbcProductDao;
import com.khamutov.dao.ProductDao;
import com.khamutov.services.CookiesService;
import com.khamutov.services.ProductService;
import com.khamutov.servlets.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws Exception {

        CookiesService cookiesService = new CookiesService();
        PropertiesReader propertiesReader = new PropertiesReader("target/classes/application.properties");
        Properties properties = propertiesReader.getProperties();

        MyH2DataSource mysqlDS = new MyH2DataSource(properties.getProperty("h2.url"),
                properties.getProperty("h2.username"),
                properties.getProperty("h2.password"));

        Flyway flyway = Flyway.configure().dataSource(
                properties.getProperty("h2.url"),
                properties.getProperty("h2.username"),
                properties.getProperty("h2.password")).load();

        flyway.migrate();


        ProductDao jdbcProductDao = new JdbcProductDao( mysqlDS);
        ProductService productService = new ProductService( jdbcProductDao);
        ProductServlet productServlet = new ProductServlet(productService);
        DeleteProductServlet deleteProductServlet = new DeleteProductServlet(productService);
        UpdateProductServlet updateProductServlet = new UpdateProductServlet(productService);
        LoginServlet loginServlet = new LoginServlet(productService,cookiesService);
        ResourcesServlet resourcesServlet = new ResourcesServlet();
        AddProductServlet addProductServlet = new AddProductServlet(productService);


        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(productServlet), "/products");
        context.addServlet(new ServletHolder(deleteProductServlet), "/delete");
        context.addServlet(new ServletHolder(addProductServlet), "/add");
        context.addServlet(new ServletHolder(updateProductServlet), "/update");
        context.addServlet(new ServletHolder(loginServlet), "/login");
        context.addServlet(new ServletHolder(resourcesServlet), "/script/*");
        Server server = new Server(8081);
        server.setHandler(context);
        server.start();
    }
}
