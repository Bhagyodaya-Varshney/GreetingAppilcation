package com.example.GreetingApp.controller;

import com.example.GreetingApp.serviceInterfaces.GreetingServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/")
public class GreetingController {

    @Autowired
    GreetingServiceInterface greetingServiceInterface;


    @GetMapping()
    public Greeting getGreeting(){
        System.out.println(1);
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
        return new Greeting(greetingServiceInterface.getSimpleGreeting());
    }
    @GetMapping("/greetUser")
    public Greeting getPersonalizedGreeting(@RequestParam(required = false) String firstName,
                                            @RequestParam(required = false) String lastName) {
        return new Greeting(greetingServiceInterface.getGreetingMessage(firstName, lastName));
    }

    @PostMapping("/save")
    public Greeting saveGreeting(@RequestParam String message) {
        return greetingServiceInterface.saveGreeting(message);
    }

    @GetMapping("/getById")
    public Greeting getGreetingById(@RequestParam Long id) {
        return greetingServiceInterface.getGreetingById(id);
    }
    @GetMapping("/all")
    public List<Greeting> getAllGreetings() {
        return greetingServiceInterface.getAllGreetings();
    }

    @PutMapping("/edit")
    public Greeting editGreeting(@RequestParam Long id, @RequestParam String message) {
        return greetingServiceInterface.editGreeting(id, message);
    }

    @DeleteMapping("/delete")
    public String deleteGreeting(@RequestParam Long id) {
        return greetingServiceInterface.deleteGreeting(id);
    }
}
