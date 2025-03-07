package com.example.GreetingApp.controller;


import com.example.GreetingApp.dto.LoginDTO;
import com.example.GreetingApp.dto.RegisterDTO;
import com.example.GreetingApp.serviceInterfaces.UserServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    UserServiceInterface userServiceInterface;


    //Ye Ek REST API banayenge jo user ko register karegi.
    @PostMapping("/register")
    public ResponseEntity<Map<String,String>> registeruser(@Valid @RequestBody RegisterDTO registerUser){
        return userServiceInterface.registerUser(registerUser);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@Valid@RequestBody LoginDTO loginUser){
        return userServiceInterface.loginUser(loginUser);
    }

    @PutMapping("/forgotPassword/{email}")
    public ResponseEntity<Map<String, String>> forgotPassword(@PathVariable String email, @RequestBody Map<String, String> request) {
        String newPassword = request.get("password");
        return userServiceInterface.forgotPassword(email, newPassword);
    }


    @PutMapping("/resetPassword/{email}")
    public ResponseEntity<Map<String, String>> resetPassword(
            @PathVariable String email,
            @RequestParam String currentPassword,
            @RequestParam String newPassword) {
        return userServiceInterface.resetPassword(email, currentPassword, newPassword);
    }

}
