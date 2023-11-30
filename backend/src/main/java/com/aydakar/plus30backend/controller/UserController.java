package com.aydakar.plus30backend.controller;


import com.aydakar.plus30backend.entity.Summoner;
import com.aydakar.plus30backend.util.LCUConnector;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
public class UserController {
    private final LCUConnector connector;
    private final ObjectMapper objectMapper;

    @Autowired
    public UserController(LCUConnector connector, ObjectMapper objectMapper){
        this.connector = connector;
        this.objectMapper = objectMapper;
        connector.connect();
    }

    @GetMapping("/")
    public Summoner currentUser() {
        Summoner summoner = new Summoner();
        try {
            JsonNode data = connector.get("/lol-summoner/v1/current-summoner");
            summoner = objectMapper.treeToValue(data, Summoner.class);
        }
        catch (Exception ignored){

        }
        return summoner;
    }

}
