package com.mlesniak.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationFilter implements Filter {
    private AuthenticationService authenticationService;

    @Autowired
    public AuthenticationFilter(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        // Check if we are authenticated but ignore the callback URL.
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        if (!httpRequest.getRequestURI().equals("/callback") && !authenticationService.isAuthenticated()) {
            HttpServletResponse httpResponse = (HttpServletResponse) resp;
            String authenticationUrl = authenticationService.getAuthenticationUrl();
            httpResponse.sendRedirect(authenticationUrl);
            return;
        }

        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}
