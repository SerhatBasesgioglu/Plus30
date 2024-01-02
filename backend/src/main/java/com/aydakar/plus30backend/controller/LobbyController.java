package com.aydakar.plus30backend.controller;

import com.aydakar.plus30backend.dto.CustomGameDTO;
import com.aydakar.plus30backend.entity.Bot;
import com.aydakar.plus30backend.entity.Summoner;
import com.aydakar.plus30backend.service.LobbyService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/lobby")
public class LobbyController {
    private final LobbyService lobbyService;

    public LobbyController(LobbyService lobbyService) {
        this.lobbyService = lobbyService;
    }

    @GetMapping("/custom-games")
    public List<CustomGameDTO> getCustomGames() {
        return lobbyService.getCustomGames();
    }

    @PostMapping("")
    public JsonNode create(@RequestBody JsonNode inputs) {
        return lobbyService.create(inputs);
    }

    @DeleteMapping("")
    public JsonNode delete() {
        return lobbyService.delete();
    }

    @GetMapping("")
    public JsonNode get() {
        return lobbyService.get();
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
    public List<Summoner> members() {
        return lobbyService.members();
    }

    @PostMapping("/bot")
    public JsonNode addBot(@RequestBody Bot inputs) {
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
