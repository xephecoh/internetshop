package com.khamutov.web.servlets;

import com.khamutov.entities.Product;
import com.khamutov.services.CartService;
import com.khamutov.services.ProductService;
import com.khamutov.templater.PageGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class ProductServlet extends HttpServlet {
    private final ProductService service;
    private final CartService cartService;

    public ProductServlet(ProductService productService, CartService cartService) {
        this.service = productService;
        this.cartService = cartService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Product> productList = service.getProductList();
        Cookie[] cookies = req.getCookies();
        Optional<String> userRole = Stream.of(cookies).filter(e -> e.getName().equals("userRole")).map(Cookie::getValue).findAny();
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("products", productList);
        userRole.ifPresent(s -> pageVariables.put("userRole", s));
        PageGenerator.getPage("products.html", pageVariables, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Product> productList = service.getProductList();
        String productName = req.getParameter("name");
        String productPrice = req.getParameter("price");
        Cookie[] cookies = req.getCookies();
        Map<String, Object> pageVariables = new HashMap<>();
        Optional<String> userRole = Stream.of(cookies)
                .filter(e -> e.getName().equals("userRole"))
                .map(Cookie::getValue)
                .findAny();
        Optional<String> userName = Stream.of(cookies)
                .filter(e -> e.getName().equals("userName"))
                .map(Cookie::getValue)
                .findAny();
        userName.ifPresent(s -> cartService.addToCart(s, productName, Integer.parseInt(productPrice)));
        if (userName.isPresent()) {
            List<Product> userCart = cartService.getUserCart(userName.get());
            pageVariables.put("userCart", userCart);
        }
        pageVariables.put("products", productList);
        userRole.ifPresent(role -> pageVariables.put("userRole", role));
        PageGenerator.getPage("products.html", pageVariables, resp.getWriter());
    }

}
