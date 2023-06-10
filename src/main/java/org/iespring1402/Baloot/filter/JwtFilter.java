package org.iespring1402.Baloot.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.iespring1402.Baloot.models.AuthToken;
import org.iespring1402.Baloot.models.Baloot;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class JwtFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

            final String authHeader = httpRequest.getHeader("Authorization");

            httpRequest.setAttribute("unauthorized", false);

            if (authHeader == null || !authHeader.startsWith("token ")) {
                httpRequest.setAttribute("unauthorized", true);
            } else {

                final String token = authHeader.substring(6);

                try {
                    AuthToken.validateToken(token, Baloot.SECRET_KEY, Baloot.ISSUER);
                } catch (Exception e) {
                    httpRequest.setAttribute("unauthorized", true);
                }
            }

            filterChain.doFilter(httpRequest, httpResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}