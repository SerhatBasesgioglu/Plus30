package com.aydakar.plus30backend.controller;

import com.aydakar.plus30backend.util.LCUConnector;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class LobbyController {
    private final LCUConnector connector;

    public LobbyController(LCUConnector connector){
        this.connector = connector;
        connector.connect();
        System.out.println("Injected");
    }

    @GetMapping("/userdata")
    public Mono<String> userData(){
        return connector.get("/lol-summoner/v1/current-summoner");
    }

    @GetMapping("/")
    public String hello(){
        return "Whats up!";
    }

}
