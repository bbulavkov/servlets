package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/api/toUp")
public class ToUpperCaseServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setHeader("Content-Type", "text/html; charset=utf-8");

        PrintWriter writer = resp.getWriter();


        String allParameters = getAllParameters(req);
        writer.println("All params "+allParameters);
        writer.println("<br>");
        writer.close();
    }

    private String getAllParameters(HttpServletRequest request) {
        String contentType = request.getHeader("content-type");

        if ("application/json".equals(contentType)) {
            return getAllParametersJson(request);
        } else {
            return getAllParametersUrlEncoded(request);
        }
    }

    private String getAllParametersJson(HttpServletRequest request) {
        try {
            String body = request
                    .getReader()
                    .lines()
                    .collect(Collectors.joining("\n"));

            Map<String, String> params = new Gson().fromJson(
                    body,
                    TypeToken.getParameterized(Map.class, String.class, String.class).getType()
            );

            return params
                    .entrySet()
                    .stream()
                    .map(it -> it.getKey() + " = " + it.getValue())
                    .collect(Collectors.joining("<br>"));
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return "";
    }

    private String getAllParametersUrlEncoded(HttpServletRequest request) {
        StringJoiner result = new StringJoiner("<br>");

        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            String parameterValues = Arrays.toString(request.getParameterValues(parameterName));

            result.add(parameterName + " = " + parameterValues);
        }


        return result.toString();
    }
}
