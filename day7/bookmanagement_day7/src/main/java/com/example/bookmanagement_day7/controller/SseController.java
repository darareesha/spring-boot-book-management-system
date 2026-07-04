package com.example.bookmanagement_day7.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/sse")
public class SseController {

    private final List<SseEmitter> emitters = new ArrayList<>();

    // UNICAST
    @GetMapping("/unicast")
    public SseEmitter unicast() {
        SseEmitter emitter = new SseEmitter();
        new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    emitter.send("Unicast update " + i);
                    Thread.sleep(2000);
                }
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        }).start();
        return emitter;
    }

    // BROADCAST - subscribe
    @GetMapping("/broadcast/subscribe")
    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        return emitter;
    }

    // BROADCAST - send to all
    @PostMapping("/broadcast/send")
    public String broadcast(@RequestParam String message) {
        List<SseEmitter> dead = new ArrayList<>();
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send("Broadcast: " + message);
            } catch (Exception e) {
                dead.add(emitter);
            }
        }
        emitters.removeAll(dead);
        return "Sent to " + emitters.size() + " clients";
    }

    // MULTICAST - send to a subset
    @PostMapping("/multicast/send")
    public String multicast(@RequestParam String message) {
        emitters.stream().limit(2).forEach(e -> {
            try { e.send("Multicast: " + message); } catch (Exception ignored) {}
        });
        return "Sent to a subset of clients";
    }
}