package com.example.kafkatutorial.controllers;

import com.example.kafkatutorial.model.Student;
import com.example.kafkatutorial.transformers.MessageTransformer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/producer")
public class ProducerController {

    private final MessageTransformer transformer;

    @Autowired
    public ProducerController(MessageTransformer transformer) {
        this.transformer = transformer;
    }

    @PostMapping("/send-message")
    public void sendMessage(@RequestParam("message") String message) {
        transformer.sendMessage("student", message);
    }

    @PostMapping("/send-message-2")
    public void sendMessage(@RequestBody Student student) {
        ObjectMapper objectMapper = new ObjectMapper();
        String message;
        try {
            message = objectMapper.writeValueAsString(student);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        transformer.sendMessage("student", message);
    }


}
