package com.khamutov.web.servlets;

import com.khamutov.services.CartService;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

public class DeleteFromCartServlet extends HttpServlet {

    private final CartService cartService;

    public DeleteFromCartServlet(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String productName = req.getParameter("name");
        Cookie[] cookies = req.getCookies();
        Optional<String> userName = Stream.of(cookies)
                .filter(e -> e.getName().equals("userName"))
                .map(Cookie::getValue)
                .findAny();
        userName.ifPresent(name -> cartService.deleteProductFromCart(productName, name));
        resp.sendRedirect("/products");
    }


}
