package com.aydakar.plus30backend.controller;

import com.aydakar.plus30backend.util.LCUConnector;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/lobby")
public class LobbyController {
    private final LCUConnector connector;

    public LobbyController(LCUConnector connector){
        this.connector = connector;
        connector.connect();
        System.out.println("Injected");
    }

    @GetMapping("all-lobbies")
    public Mono<String> allLobbies(){
        return connector.get("/lol-lobby/v1/custom-games");
    }

    @GetMapping("create")
    public Mono<String> createLobby(){
        String data ="""
                {"customGameLobby":{"configuration":
                    {"gameMode":"ARAM","gameMutator":"","gameServerRegion":"","mapId":12,"mutators":
                        {"id":1},
                    "spectatorPolicy":"AllAllowed","teamSize":5},"lobbyName":"AyDaKaR oyunu","lobbyPassword":"21"},
                "isCustom":true,"queueId":-1}
                """;
        return connector.post("/lol-lobby/v2/lobby", data);
    }
}
