package com.khamutov.web.servlets;

import com.khamutov.entities.Session;
import com.khamutov.services.CartService;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class DeleteFromCartServlet extends HttpServlet {

    private final CartService cartService;

    public DeleteFromCartServlet(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String productName = req.getParameter("productName");
        Session session = (Session) req.getAttribute("session");
        String userName = session.getUserName();
        cartService.deleteProductFromCart(userName, productName);
        resp.sendRedirect("/products");
    }
}
