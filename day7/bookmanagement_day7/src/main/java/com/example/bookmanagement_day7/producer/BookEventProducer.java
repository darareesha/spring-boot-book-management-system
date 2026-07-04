//package com.example.bookmanagement_day7kafka.producer;
//package com.example.bookmanagement_day7_kakfa.producer;
package com.example.bookmanagement_day7.producer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public BookEventProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendBookCreatedEvent(String title) {
        kafkaTemplate.send("book-events", "New book added: " + title);
    }
}