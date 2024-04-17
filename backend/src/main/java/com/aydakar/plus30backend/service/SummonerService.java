package com.aydakar.plus30backend.service;

import com.aydakar.plus30backend.entity.Summoner;
import com.aydakar.plus30backend.repository.SummonerRepository;
import com.aydakar.plus30backend.util.LCUConnector;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class SummonerService {
    private final LCUConnector connector;
    private final ObjectMapper objectMapper;
    private final SummonerRepository summonerRepository;

    public SummonerService(LCUConnector connector, ObjectMapper objectMapper, SummonerRepository summonerRepository) {
        this.connector = connector;
        this.objectMapper = objectMapper;
        this.summonerRepository = summonerRepository;
        connector.connect();
    }

    public Summoner currentSummoner() {
        try {
            JsonNode summonerJson = connector.get("/lol-summoner/v1/current-summoner");
            Summoner summoner = objectMapper.treeToValue(summonerJson, Summoner.class);
            return summoner;
        } catch (Exception e) {
            return null;
        }
    }

    public JsonNode changeIcon(JsonNode data) {
        return connector.put("/lol-summoner/v1/current-summoner/icon", data);
    }

    public JsonNode getProfile() {
        return connector.get("/lol-summoner/v1/current-summoner/summoner-profile");
    }

    public JsonNode changeStatusMessage(JsonNode inputs) {
        return connector.put("/lol-chat/v1/me", inputs);
    }


}
