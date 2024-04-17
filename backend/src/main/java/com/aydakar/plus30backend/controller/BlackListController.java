package com.aydakar.plus30backend.controller;

import com.aydakar.plus30backend.entity.Summoner;
import com.aydakar.plus30backend.service.BlackListService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/blacklist")
public class BlackListController {
    private final BlackListService blackListService;

    public BlackListController(BlackListService blackListService) {
        this.blackListService = blackListService;
    }

    @GetMapping("/")
    public List<Summoner> getSummoners() {
        return blackListService.getSummoners();
    }

    @GetMapping("/{id}")
    public Optional<Summoner> getSummoner(@PathVariable String id) {
        return blackListService.getSummoner(id);
    }

    @PostMapping("/")
    public void addSummoner(@RequestBody Summoner summoner) {
        blackListService.addSummoner(summoner);
    }

    @DeleteMapping("/")
    public void deleteSummoner(@RequestBody Summoner summoner) {
        blackListService.deleteSummoner(summoner);
    }

    @DeleteMapping("/all")
    public void deleteAllSummoners() {
        blackListService.deleteAllSummoners();
    }
}
