package com.example.kafkatutorial.listeners;

import com.example.kafkatutorial.model.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Getter
public class MessageListener {

    private String msg;

    private Student student;

    @KafkaListener(topics = {"student"}, groupId = "group-id")
    private void getMessage(String message) {
        msg = message;
    }

    @KafkaListener(topics = {"test"}, groupId = "group-id")
    private void listen(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            student = objectMapper.readValue(message, Student.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
