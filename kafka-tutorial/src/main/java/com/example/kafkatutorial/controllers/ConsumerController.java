package com.example.kafkatutorial.controllers;

import com.example.kafkatutorial.listeners.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/consumer")
public class ConsumerController {

    private final MessageListener messageListener;

    @Autowired
    public ConsumerController(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    @GetMapping("/getMessage")
    public String getMessage() {
        return messageListener.getMsg();
    }
}
