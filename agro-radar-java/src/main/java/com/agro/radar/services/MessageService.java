package com.agro.radar.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.email}")
    private String emailExchange;

    @Value("${rabbitmq.routingkey.email}")
    private String emailRoutingKey;

    public void sendEmailMessage(String subject, String recipient, String content) {
        String message = String.format("{\"subject\":\"%s\", \"recipient\":\"%s\", \"content\":\"%s\"}",
                subject, recipient, content);
        rabbitTemplate.convertAndSend(emailExchange, emailRoutingKey, message);
    }
}