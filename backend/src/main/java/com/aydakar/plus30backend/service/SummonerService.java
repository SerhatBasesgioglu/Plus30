package com.aydakar.plus30backend.service;

import com.aydakar.plus30backend.entity.Summoner;
import com.aydakar.plus30backend.repository.SummonerRepository;
import com.aydakar.plus30backend.util.LCUConnector;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SummonerService {
    private final LCUConnector connector;
    private final ObjectMapper objectMapper;
    private final SummonerRepository summonerRepository;

    @Autowired
    public SummonerService(LCUConnector connector, ObjectMapper objectMapper, SummonerRepository summonerDAO) {
        this.connector = connector;
        this.objectMapper = objectMapper;
        this.summonerRepository = summonerDAO;
        connector.connect();
    }

    public List<Summoner> findAll() {
        return (List<Summoner>) summonerRepository.findAll();
    }

    public Optional<Summoner> findById(String id) {
        return summonerRepository.findById(id);
    }

    @Transactional
    public Summoner save(Summoner summoner) {
        Optional<Summoner> dbData = summonerRepository.findById(summoner.getPuuid());
        if (dbData.isPresent()) {
            Summoner dbSummoner = dbData.get();
            dbSummoner.setSummonerLevel(summoner.getSummonerLevel());
            dbSummoner.setGameName(summoner.getGameName());
            dbSummoner.setTagLine(summoner.getTagLine());
            if (summoner.getIsBlacklisted() != null) {
                dbSummoner.setIsBlacklisted(summoner.getIsBlacklisted());
            }
            return summonerRepository.save(dbSummoner);
        }
        return summonerRepository.save(summoner);
    }


    @Transactional
    public void deleteById(String id) {
        summonerRepository.deleteById(id);
    }


    public Summoner getCurrentSummoner() {
        try {
            JsonNode summonerJson = connector.get("/lol-summoner/v1/current-summoner", JsonNode.class);
            return objectMapper.treeToValue(summonerJson, Summoner.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public Summoner addSummonerByTag(String gameName, String tagLine) {
        Summoner summoner = getSummonerByTag(gameName, tagLine);
        return summonerRepository.save(summoner);
    }

    public Summoner getSummonerByTag(String gameName, String tagLine) {
        try {
            String uri1 = String.format("/lol-summoner/v1/alias/lookup?gameName=%s&tagLine=%s", gameName, tagLine);
            String puuid = connector.get(uri1, JsonNode.class).get("puuid").asText();
            String uri2 = String.format("/lol-summoner/v2/summoners/puuid/%s", puuid);
            JsonNode summonerJson = connector.get(uri2, JsonNode.class);
            return objectMapper.treeToValue(summonerJson, Summoner.class);
        } catch (Exception e) {
            System.out.println("There is an error in getSummonerByTag method");
            return null;
        }
    }


//    public JsonNode changeIcon(JsonNode data) {
//        return connector.put("/lol-summoner/v1/current-summoner/icon", data);
//    }
//
//    public JsonNode getProfile() {
//        return connector.get("/lol-summoner/v1/current-summoner/summoner-profile");
//    }
//
//    public JsonNode changeStatusMessage(JsonNode inputs) {
//        return connector.put("/lol-chat/v1/me", inputs);
//    }

}
