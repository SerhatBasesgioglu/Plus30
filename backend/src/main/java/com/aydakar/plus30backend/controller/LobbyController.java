package com.aydakar.plus30backend.controller;

import com.aydakar.plus30backend.service.LobbyService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/lobby")
public class LobbyController {
    private final LobbyService lobbyService;

    public LobbyController(LobbyService lobbyService) {
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

    @DeleteMapping("")
    public JsonNode delete() {
        return lobbyService.delete();
    }

    @GetMapping("/start-kicker")
    public void startAutoKicker() {
        lobbyService.startAutoKicker(1000);
    }

    @GetMapping("/stop-kicker")
    public void stopVoidKicker() {
        lobbyService.stopAutoKicker();
    }

    @PostMapping("/join")
    public JsonNode joinLobby(@RequestBody JsonNode inputs) {
        return lobbyService.joinLobby(inputs);
    }

    @GetMapping("/members")
    public JsonNode members() {
        return lobbyService.members();
    }

    @PostMapping("/add-bot")
    public JsonNode addBot(@RequestBody JsonNode inputs) {
        return lobbyService.addBot(inputs);
    }

    @GetMapping("/available-bots")
    public JsonNode availableBots() {
        return lobbyService.availableBots();
    }

    @PostMapping("/invite")
    public JsonNode invite(@RequestBody JsonNode inputs) {
        return lobbyService.invite(inputs);
    }
}
