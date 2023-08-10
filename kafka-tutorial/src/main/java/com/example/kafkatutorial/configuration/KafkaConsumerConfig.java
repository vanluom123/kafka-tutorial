package com.example.kafkatutorial.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.ExponentialBackOffWithMaxRetries;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableKafka
@Configuration
@Slf4j
public class KafkaConsumerConfig {
  public static final String RETRY = "RETRY";
  public static final String SUCCESS = "SUCCESS";
  public static final String DEAD = "DEAD";

  @Autowired
  private KafkaTemplate<Integer, String> kafkaTemplate;

  @Value("${kafka-tutorial.retry}")
  private String retryTopic;

  @Value("${kafka-tutorial.dlt}")
  private String deadLetterTopic;

  @Value("${kafka-tutorial.bootstrap-server}")
  private String server;

  @Bean
  public ConsumerFactory<String, String> consumerFactory() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "group-id");
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
    props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
    props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    return new DefaultKafkaConsumerFactory<>(props);
  }

  @Bean
  @ConditionalOnMissingBean(name = "kafkaListenerContainerFactory")
  public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, String>
        factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConcurrency(3);
    factory.setCommonErrorHandler(errorHandler());
    factory.setConsumerFactory(consumerFactory());
    return factory;
  }

  private DeadLetterPublishingRecoverer publishingRecover() {
    return new DeadLetterPublishingRecoverer(kafkaTemplate
        , (r, e) -> {
      log.error("Exception in publishingRecover : {} ", e.getMessage(), e);
      if (e.getCause() instanceof RecoverableDataAccessException) {
        return new TopicPartition(retryTopic, r.partition());
      } else {
        return new TopicPartition(deadLetterTopic, r.partition());
      }
    });
  }

  private DefaultErrorHandler errorHandler() {
    var exceptiopnToIgnorelist = List.of(
        IllegalArgumentException.class
    );

    ExponentialBackOffWithMaxRetries expBackOff = new ExponentialBackOffWithMaxRetries(2);
    expBackOff.setInitialInterval(1_000L);
    expBackOff.setMultiplier(2.0);
    expBackOff.setMaxInterval(2_000L);

    var defaultErrorHandler = new DefaultErrorHandler(
        publishingRecover(),
        expBackOff
    );

    exceptiopnToIgnorelist.forEach(defaultErrorHandler::addNotRetryableExceptions);

    defaultErrorHandler.setRetryListeners(
        (record, ex, deliveryAttempt) ->
            log.info("Failed Record in Retry Listener  exception : {} , deliveryAttempt : {} ", ex.getMessage(), deliveryAttempt)
    );

    return defaultErrorHandler;
  }

}