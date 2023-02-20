package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.StringJoiner;

@WebServlet(urlPatterns = "/api/hello")
public class HelloServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Content-Type", "text/html; charset=utf-8");

        PrintWriter writer = resp.getWriter();


        writer.println("Отримали такі параметри " + getAllParamNames(req));
        writer.println("<br>");
        writer.println("Got all headers " + getAllHeaders(req));
        writer.close();
    }

    private String getAllParamNames(ServletRequest req) {
        Enumeration<String> parameterNames = req.getParameterNames();
        StringJoiner stringJoiner = new StringJoiner("<br>");

        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            stringJoiner.add(parameterName);
        }

        return stringJoiner.toString();
    }

    private String getAllHeaders(HttpServletRequest req) {
        Enumeration<String> headerNames = req.getHeaderNames();
        StringJoiner stringJoiner = new StringJoiner("<br>");

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            stringJoiner.add(headerName + "=" + req.getHeader(headerName));
        }
        return stringJoiner.toString();
    }
}
