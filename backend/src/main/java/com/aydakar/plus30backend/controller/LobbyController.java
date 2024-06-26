package com.aydakar.plus30backend.controller;

import com.aydakar.plus30backend.entity.Bot;
import com.aydakar.plus30backend.entity.CustomGame;
import com.aydakar.plus30backend.entity.LobbyRequest.IncomingLobbyRequest;
import com.aydakar.plus30backend.entity.Summoner;
import com.aydakar.plus30backend.service.LobbyService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("lobby")
public class LobbyController {
    private final LobbyService lobbyService;

    public LobbyController(LobbyService lobbyService) {
        this.lobbyService = lobbyService;
    }

    @GetMapping("custom-games")
    public List<CustomGame> getCustomGames() {
        return lobbyService.getCustomGames();
    }

    @PostMapping()
    public JsonNode createLobby(@RequestBody IncomingLobbyRequest req) {
        return lobbyService.createLobby(req);
    }

    @DeleteMapping()
    public JsonNode deleteLobby() {
        return lobbyService.deleteLobby();
    }

    @GetMapping()
    public JsonNode getLobby() {
        return lobbyService.getLobby();
    }

    @GetMapping("start-kicker")
    public void startAutoKicker() {
        lobbyService.startAutoKicker(1000);
    }

    @GetMapping("stop-kicker")
    public void stopVoidKicker() {
        lobbyService.stopAutoKicker();
    }

    @PostMapping("join")
    public JsonNode joinLobby(@RequestBody JsonNode inputs) {
        return lobbyService.joinLobby(inputs);
    }

    @GetMapping("members")
    public List<Summoner> members() {
        return lobbyService.getLobbyMembers();
    }

    @PostMapping("bot")
    public JsonNode addBot(@RequestBody Bot inputs) {
        return lobbyService.addBot(inputs);
    }

    @GetMapping("available-bots")
    public JsonNode availableBots() {
        return lobbyService.availableBots();
    }

    @PostMapping("invite")
    public JsonNode invite(@RequestBody JsonNode inputs) {
        return lobbyService.invite(inputs);
    }

    @GetMapping("start")
    public JsonNode startGame() {
        return lobbyService.start();
    }

    @PostMapping("reroll")
    public JsonNode reroll() {
        return lobbyService.reroll();
    }

    @PostMapping("{summonerId}/promote")
    public JsonNode promote(@PathVariable long summonerId) {
        return lobbyService.promote(summonerId);
    }

}
