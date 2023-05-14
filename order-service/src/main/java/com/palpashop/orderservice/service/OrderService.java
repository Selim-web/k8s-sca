package com.palpashop.orderservice.service;

import com.palpashop.orderservice.dto.OrderLineItemsDto;
import com.palpashop.orderservice.dto.OrderRequest;
import com.palpashop.orderservice.model.Order;
import com.palpashop.orderservice.model.OrderLineItems;
import com.palpashop.orderservice.repository.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepo orderRepo; ;

    public void PlaceOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                        .collect(Collectors.toList());

        order.setOrderLineItemsList(orderLineItems);
        orderRepo.add(order);

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto){
        OrderLineItems orderLineItems =  new OrderLineItems();
        orderLineItems.setCode(orderLineItemsDto.getCode());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        return orderLineItems;
    }

    @Cacheable(value="Order")
    public List<Order> getAllOrders(){
        return orderRepo.getAll();
    }
}
