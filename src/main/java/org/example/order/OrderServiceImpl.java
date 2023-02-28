package org.example.order;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderServiceImpl implements OrderService {

    List<Order> orders = new CopyOnWriteArrayList<>();

    @Override
    public List<Order> getAll() {
        return orders;
    }

    @Override
    public void create(Order order) {
        orders.add(order);
    }

    @Override
    public void delete(String id) {

        orders = orders.stream()
                .filter(o -> !o.getId().equals(id))
                .collect(Collectors.toList());

    }
}
