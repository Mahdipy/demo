package com.neshan.demo.Controllers;

import com.neshan.demo.Configurations.MessagingConfiguration;
import com.neshan.demo.Domain.Order;
import com.neshan.demo.Domain.OrderStatus;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private RabbitTemplate template;

    @PostMapping("/{restaurantName}")
    public String bookOrder(@RequestBody Order order, @PathVariable String restaurantName) {
        order.setOrderId(UUID.randomUUID().toString());
        OrderStatus orderStatus = new OrderStatus(order, "PROCESS", "order placed succesfully in " + restaurantName);
        template.convertAndSend(MessagingConfiguration.EXCHANGE, MessagingConfiguration.ROUTING_KEY, orderStatus);
        return "Success !!";
    }
}
