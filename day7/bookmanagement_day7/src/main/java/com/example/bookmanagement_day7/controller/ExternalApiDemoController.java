package com.example.bookmanagement_day7.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class ExternalApiDemoController {

    @GetMapping("/api/demo/resttemplate")
    public String callWithRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        // Blocking call - this thread waits here until the response arrives
        return restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts/1", String.class);
    }

    @GetMapping("/api/demo/webclient")
    public String callWithWebClient() {
        WebClient webClient = WebClient.create();
        // Non-blocking call - .block() is used here only to keep this demo simple and return a value immediately
        return webClient.get()
                .uri("https://jsonplaceholder.typicode.com/posts/1")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}