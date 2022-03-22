package com.khamutov.web.security;


import com.khamutov.entities.Session;
import com.khamutov.entities.UserRole;
import com.khamutov.main.ServiceLocator;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class TokenFilter implements Filter {
    private final SecurityService securityService = ServiceLocator.get(SecurityService.class);

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            response.sendRedirect("/login");
        } else {
            Optional<String> optionalToken = Stream.of(cookies)
                    .filter(cookie -> cookie.getName().equals("token"))
                    .map(Cookie::getValue)
                    .filter(securityService::isTokenPresent)
                    .findAny();
            if (!optionalToken.isPresent()) {
                response.sendRedirect("/login");
            } else {
                Session sessionByToken = securityService.getSessionByToken(optionalToken.get());
                if (isAccessGranted(sessionByToken.getUserRole())) {
                    response.sendRedirect("/login");
                }
                request.setAttribute("session", sessionByToken);
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }

    @Override
    public void destroy() {
    }

    abstract boolean isAccessGranted(UserRole userRole);
}
