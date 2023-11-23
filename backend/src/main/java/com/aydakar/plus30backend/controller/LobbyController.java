package com.aydakar.plus30backend.controller;

import com.aydakar.plus30backend.util.LCUConnector;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LobbyController {
    private LCUConnector connector;

    public LobbyController(LCUConnector connector){
        this.connector = connector;
        connector.connect();
        System.out.println("Injected");
    }

    @GetMapping("/userdata")
    public String userData(){
        connector.get("/lol-summoner/v1/current-summoner");
        return "Hello world!";
    }

    @GetMapping("/")
    public String hello(){
        return "Whats up!";
    }

}
