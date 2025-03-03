package com.example.GreetingApp.controller;

import com.example.GreetingApp.services.GreetingService;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;

@RestController
@RequestMapping("/")
public class GreetingController {

    private final GreetingService greetingService;

    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

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

    @GetMapping("/greet")
    public Greeting sayGreeting(){
        return new Greeting(greetingService.getSimpleGreeting());
    }
    @GetMapping("/greetUser")
    public Greeting getPersonalizedGreeting(@RequestParam(required = false) String firstName,
                                            @RequestParam(required = false) String lastName) {
        return new Greeting(greetingService.getGreetingMessage(firstName, lastName));
    }
}
