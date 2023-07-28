package com.example.kafkatutorial.controllers;

import com.example.kafkatutorial.listeners.MessageListener;
import com.example.kafkatutorial.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/consumer")
public class ConsumerController {

    @Autowired
    private MessageListener messageListener;

    @GetMapping("/getStudent")
    public Student getStudent() {
        return messageListener.getStudent();
    }
}
