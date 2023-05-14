package com.palpashop.orderservice.repository;

import com.palpashop.orderservice.model.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderRepo {

    List<Order> orders = new ArrayList<>();

    public void add(Order order) {
        orders.add(order);
    }

    public List<Order> getAll() {
        System.out.println("Get All -> count : " + orders.size());
        return orders;
    }
}
