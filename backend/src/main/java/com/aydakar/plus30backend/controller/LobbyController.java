package com.aydakar.plus30backend.controller;

import com.aydakar.plus30backend.service.LobbyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/lobby")
public class LobbyController {
    private final LobbyService lobbyService;

    public LobbyController(LobbyService lobbyService){
        this.lobbyService = lobbyService;
    }

    @GetMapping("/all-lobbies")
    public JsonNode allLobbies() {
        return lobbyService.allLobbies();
    }

    @PostMapping("/create")
    public JsonNode createLobby(@RequestBody JsonNode inputs) {
        return lobbyService.createLobby(inputs);
    }

    @GetMapping("/start-kicker")
    public void startAutoKicker() {
        lobbyService.startAutoKicker(1000);
    }
    @GetMapping("/stop-kicker")
    public void stopVoidKicker(){
        lobbyService.stopAutoKicker();
    }

    @PostMapping("/join")
    public JsonNode joinLobby(@RequestBody JsonNode inputs){
        return lobbyService.joinLobby(inputs);
    }

    @GetMapping("/members")
    public JsonNode members(){
        return lobbyService.members();
    }
}
