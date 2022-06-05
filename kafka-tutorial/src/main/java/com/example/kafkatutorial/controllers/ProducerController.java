package com.example.kafkatutorial.controllers;

import com.example.kafkatutorial.transformers.MessageTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
