package com.example.kafkatutorial.consumer;

import com.example.kafkatutorial.model.Student;
import com.example.kafkatutorial.repository.StudentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @Autowired
    private StudentRepository studentRepository;

    @KafkaListener(topics = {"littlecrochet"}, groupId = "group-id")
    private void saveStudent(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            var student = objectMapper.readValue(message, Student.class);
            studentRepository.save(student);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
