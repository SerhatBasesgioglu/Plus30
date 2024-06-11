package com.aydakar.plus30backend.controller;

import com.aydakar.plus30backend.entity.Bot;
import com.aydakar.plus30backend.entity.CustomGame;
import com.aydakar.plus30backend.entity.Summoner;
import com.aydakar.plus30backend.service.LobbyService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("")
public class LobbyController {
    private final LobbyService lobbyService;

    public LobbyController(LobbyService lobbyService) {
        this.lobbyService = lobbyService;
    }

    @GetMapping("/lobby/custom-games")
    public List<CustomGame> getCustomGames() {
        return lobbyService.getCustomGames();
    }

    @PostMapping("/lobby")
    public JsonNode createLobby(@RequestBody JsonNode inputs) {
        return lobbyService.create(inputs);
    }

    @DeleteMapping("/lobby")
    public JsonNode deleteLobby() {
        return lobbyService.delete();
    }

    @GetMapping("/lobby")
    public JsonNode getLobby() {
        return lobbyService.get();
    }

    @GetMapping("/lobby/start-kicker")
    public void startAutoKicker() {
        lobbyService.startAutoKicker(1000);
    }

    @GetMapping("/lobby/stop-kicker")
    public void stopVoidKicker() {
        lobbyService.stopAutoKicker();
    }

    @PostMapping("/lobby/join")
    public JsonNode joinLobby(@RequestBody JsonNode inputs) {
        return lobbyService.joinLobby(inputs);
    }

    @GetMapping("/lobby/members")
    public List<Summoner> members() {
        return lobbyService.members();
    }

    @PostMapping("/lobby/bot")
    public JsonNode addBot(@RequestBody Bot inputs) {
        return lobbyService.addBot(inputs);
    }

    @GetMapping("/lobby/available-bots")
    public JsonNode availableBots() {
        return lobbyService.availableBots();
    }

    @PostMapping("/lobby/invite")
    public JsonNode invite(@RequestBody JsonNode inputs) {
        return lobbyService.invite(inputs);
    }

    @GetMapping("/lobby/start")
    public JsonNode startGame() {
        return lobbyService.start();
    }

    @PostMapping("/lobby/reroll")
    public JsonNode reroll() {
        return lobbyService.reroll();
    }
}
