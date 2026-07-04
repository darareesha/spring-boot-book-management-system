package com.example.bookmanagement_day7.consumer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class BookEventConsumer {

    @KafkaListener(topics = "book-events", groupId = "book-group")
    public void listen(String message) {
        System.out.println("Received from Kafka: " + message);
    }
}