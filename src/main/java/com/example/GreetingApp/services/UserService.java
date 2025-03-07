package com.example.GreetingApp.services;


import com.example.GreetingApp.dto.LoginDTO;
import com.example.GreetingApp.dto.RegisterDTO;
import com.example.GreetingApp.model.User;
import com.example.GreetingApp.repository.UserRepository;
import com.example.GreetingApp.serviceInterfaces.UserServiceInterface;
import com.example.GreetingApp.utility.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


//Yeh service user ko database me save karegi.
@Service
public class UserService implements UserServiceInterface {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    EmailService emailService;
    @Autowired
    JwtUtility jwtUtility;

    @Override
    public ResponseEntity<Map<String,String>> registerUser(RegisterDTO registerDTO){
        Map<String,String> res = new HashMap<>();
        if(existsByEmail(registerDTO.getEmail())){
            res.put("error","User Already Exists");
            return ResponseEntity.ok(res);
        }

        User user = new User();
        user.setFullName(registerDTO.getFullName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());

        String token = jwtUtility.generateToken(user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        String subject = "Welcome to Our Platform!";
        String body = "Hello " + user.getFullName() + ",\n\nYour account has been successfully created!";
        emailService.sendEmail(user.getEmail(), subject, body);

        res.put("message","User Registered Successfully");
        return ResponseEntity.ok(res);
    }

    @Override
    public ResponseEntity<Map<String,String>> loginUser(LoginDTO loginDTO){
        Map<String,String> res = new HashMap<>();
        Optional<User> userExists = getUserByEmail(loginDTO.getEmail());
        if(userExists.isPresent()){
            User user = userExists.get();
            if(matchPassword(loginDTO.getPassword(),user.getPassword())){
                String token = jwtUtility.generateToken(user.getEmail());
                String subject = "Welcome Back to Our Platform!";
                String body = "Hello " + user.getFullName() + ",\n\nYour account has been successfully Logged In! and Your Token is: "+token;
                emailService.sendEmail(user.getEmail(), subject, body);
                res.put("message","User Logged In Successfully"+token);
                return ResponseEntity.ok(res);
            }
            else{
                res.put("error","Invalid Crendentials");
                return ResponseEntity.ok(res);
            }
        }
        else{
            res.put("error","User Not Found");
            return ResponseEntity.ok(res);
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean matchPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public ResponseEntity<Map<String, String>> forgotPassword(String email, String newPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        Map<String, String> response = new HashMap<>();

        if (userOptional.isEmpty()) {
            response.put("message", "Sorry! We cannot find the user email: " + email);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        User user = userOptional.get();
        user.setPassword(passwordEncoder.encode(newPassword)); // Hash the new password
        userRepository.save(user);

        emailService.sendEmail(email, "Password Reset", "Your password has been changed successfully!");

        response.put("message", "Password has been changed successfully!");
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Map<String, String>> resetPassword(String email, String currentPassword, String newPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        Map<String, String> response = new HashMap<>();

        if (userOptional.isEmpty()) {
            response.put("message", "User not found with email: " + email);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            response.put("message", "Current password is incorrect!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        user.setPassword(passwordEncoder.encode(newPassword)); // Hash the new password
        userRepository.save(user);

        response.put("message", "Password reset successfully!");
        return ResponseEntity.ok(response);
    }
}
