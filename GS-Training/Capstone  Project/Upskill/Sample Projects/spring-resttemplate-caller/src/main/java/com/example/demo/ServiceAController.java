package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/serviceA")
public class ServiceAController {

    @GetMapping("/greet")
    public String greet() {
        return "Hello from Service A";
    }
}
