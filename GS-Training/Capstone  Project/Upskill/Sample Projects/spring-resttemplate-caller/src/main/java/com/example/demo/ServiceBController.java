package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/serviceB")
public class ServiceBController {

    private final RestTemplate restTemplate;

    public ServiceBController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/callA")
    public String callA() {
        String response = restTemplate.getForObject("http://localhost:8080/serviceA/greet", String.class);
        return "Service B got: " + response;
    }
}
