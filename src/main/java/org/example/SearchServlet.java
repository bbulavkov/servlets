package org.example;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/api/search")
public class SearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String searchParameter = req.getParameter("q");
        String redirectUrl = "https://www.google.com/search";

        if (searchParameter != null) {
            redirectUrl = redirectUrl + "?q=" + searchParameter;
        }
        resp.sendRedirect(redirectUrl);
    }
}
