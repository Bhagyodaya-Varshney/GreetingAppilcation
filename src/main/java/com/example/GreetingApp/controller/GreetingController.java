package com.example.GreetingApp.controller;

import org.springframework.web.bind.annotation.*;
import java.util.HashMap;

@RestController
@RequestMapping("/")
public class GreetingController {

    @GetMapping()
    public Greeting getGreeting(){
        return new Greeting("Hii Spring Boot - GET REQUEST");
    }
    @PostMapping()
    public Greeting postGreeting(){
        return new Greeting("Hii Spring Boot - POST REQUEST");
    }
    @PutMapping
    public Greeting putGreeting() {
        return new Greeting("Hii Spring Boot - PUT REQUEST");
    }

    @DeleteMapping
    public Greeting deleteGreeting() {
        return new Greeting("Hii Spring Boot - DELETE REQUEST");
    }
}
