package com.example.bookmanagement_day7.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class BookCommentController {

    @MessageMapping("/book/comment")
    @SendTo("/topic/book-comments")
    public String handleComment(String comment) {
        return "Comment: " + comment;
    }
}