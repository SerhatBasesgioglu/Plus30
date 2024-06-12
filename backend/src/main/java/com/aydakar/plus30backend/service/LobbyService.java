package com.aydakar.plus30backend.service;

import com.aydakar.plus30backend.entity.Bot;
import com.aydakar.plus30backend.entity.CustomGame;
import com.aydakar.plus30backend.entity.LobbyRequest.*;
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
    private final ChatService chatService;
    private final TaskScheduler taskScheduler;
    private ScheduledFuture<?> scheduledFuture;

    public LobbyService(LCUConnector connector, ObjectMapper objectMapper,
                        SummonerService summonerService, ChatService chatService, TaskScheduler taskScheduler) {
        this.connector = connector;
        this.objectMapper = objectMapper;
        this.summonerService = summonerService;
        this.chatService = chatService;
        this.taskScheduler = taskScheduler;
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


    public JsonNode deleteLobby() {
        return connector.delete("/lol-lobby/v2/lobby", JsonNode.class);
    }


    public JsonNode getLobby() {
        try {
            JsonNode lobbyJson = connector.get("/lol-lobby/v2/lobby", JsonNode.class);
            return lobbyJson;
        } catch (Exception ignored) {
        }
        return null;
    }


    public LobbyRequest createLobby(IncomingLobbyRequest req) {
        Mutators mutators = new Mutators(1);
        Configuration config = new Configuration("ARAM", 12, mutators, "AllAllowed", 5);
        CustomGameLobby lobby = new CustomGameLobby(config, "Test", "");
        LobbyRequest request = new LobbyRequest(lobby, true);

        lobby.setLobbyName(req.getLobbyName().isEmpty() ? "Test" : req.getLobbyName());
        lobby.setLobbyPassword(req.getLobbyPassword());
        config.setMapId(req.getMapId());
        config.setSpectatorPolicy(req.getSpectatorPolicy());
        config.setTeamSize(req.getTeamSize());

        String gameMode = config.getMapId() == 12 ? "ARAM" : "CLASSIC";
        config.setGameMode(gameMode);
        return connector.post("/lol-lobby/v2/lobby", request, LobbyRequest.class);
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
            List<Summoner> members = getLobbyMembers();
            List<Summoner> spectators = getLobbySpectators();
            List<Summoner> blocked = chatService.getBlockedSummoners();
            List<Summoner> blackList = summonerService.findAll();
            Summoner currentSummoner = summonerService.getCurrentSummoner();

            List<Summoner> kickList = new ArrayList<>(blocked);
            List<Summoner> lobbyList = new ArrayList<>(members);
            lobbyList.addAll(spectators);
            kickList.addAll(blackList);

            for (Summoner member : lobbyList) {
                for (Summoner kick : kickList) {
                    long memberId = member.getSummonerId();
                    long kickId = kick.getSummonerId();
                    long currentSummonerId = currentSummoner.getSummonerId();
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


    public List<Summoner> getLobbyMembers() {
        List<Summoner> lobbyMembersRaw = Arrays.asList(connector.get("/lol-lobby/v2/lobby/members", Summoner[].class));
        List<Summoner> summoners = new ArrayList<>();
        for (Summoner summoner : lobbyMembersRaw) {
            summoners.add(summonerService.getSummonerByPuuid(summoner.getPuuid()));
        }
        return summoners;
    }


    public List<Summoner> getLobbySpectators() {
        try {
            JsonNode lobby = connector.get("/lol-lobby/v2/lobby", JsonNode.class);
            JsonNode spectatorsJson = lobby.get("gameConfig").get("customSpectators");
            return Arrays.asList(objectMapper.treeToValue(spectatorsJson, Summoner[].class));
        } catch (Exception e) {
            System.out.println("There is an error while getting lobby spectators");
            return null;
        }

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
