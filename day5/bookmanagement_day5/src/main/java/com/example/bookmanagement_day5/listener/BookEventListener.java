package com.example.bookmanagement_day5.listener;

import com.example.bookmanagement_day5.event.BookCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BookEventListener {

    @EventListener
    public void handleBookCreated(BookCreatedEvent event) {
        System.out.println("[Event] New book created: " + event.getTitle());
    }
}