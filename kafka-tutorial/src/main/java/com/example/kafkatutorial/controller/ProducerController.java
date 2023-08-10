package com.example.kafkatutorial.controller;

import com.example.kafkatutorial.event.StudentEvent;
import com.example.kafkatutorial.producer.MessageProducer;
import com.example.kafkatutorial.request.StudentRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/producer")
public class ProducerController {

    @Autowired
    private MessageProducer messageProducer;

    @PostMapping("/send-message")
    public void sendMessage(@RequestBody StudentRequest request) throws JsonProcessingException {
        StudentEvent event = new StudentEvent();
        event.setId(request.getId());
        event.setFirstName(request.getFirstName());
        event.setLastName(request.getLastName());
        messageProducer.send(event);
    }
}
