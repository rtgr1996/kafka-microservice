package com.example.order_service.controller;

import com.example.base_domains.dto.Order;
import com.example.base_domains.dto.OrderEvent;
import com.example.order_service.kafka.OrderProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @PostMapping("/orders")
    public String placeOrder(@RequestBody Order order){
        order.setOrderId(UUID.randomUUID().toString());

        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setOrder(order);
        orderEvent.setMessage("order in pending state");
        orderEvent.setStatus("Pending");
        orderProducer.sendMessage(orderEvent);
        return "Order placed successfully";
    }
}