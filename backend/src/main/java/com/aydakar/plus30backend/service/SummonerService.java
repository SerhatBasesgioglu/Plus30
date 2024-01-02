package com.aydakar.plus30backend.service;

import com.aydakar.plus30backend.dao.SummonerDAO;
import com.aydakar.plus30backend.entity.Summoner;
import com.aydakar.plus30backend.util.LCUConnector;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SummonerService {
    private final LCUConnector connector;
    private final ObjectMapper objectMapper;
    private final SummonerDAO summonerDAO;

    public SummonerService(LCUConnector connector, ObjectMapper objectMapper, SummonerDAO summonerDAO) {
        this.connector = connector;
        this.objectMapper = objectMapper;
        this.summonerDAO = summonerDAO;
        connector.connect();
    }

    public JsonNode currentSummoner() {
        try {
            JsonNode result = connector.get("/lol-summoner/v1/current-summoner");
            return result;
        } catch (Exception e) {
            return objectMapper.valueToTree(e);
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

    public List<Summoner> getBlockedSummoners() {
        return summonerDAO.findAll();
    }

    public void addSummonerToBlockList(Summoner summoner) {
        summonerDAO.save(summoner);
    }
}
