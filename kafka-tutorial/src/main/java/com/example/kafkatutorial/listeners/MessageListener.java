package com.example.kafkatutorial.listeners;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    private String msg;

    public String getMsg() {
        return msg;
    }

    @KafkaListener(topics = {"student"}, groupId = "group-id")
    private void getMessage(String message) {
        msg = message;
    }
}
