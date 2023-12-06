package com.aydakar.plus30backend.service;

import com.aydakar.plus30backend.util.LCUConnector;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class SummonerService {
    private final LCUConnector connector;
    private final ObjectMapper objectMapper;

    public SummonerService(LCUConnector connector, ObjectMapper objectMapper){
        this.connector = connector;
        this.objectMapper = objectMapper;
        connector.connect();
    }

    public JsonNode currentSummoner(){
        try {
            JsonNode result = connector.get("/lol-summoner/v1/current-summoner");
            return result;
        }
        catch (Exception e){
            return objectMapper.valueToTree(e);
        }
    }

    public JsonNode changeIcon(JsonNode data){
        return connector.put("/lol-summoner/v1/current-summoner/icon", data);
    }
}
