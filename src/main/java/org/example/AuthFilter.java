package org.example;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/api/*")
public class AuthFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req,
                            HttpServletResponse resp,
                            FilterChain chain) throws IOException, ServletException {

        String authHeaderValue = req.getHeader("Authorization");

        if ("111".equals(authHeaderValue)) {
            chain.doFilter(req, resp);
        } else {
            resp.setStatus(401);

            resp.setContentType("application/json");
            resp.getWriter().write("{\"Error\": \"Not authorized\"}");
            //Go to login page ->
            resp.getWriter().close();
        }
    }
}
