package com.aydakar.plus30backend.service;

import com.aydakar.plus30backend.util.LCUConnector;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LobbyService {
    private final LCUConnector connector;
    private final ObjectMapper objectMapper;

    public LobbyService(LCUConnector connector, ObjectMapper objectMapper) {
        this.connector = connector;
        this.objectMapper = objectMapper;
        connector.connect();
    }

    public JsonNode allLobbies(){
        try {
            return connector.get("/lol-lobby/v1/custom-games");

        } catch (Exception e) {
            return objectMapper.valueToTree(e);
        }
    }

    public JsonNode createLobby(){
        return objectMapper.valueToTree("Not implemented yet");
    }

    public JsonNode autoKicker(){
        try {
            List<String> memberList = new ArrayList<>();
            List<String> blockedList = new ArrayList<>();

            JsonNode membersJson = connector.get("/lol-lobby/v2/lobby").get("members");
            JsonNode blockedJson = connector.get("/lol-chat/v1/blocked-players");

            for (JsonNode element : membersJson) {
                String temp = objectMapper.treeToValue(element.get("summonerId"), String.class);
                memberList.add(temp);
            }
            for (JsonNode element : blockedJson) {
                String temp = objectMapper.treeToValue(element.get("summonerId"), String.class);
                blockedList.add(temp);
            }
            for (String a : memberList) {
                for (String b : blockedList) {
                    if (a.equals(b)) {
                        String url = "lol-lobby/v2/lobby/members/" + a + "/kick";
                        return connector.post(url);
                    }
                }
            }
        }
        catch(Exception e){
            return objectMapper.valueToTree(e);
        }
        return null;
    }

}
