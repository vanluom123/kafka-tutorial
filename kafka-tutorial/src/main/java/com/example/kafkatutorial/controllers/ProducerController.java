package com.example.kafkatutorial.controllers;

import com.example.kafkatutorial.model.Student;
import com.example.kafkatutorial.transformers.MessageTransformer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/producer")
public class ProducerController {

    @Autowired
    private MessageTransformer transformer;

    @PostMapping("/send")
    public void sendMessage(@RequestBody Student student) {
        ObjectMapper objectMapper = new ObjectMapper();
        String message;
        try {
            message = objectMapper.writeValueAsString(student);
            transformer.sendMessage("student", message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
