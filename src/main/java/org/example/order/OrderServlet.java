package org.example.order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@WebServlet(urlPatterns = "/api/orders/*")
public class OrderServlet extends HttpServlet {

    OrderService orderService;
    TemplateEngine engine;

    @Override
    public void init() {
        orderService = new OrderServiceImpl();

        engine = new TemplateEngine();

        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();

        resolver.setPrefix("/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setOrder(1);
        resolver.setCacheable(false);

        engine.addTemplateResolver(resolver);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = req.getRequestURI();

        if (uri.contains("delete")) {
            String orderId = req.getParameter("id");

            orderService.delete(orderId);

        } else {

            Order order = Order.builder()
                    .id(UUID.randomUUID().toString())
                    .user(req.getParameter("user"))
                    .sum(Integer.parseInt(req.getParameter("sum")))
                    .build();

            orderService.create(order);

        }
        resp.sendRedirect("/api/orders");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Order> orders = orderService.getAll();

        //engine mapping
        Context simpleContext = new Context(
                req.getLocale(),
                Map.of("orders", orders)
        );

        engine.process("orders", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}
