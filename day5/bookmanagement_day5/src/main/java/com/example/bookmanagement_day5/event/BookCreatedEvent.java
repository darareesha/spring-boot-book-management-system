package com.example.bookmanagement_day5.event;

import org.springframework.context.ApplicationEvent;

public class BookCreatedEvent extends ApplicationEvent {
    private final String title;

    public BookCreatedEvent(Object source, String title) {
        super(source);
        this.title = title;   // (this) is the service publishing the event
    }

    public String getTitle() {
        return title;
    }
}
