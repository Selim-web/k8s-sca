package com.palpashop.orderservice.ressource;

import com.palpashop.orderservice.dto.OrderRequest;
import com.palpashop.orderservice.model.Order;
import com.palpashop.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "order", produces = {"application/json"})
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        orderService.PlaceOrder(orderRequest);
        return "Order Placed Successfuly";
    }

    @GetMapping("/getAllOrders")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }
}
