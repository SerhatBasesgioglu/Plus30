package com.aydakar.plus30backend.controller;

import com.aydakar.plus30backend.entity.Summoner;
import com.aydakar.plus30backend.service.SummonerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("")
public class SummonerController {
    private final SummonerService summonerService;

    public SummonerController(SummonerService theSummonerService) {
        summonerService = theSummonerService;
    }

    @GetMapping("/summoners")
    public List<Summoner> findAll() {
        return summonerService.findAll();
    }

    @GetMapping("/summoners/{id}")
    public Optional<Summoner> findById(@PathVariable String id) {
        Optional<Summoner> summoner = summonerService.findById(id);
        if (summoner.isEmpty()) {
            throw new RuntimeException("Summoner id cant be found" + id);
        }
        return summoner;
    }


    @PostMapping("/summoners")
    public Summoner addSummoner(@RequestBody Summoner summoner) {
        Summoner theSummoner = summonerService.save(summoner);
        return theSummoner;
    }

    @PostMapping("/summoners/{gameName}/{tagLine}")
    public Summoner addSummonerByTag(@PathVariable String gameName, @PathVariable String tagLine) {
        return summonerService.addSummonerByTag(gameName, tagLine);
    }

    @DeleteMapping("/summoners/{id}")
    public void deleteSummoner(@RequestBody String id) {
        summonerService.deleteById(id);
    }


    @GetMapping("/summoners/current-summoner")
    public Summoner currentSummoner() {
        return summonerService.getCurrentSummoner();
    }

//    @PutMapping("icon")
//    public JsonNode changeIcon(@RequestBody JsonNode data) {
//        return summonerService.changeIcon(data);
//    }
//
//    @GetMapping("profile")
//    public JsonNode getProfile() {
//        return summonerService.getProfile();
//    }
//
//    @PutMapping("status-message")
//    public JsonNode changeStatusMessage(@RequestBody JsonNode inputs) {
//        return summonerService.changeStatusMessage(inputs);
//    }


}
