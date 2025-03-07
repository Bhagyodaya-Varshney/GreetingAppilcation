package com.example.GreetingApp.serviceInterfaces;


import com.example.GreetingApp.controller.Greeting;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface GreetingServiceInterface {
    String getSimpleGreeting();
    String getGreetingMessage(String firstName, String lastName);
    Greeting saveGreeting(String message);
    Greeting getGreetingById(Long id);
    List<Greeting> getAllGreetings();
    Greeting editGreeting(Long id, String message);
    String deleteGreeting(Long id);
}
