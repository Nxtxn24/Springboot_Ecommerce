package com.example.app.demo;

import org.springframework.web.bind.annotation.*;


@RestController
public class MyController {
    
    @GetMapping("/thing")
    public String thing() {
        return new String("This is a thing!");
    }

    @PostMapping("/postthing")
    public String postMethodName(@RequestBody String entity) {
        return entity.toUpperCase().substring(0, 5);
    }

    @PostMapping("/user")
    public String postMethodName(@RequestBody User user) {        
        return user.getName();
    }
    
    
}
