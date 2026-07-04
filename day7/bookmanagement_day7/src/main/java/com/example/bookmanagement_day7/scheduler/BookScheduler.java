package com.example.bookmanagement_day7.scheduler;

import  com.example.bookmanagement_day7.repository.BookRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BookScheduler {

    private final BookRepository bookRepository;

    public BookScheduler(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    //(cron-based)
    @Scheduled(cron = "0 * * * * *")
    public void logTotalBooks() {
        long count = bookRepository.count();
        System.out.println("[Cron] Total Books in DB: " + count);
    }

    // fixed poller
    @Scheduled(fixedRate = 10000)
    public void pollForNewBooks() {
        long count = bookRepository.count();
        System.out.println("[Poller] Current book count: " + count);
    }
}