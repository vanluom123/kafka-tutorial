package com.example.kafkatutorial.controller;

import com.example.kafkatutorial.consumer.MessageConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/consumer")
public class ConsumerController {

  @Autowired
  private MessageConsumer consumer;

  @GetMapping("/receive")
  public ResponseEntity<String> receive() {
    consumer.receiveMessage();
    return ResponseEntity.ok("Received");
  }
}
