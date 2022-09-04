package com.neshan.demo.Controllers;

import com.neshan.demo.Configurations.MessagingConfiguration;
import com.neshan.demo.Domain.OrderStatus;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ReceiverController {

    @RabbitListener(queues = MessagingConfiguration.QUEUE)
    public void consumeMessageFromQueue(OrderStatus orderStatus) {
        System.out.println("Message recieved from queue : " + orderStatus);
    }
}
