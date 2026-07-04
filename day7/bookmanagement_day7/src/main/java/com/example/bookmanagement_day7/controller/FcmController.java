package com.example.bookmanagement_day7.controller;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FcmController {

    @PostMapping("/api/fcm/send")
    public String sendPushNotification(@RequestParam String deviceToken, @RequestParam String title, @RequestParam String body) {
        try {
            Message message = Message.builder()
                    .setToken(deviceToken)
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
            return "Sent successfully: " + response;
        } catch (Exception e) {
            return "Failed to send (expected if Firebase isn't fully configured): " + e.getMessage();
        }
    }
}