package com.aydakar.plus30backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LobbyController {
    @GetMapping("/userdata")
    public String userData(){
        return "Hello world!";
    }

    @GetMapping("/")
    public String hello(){
        return "Whats up!";
    }

}
