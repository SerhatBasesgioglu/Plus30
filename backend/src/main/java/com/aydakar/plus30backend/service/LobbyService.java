package com.aydakar.plus30backend.service;

import com.aydakar.plus30backend.util.LCUConnector;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

    public JsonNode createLobby(JsonNode inputs) {
        JsonNode lobbyNameJson = inputs.get("lobbyName");
        JsonNode lobbyPasswordJson = inputs.get("lobbyPassword");
        JsonNode mapIdJson = inputs.get("mapId");
        JsonNode teamSizeJson = inputs.get("teamSize");
        JsonNode spectatorPolicyJson = inputs.get("spectatorPolicy");

        String lobbyName = (lobbyNameJson != null && lobbyNameJson.asText().length() > 2) ?
                lobbyNameJson.asText() : "Test";
        String lobbyPassword = (lobbyPasswordJson != null) ?
                lobbyPasswordJson.asText() : "";
        int mapId = (mapIdJson != null) ?
                mapIdJson.asInt() : 11;
        String gameMode = switch(mapId){
            case 11 -> "CLASSIC";
            case 12 -> "ARAM";
            default -> "ARAM";
        };
        int teamSize = (teamSizeJson != null) ?
                teamSizeJson.asInt() : 5;
        String spectatorPolicy = (spectatorPolicyJson != null) ?
                spectatorPolicyJson.asText() : "AllAllowed";


        ObjectNode mutatorsNode = objectMapper.createObjectNode();
        mutatorsNode.put("id", 1);

        ObjectNode configurationNode = objectMapper.createObjectNode();
        configurationNode.put("gameMode", gameMode);
        configurationNode.put("gameMutator", "");
        configurationNode.put("gameServerRegion", "");
        configurationNode.put("mapId", mapId);
        configurationNode.set("mutators", mutatorsNode);
        configurationNode.put("spectatorPolicy", spectatorPolicy);
        configurationNode.put("teamSize", teamSize);

        ObjectNode customGameLobbyNode = objectMapper.createObjectNode();
        customGameLobbyNode.set("configuration", configurationNode);
        customGameLobbyNode.put("lobbyName", lobbyName);
        customGameLobbyNode.put("lobbyPassword", lobbyPassword);

        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.set("customGameLobby", customGameLobbyNode);
        rootNode.put("isCustom", true);
        rootNode.put("queueId", -1);

        return connector.post("/lol-lobby/v2/lobby", rootNode);
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
