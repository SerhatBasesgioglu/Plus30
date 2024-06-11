package com.aydakar.plus30backend.service;

import com.aydakar.plus30backend.entity.Bot;
import com.aydakar.plus30backend.entity.CustomGame;
import com.aydakar.plus30backend.entity.Summoner;
import com.aydakar.plus30backend.util.LCUConnector;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

@Service
public class LobbyService {
    private final LCUConnector connector;
    private final ObjectMapper objectMapper;
    private final SummonerService summonerService;
    private final TaskScheduler taskScheduler;
    private ScheduledFuture<?> scheduledFuture;

    public LobbyService(LCUConnector connector, ObjectMapper objectMapper,
                        SummonerService summonerService, TaskScheduler taskScheduler) {
        this.connector = connector;
        this.objectMapper = objectMapper;
        this.summonerService = summonerService;
        this.taskScheduler = taskScheduler;
        connector.connect();
    }

    public List<CustomGame> getCustomGames() {
        try {
            connector.post("/lol-lobby/v1/custom-games/refresh", JsonNode.class);
            JsonNode customGameJson = connector.get("/lol-lobby/v1/custom-games", JsonNode.class);
            List<CustomGame> customGame = objectMapper.convertValue(customGameJson,
                    new TypeReference<List<CustomGame>>() {
                    });

            return customGame;
        } catch (Exception e) {
        }
        return null;
    }


    public JsonNode delete() {
        return connector.delete("/lol-lobby/v2/lobby", JsonNode.class);
    }

    public JsonNode get() {
        try {
            JsonNode lobbyJson = connector.get("/lol-lobby/v2/lobby", JsonNode.class);
            return lobbyJson;
        } catch (Exception ignored) {
        }
        return null;
    }

    public JsonNode create(JsonNode inputs) {
        return connector.post("/lol-lobby/v2/lobby", inputs, JsonNode.class);
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

    public JsonNode autoKicker() {
        try {
            JsonNode lobby = connector.get("/lol-lobby/v2/lobby", JsonNode.class);
            JsonNode membersJson = lobby.get("members");
            JsonNode spectatorsJson = lobby.get("gameConfig").get("customSpectators");
            JsonNode blockedJson = connector.get("/lol-chat/v1/blocked-players", JsonNode.class);
            JsonNode currentSummonerJson = connector.get("/lol-summoner/v1/current-summoner", JsonNode.class);

            List<Summoner> members = Arrays.asList(objectMapper.treeToValue(membersJson, Summoner[].class));
            List<Summoner> spectators = Arrays.asList(objectMapper.treeToValue(spectatorsJson, Summoner[].class));
            List<Summoner> blocked = Arrays.asList(objectMapper.treeToValue(blockedJson, Summoner[].class));
            List<Summoner> blackList = summonerService.findAll();
            Summoner currentSummoner = objectMapper.treeToValue(currentSummonerJson, Summoner.class);

            List<Summoner> kickList = new ArrayList<>(blocked);
            List<Summoner> lobbyList = new ArrayList<>(members);
            lobbyList.addAll(spectators);
            kickList.addAll(blackList);

            for (Summoner member : lobbyList) {
                for (Summoner kick : kickList) {
                    int memberId = member.getSummonerId();
                    int kickId = kick.getSummonerId();
                    int currentSummonerId = currentSummoner.getSummonerId();
                    if (memberId == kickId && kickId != currentSummonerId) {
                        String url = "lol-lobby/v2/lobby/members/" + kick.getSummonerId() + "/kick";
                        return connector.post(url, JsonNode.class);
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
        return connector.post("/lol-lobby/v1/custom-games/" + lobbyId + "/join", data, JsonNode.class);
    }

    public List<Summoner> members() {
        JsonNode lobbyMembersJson = connector.get("/lol-lobby/v2/lobby/members", JsonNode.class);
        List<Summoner> memberList = new ArrayList<>();

        try {
            if (lobbyMembersJson.isArray()) {
                for (JsonNode node : lobbyMembersJson) {
                    String puuid = node.get("puuid").asText();
                    String endpoint = "/lol-summoner/v2/summoners/puuid/" + puuid;
                    JsonNode summonerJson = connector.get(endpoint, JsonNode.class);
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
        return connector.post("/lol-lobby/v1/lobby/custom/bots", botDTOJson, JsonNode.class);
    }

    public JsonNode availableBots() {
        return connector.get("/lol-lobby/v2/lobby/custom/available-bots", JsonNode.class);
    }

    public JsonNode invite(JsonNode inputs) {
        return connector.post("/lol-lobby/v2/lobby/invitations", inputs, JsonNode.class);
    }

    public JsonNode start() {
        return connector.post("/lol-lobby/v1/lobby/custom/start-champ-select", JsonNode.class);
    }


    public JsonNode reroll() {
        return connector.post("/lol-champ-select-legacy/v1/session/my-selection/reroll", JsonNode.class);
    }
}
