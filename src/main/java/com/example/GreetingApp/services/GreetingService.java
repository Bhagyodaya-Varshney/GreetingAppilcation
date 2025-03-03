package com.example.GreetingApp.services;


import org.springframework.stereotype.Service;

@Service
public class GreetingService {
    public String getSimpleGreeting() {
        return "Hello World";
    }
}
