package com.khamutov.web.servlets;

import com.khamutov.web.ContentLoader;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResourcesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  {
        ContentLoader.writeScript(request.getPathInfo(), response);
    }
}
