package org.example.order;

import java.util.List;

public interface OrderService {

    List<Order> getAll();

    void create(Order order);

    void delete(String id);
}
