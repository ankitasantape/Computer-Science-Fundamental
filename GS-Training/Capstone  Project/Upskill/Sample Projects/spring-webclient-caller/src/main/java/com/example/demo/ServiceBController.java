package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/serviceB")
public class ServiceBController {

    private final WebClient webClient;

    public ServiceBController(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping("/callA")
    public Mono<String> callA() {
        return webClient.get()
                .uri("/serviceA/greet")
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> "Service B got: " + response);
    }
}
