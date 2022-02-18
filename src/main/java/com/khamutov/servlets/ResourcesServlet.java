package com.khamutov.servlets;

import com.khamutov.services.ContentLoader;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResourcesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println(request.getPathInfo());
        ContentLoader.writeScript(request.getPathInfo(), response);
    }
}
