package com.aydakar.plus30backend.service;

import com.aydakar.plus30backend.entity.Summoner;
import com.aydakar.plus30backend.util.LCUConnector;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ChatService {
    private final LCUConnector connector;
    private final SummonerService summonerService;

    public ChatService(LCUConnector connector, SummonerService summonerService) {
        this.connector = connector;
        this.summonerService = summonerService;
    }

    public List<Summoner> getBlockedSummoners() {
        List<Summoner> blockedSummonersRaw = Arrays.asList(connector.get("/lol-chat/v1/blocked-players", Summoner[].class));
        List<Summoner> blockedSummoners = new ArrayList<>();
        for (Summoner summoner : blockedSummonersRaw) {
            blockedSummoners.add(summonerService.getSummonerByPuuid(summoner.getPuuid()));
        }
        return blockedSummoners;
    }
}
