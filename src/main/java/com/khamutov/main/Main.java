package com.khamutov.main;

import com.khamutov.configuration.PropertiesReader;
import com.khamutov.dao.UserDao;
import com.khamutov.jdbc.JdbcProductDao;
import com.khamutov.dao.ProductDao;
import com.khamutov.jdbc.JdbcUserDao;
import com.khamutov.web.security.SecurityService;
import com.khamutov.services.ProductService;
import com.khamutov.web.security.TokenFilter;
import com.khamutov.web.servlets.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;

import javax.servlet.DispatcherType;
import java.util.EnumSet;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws Exception {


        PropertiesReader propertiesReader = new PropertiesReader("target/classes/application.properties");
        Properties properties = propertiesReader.getProperties();


        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(properties.getProperty("postgres.url"));
        dataSource.setUser(properties.getProperty("postgres.username"));
        dataSource.setPassword(properties.getProperty("postgres.password"));


        Flyway flyway = Flyway.configure().dataSource(
                properties.getProperty("h2.url"),
                properties.getProperty("h2.username"),
                properties.getProperty("h2.password")).load();
        flyway.migrate();


        UserDao userDao = new JdbcUserDao(dataSource);
        SecurityService securityService = new SecurityService(userDao);
        TokenFilter tokenFilter = new TokenFilter(securityService);
        ProductDao jdbcProductDao = new JdbcProductDao(dataSource);
        ProductService productService = new ProductService(jdbcProductDao);
        ProductServlet productServlet = new ProductServlet(productService);
        DeleteProductServlet deleteProductServlet = new DeleteProductServlet(productService);
        UpdateProductServlet updateProductServlet = new UpdateProductServlet(productService);
        LoginServlet loginServlet = new LoginServlet(securityService);
        ResourcesServlet resourcesServlet = new ResourcesServlet();
        AddProductServlet addProductServlet = new AddProductServlet(productService);


        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addFilter(new FilterHolder(tokenFilter), "/update", EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(new FilterHolder(tokenFilter), "/delete", EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(new FilterHolder(tokenFilter), "/products", EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(new FilterHolder(tokenFilter), "/add", EnumSet.of(DispatcherType.REQUEST));
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
