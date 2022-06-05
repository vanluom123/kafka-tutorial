package com.example.kafkatutorial.transformers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageTransformer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public MessageTransformer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String msg) {
        kafkaTemplate.send(topic, msg);
    }
}
