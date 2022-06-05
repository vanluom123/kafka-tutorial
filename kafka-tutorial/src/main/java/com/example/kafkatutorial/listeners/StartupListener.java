//package com.example.kafkatutorial.listeners;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
//@Component
//public class StartupListener implements ApplicationRunner {
//    private final KafkaTemplate<String, String> kafkaTemplate;
//
//    @Autowired
//    public StartupListener(KafkaTemplate<String, String> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }
//
//    public void sendMessage(String msg) {
//        kafkaTemplate.send("test", msg);
//    }
//
//    @KafkaListener(topics = "test", groupId = "group-id")
//    public void listen(String message) {
//        System.out.println("Received Message in group - group-id: " + message);
//    }
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        for (int i = 0; i < 1000; i++) {
//            sendMessage("Now: " + new Date());
//            Thread.sleep(2000);
//        }
//    }
//}
