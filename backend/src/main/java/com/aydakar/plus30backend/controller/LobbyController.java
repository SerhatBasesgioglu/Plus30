package com.aydakar.plus30backend.controller;

import com.aydakar.plus30backend.entity.Lobby;
import com.aydakar.plus30backend.entity.Summoner;
import com.aydakar.plus30backend.util.LCUConnector;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/lobby")
public class LobbyController {
    private final LCUConnector connector;
    private final ObjectMapper objectMapper;

    public LobbyController(LCUConnector connector, ObjectMapper objectMapper){
        this.connector = connector;
        this.objectMapper = objectMapper;
        connector.connect();
    }

    @GetMapping("all-lobbies")
    public Lobby[] allLobbies() throws JsonProcessingException {
        String data = connector.get("/lol-lobby/v1/custom-games").block();
        Lobby[] lobbies = objectMapper.readValue(data, Lobby[].class);
        return lobbies;
    }


    //Change to post after frontend starts to work
    @GetMapping("create")
    public Mono<String> createLobby(){
        String data ="""
                {"customGameLobby":{"configuration":
                    {"gameMode":"ARAM","gameMutator":"","gameServerRegion":"","mapId":12,"mutators":
                        {"id":1},
                    "spectatorPolicy":"AllAllowed","teamSize":5},"lobbyName":"­ ­ ­ ­ ­ ­ ­ ­ ­zzz","lobbyPassword":"21"},
                "isCustom":true,"queueId":-1}
                """;
        return connector.post("/lol-lobby/v2/lobby", data);


    }

    @GetMapping("auto-kicker")
        public Mono <String> autoKicker(){

        return null;
    }
}
