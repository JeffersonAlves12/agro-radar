package com.agro.radar.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailConsumer {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "${rabbitmq.queue.email}")
    public void receiveEmailMessage(String message) {
        try {
            JsonNode jsonNode = objectMapper.readTree(message);
            String subject = jsonNode.get("subject").asText();
            String recipient = jsonNode.get("recipient").asText();
            String content = jsonNode.get("content").asText();

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(recipient);
            mailMessage.setSubject(subject);
            mailMessage.setText(content);
            mailMessage.setFrom("jafelix495@gmail.com"); // Remetente

            mailSender.send(mailMessage);
            System.out.println("E-mail enviado para " + recipient);
        } catch (Exception e) {
            System.err.println("Erro ao processar mensagem de e-mail: " + e.getMessage());
        }
    }
}