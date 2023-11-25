package com.aydakar.plus30backend.controller;


import com.aydakar.plus30backend.util.LCUConnector;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
public class UserController {
    private final LCUConnector connector;

    public UserController(LCUConnector connector){
        this.connector = connector;
        connector.connect();
    }

    @GetMapping("/")
    public Mono<String> currentUser(){
        return connector.get("/lol-summoner/v1/current-summoner");
    }
}
