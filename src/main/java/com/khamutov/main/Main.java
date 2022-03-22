package com.khamutov.main;

import com.khamutov.configuration.PropertiesReader;
import com.khamutov.jdbc.JdbcCartDao;
import com.khamutov.jdbc.dao.UserDao;
import com.khamutov.jdbc.JdbcProductDao;
import com.khamutov.jdbc.dao.ProductDao;
import com.khamutov.jdbc.JdbcUserDao;
import com.khamutov.services.CartService;
import com.khamutov.services.UserService;
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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws Exception {


        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
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
        JdbcCartDao jdbcCartDao = new JdbcCartDao(dataSource);
        CartService cartService = new CartService(jdbcCartDao);
        ProductDao jdbcProductDao = new JdbcProductDao(dataSource);
        SecurityService securityService = new SecurityService();
        TokenFilter tokenFilter = new TokenFilter();
        UserService userService = new UserService(userDao);
        ProductService productService = new ProductService(jdbcProductDao);
        ProductServlet productServlet = new ProductServlet();
        DeleteProductServlet deleteProductServlet = new DeleteProductServlet();
        UpdateProductServlet updateProductServlet = new UpdateProductServlet();
        DeleteFromCartServlet deleteFromCartServlet = new DeleteFromCartServlet(cartService);
        LoginServlet loginServlet = new LoginServlet();
        ResourcesServlet resourcesServlet = new ResourcesServlet();
        AddProductServlet addProductServlet = new AddProductServlet();
        RegisterServlet registerServlet = new RegisterServlet();

        executorService.scheduleWithFixedDelay(securityService, 0, 10, TimeUnit.SECONDS);


        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addFilter(new FilterHolder(tokenFilter), "/update", EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(new FilterHolder(tokenFilter), "/delete", EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(new FilterHolder(tokenFilter), "/products", EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(new FilterHolder(tokenFilter), "/add", EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(new FilterHolder(tokenFilter), "/deleteFromCart", EnumSet.of(DispatcherType.REQUEST));
        context.addServlet(new ServletHolder(updateProductServlet), "/update");
        context.addServlet(new ServletHolder(deleteProductServlet), "/delete");
        context.addServlet(new ServletHolder(productServlet), "/products");
        context.addServlet(new ServletHolder(addProductServlet), "/add");
        context.addServlet(new ServletHolder(loginServlet), "/login");
        context.addServlet(new ServletHolder(resourcesServlet), "/script/*");
        context.addServlet(new ServletHolder(registerServlet), "/register");
        context.addServlet(new ServletHolder(deleteFromCartServlet), "/deleteFromCart");
        Server server = new Server(8081);
        server.setHandler(context);
        server.start();
    }
}
