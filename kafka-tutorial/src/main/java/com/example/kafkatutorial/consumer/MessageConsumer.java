package com.example.kafkatutorial.consumer;

import com.example.kafkatutorial.model.Student;
import com.example.kafkatutorial.repository.StudentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;

@Component
public class MessageConsumer {

//    @Autowired
//    private StudentRepository studentRepository;

    @Autowired
    private KafkaConsumer<String, String> consumer;

//    @KafkaListener(topics = {"littlecrochet"}, groupId = "group-id")
//    private void saveStudent(String message) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            var student = objectMapper.readValue(message, Student.class);
//            studentRepository.save(student);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public void receiveMessage() {
        consumer.subscribe(Collections.singletonList("littlecrochet"));
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
        for (ConsumerRecord<String, String> record : records) {
            System.out.println("Received message: " + record.value());
        }
    }
}
