package com.khamutov.main;

import com.khamutov.configuration.PropertiesReader;
import com.khamutov.jdbc.JdbcCartDao;
import com.khamutov.jdbc.JdbcProductDao;
import com.khamutov.jdbc.JdbcUserDao;
import com.khamutov.jdbc.dao.CartDao;
import com.khamutov.jdbc.dao.ProductDao;
import com.khamutov.jdbc.dao.UserDao;
import com.khamutov.services.CartService;
import com.khamutov.services.ProductService;
import com.khamutov.services.UserService;
import com.khamutov.web.security.SecurityService;
import org.postgresql.ds.PGSimpleDataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ServiceLocator {
    private static final Map<Class<?>, Object> CONTEXT = new HashMap<>();

    static {
        PropertiesReader propertiesReader = new PropertiesReader("target/classes/application.properties");
        Properties properties = propertiesReader.getProperties();

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(properties.getProperty("postgres.url"));
        dataSource.setUser(properties.getProperty("postgres.username"));
        dataSource.setPassword(properties.getProperty("postgres.password"));

        UserDao userDao = new JdbcUserDao(dataSource);
        ProductDao jdbcProductDao = new JdbcProductDao(dataSource);
        CartDao jdbcCartDao = new JdbcCartDao(dataSource);

        UserService userService = new UserService(userDao);
        CONTEXT.put(UserService.class, userService);
        SecurityService securityService = new SecurityService();
        CONTEXT.put(SecurityService.class, securityService);
        ProductService productService = new ProductService(jdbcProductDao);
        CONTEXT.put(ProductService.class, productService);
        CartService cartService = new CartService(jdbcCartDao);
        CONTEXT.put(CartService.class, cartService);


    }

    public static <T> T get(Class<T> clazz) {
        return clazz.cast(CONTEXT.get(clazz));
    }
}

