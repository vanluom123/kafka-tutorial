package com.example.kafkatutorial.producer;

import com.example.kafkatutorial.event.BaseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class MessageProducer {
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Autowired
  private KafkaTemplate<Integer, String> kafkaTemplate;

  @Value("${kafka-tutorial.topic}")
  private String topic;

  public void send(BaseEvent event) throws JsonProcessingException {
    Integer key = Math.toIntExact(event.getId());
    String value = objectMapper.writeValueAsString(event);
    ProducerRecord<Integer, String> producerRecord = buildProducerRecord(key, value, topic);

    CompletableFuture<SendResult<Integer, String>> listenableFuture = kafkaTemplate.send(producerRecord);

    listenableFuture.handle((result, throwable) -> {
      if (throwable == null) {
        handleSuccess(key, value, result);
      } else {
        handleFailure(key, value, throwable);
      }
      return null;
    });
  }

  private ProducerRecord<Integer, String> buildProducerRecord(Integer key, String value, String topic) {
    List<Header> recordHeaders = List.of(new RecordHeader("event-source", "scanner".getBytes()));
    return new ProducerRecord<>(topic, null, key, value, recordHeaders);
  }

  private void handleSuccess(Integer key, String value, SendResult<Integer, String> result) {
    log.info("Message Sent SuccessFully for the key : {} and the value is {} , partition is {}", key, value, result.getRecordMetadata().partition());
  }

  private void handleFailure(Integer key, String value, Throwable ex) {
    log.error("Error Sending the Message and the exception is {}", ex.getMessage());
    try {
      throw ex;
    } catch (Throwable throwable) {
      log.error("Error in OnFailure: {}", throwable.getMessage());
    }
  }
}
