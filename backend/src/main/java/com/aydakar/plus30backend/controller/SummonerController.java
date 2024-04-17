package com.aydakar.plus30backend.controller;

import com.aydakar.plus30backend.entity.Summoner;
import com.aydakar.plus30backend.service.SummonerService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/summoner")
public class SummonerController {
    private final SummonerService summonerService;

    public SummonerController(SummonerService summonerService) {
        this.summonerService = summonerService;
    }

    @GetMapping("/")
    public Summoner currentSummoner() {
        return summonerService.currentSummoner();
    }

    @PutMapping("icon")
    public JsonNode changeIcon(@RequestBody JsonNode data) {
        return summonerService.changeIcon(data);
    }

    @GetMapping("profile")
    public JsonNode getProfile() {
        return summonerService.getProfile();
    }

    @PutMapping("status-message")
    public JsonNode changeStatusMessage(@RequestBody JsonNode inputs) {
        return summonerService.changeStatusMessage(inputs);
    }

}
