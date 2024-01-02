package com.aydakar.plus30backend.service;

import com.aydakar.plus30backend.dto.CustomGameDTO;
import com.aydakar.plus30backend.entity.Bot;
import com.aydakar.plus30backend.entity.CustomGame;
import com.aydakar.plus30backend.entity.Summoner;
import com.aydakar.plus30backend.util.LCUConnector;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

@Service
public class LobbyService {
    private final LCUConnector connector;
    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;
    private final TaskScheduler taskScheduler;
    private ScheduledFuture<?> scheduledFuture;

    public LobbyService(LCUConnector connector, ObjectMapper objectMapper,
                        ModelMapper modelMapper, TaskScheduler taskScheduler) {
        this.connector = connector;
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
        this.taskScheduler = taskScheduler;
        connector.connect();
    }

    public List<CustomGameDTO> getCustomGames() {
        try {
            connector.post("/lol-lobby/v1/custom-games/refresh");
            JsonNode customGameJson = connector.get("/lol-lobby/v1/custom-games");
            List<CustomGame> customGame = objectMapper.convertValue(customGameJson,
                    new TypeReference<List<CustomGame>>() {
                    });
            List<CustomGameDTO> customGameDTO = modelMapper.map(customGame,
                    new TypeToken<List<CustomGameDTO>>() {
                    }.getType());
            return customGameDTO;
        } catch (Exception e) {
        }
        return null;
    }

    public JsonNode create(JsonNode inputs) {
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
        String gameMode = switch (mapId) {
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

    public JsonNode delete() {
        return connector.delete("/lol-lobby/v2/lobby");
    }

    public JsonNode get() {
        try {
            JsonNode lobbyJson = connector.get("/lol-lobby/v2/lobby");
            return lobbyJson;
        } catch (Exception ignored) {
        }
        return null;
    }

    public void startAutoKicker(long rate) {
        if (scheduledFuture == null || scheduledFuture.isCancelled()) {
            scheduledFuture = taskScheduler.scheduleAtFixedRate(this::autoKicker, rate);
        }
    }

    public void stopAutoKicker() {
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
        }
    }

    private JsonNode autoKicker() {
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
        } catch (Exception e) {
            return objectMapper.valueToTree(e);
        }
        return null;
    }

    public JsonNode joinLobby(JsonNode inputs) {
        JsonNode lobbyIdJson = inputs.get("lobbyId");
        String lobbyId = lobbyIdJson.asText();
        ObjectNode data = objectMapper.createObjectNode();
        //data.put("password", "");
        //data.put("asSpectator", true);
        return connector.post("/lol-lobby/v1/custom-games/" + lobbyId + "/join", data);
    }

    public List<Summoner> members() {
        JsonNode lobbyMembersJson = connector.get("/lol-lobby/v2/lobby/members");
        List<Summoner> memberList = new ArrayList<>();

        try {
            if (lobbyMembersJson.isArray()) {
                for (JsonNode node : lobbyMembersJson) {
                    String puuid = node.get("puuid").asText();
                    String endpoint = "/lol-summoner/v2/summoners/puuid/" + puuid;
                    JsonNode summonerJson = connector.get(endpoint);
                    Summoner summoner = objectMapper.treeToValue(summonerJson, Summoner.class);
                    memberList.add(summoner);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return memberList;
    }

    public JsonNode addBot(Bot bot) {
        JsonNode botDTOJson = objectMapper.valueToTree(bot);
        return connector.post("/lol-lobby/v1/lobby/custom/bots", botDTOJson);
    }

    public JsonNode availableBots() {
        return connector.get("/lol-lobby/v2/lobby/custom/available-bots");
    }

    public JsonNode invite(JsonNode inputs) {
        return connector.post("/lol-lobby/v2/lobby/invitations", inputs);
    }
}
