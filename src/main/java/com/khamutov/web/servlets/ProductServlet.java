package com.khamutov.web.servlets;

import com.khamutov.entities.CartItem;
import com.khamutov.entities.Product;
import com.khamutov.entities.Session;
import com.khamutov.main.ServiceLocator;
import com.khamutov.services.CartService;
import com.khamutov.services.ProductService;
import com.khamutov.web.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductServlet extends HttpServlet {
    private final ProductService service = ServiceLocator.get(ProductService.class);
    private final CartService cartService = ServiceLocator.get(CartService.class);



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Product> productList = service.getProductList();
        Session session = (Session) req.getAttribute("session");
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("products", productList);
        pageVariables.put("userRole", session.getUserRole().toString());
        PageGenerator.getPage("products.html", pageVariables, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Product> productList = service.getProductList();
        String productName = req.getParameter("name");
        int productPrice = Integer.parseInt(req.getParameter("price"));
        CartItem cartItem = new CartItem(new Product(productPrice, productName), 1);
        Session session = (Session) req.getAttribute("session");
        cartService.addToCart(cartItem,session.getUserName());
        List<CartItem> userCart = cartService.getUserCart(session.getUserName());
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("userCart", userCart);
        pageVariables.put("products", productList);
        pageVariables.put("userRole", session.getUserRole().toString());
        PageGenerator.getPage("products.html", pageVariables, resp.getWriter());
    }

}
