package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/serviceB")
public class ServiceBController {

    private final GreetingService greetingService;

    public ServiceBController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping("/callA")
    public String callA() {
        return "From ServiceB: " + greetingService.greet();
    }
}
