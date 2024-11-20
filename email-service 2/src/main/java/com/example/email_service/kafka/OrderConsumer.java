package com.example.email_service.kafka;

import com.example.base_domains.dto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    private static final Logger log = LoggerFactory.getLogger(OrderConsumer.class);

    private final JavaMailSender mailSender;

    public OrderConsumer(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @KafkaListener(topics = "${spring.kafka.topics}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumer(OrderEvent orderEvent){
        log.info("event received in email service " + orderEvent.toString());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("rtgr1996@gmail.com");
        message.setSubject("Order Confirmed");
        message.setText("Order Confirmed " + orderEvent.toString());
        message.setFrom("rohit.gaur0@gmail.com");
        mailSender.send(message);
    }

}
