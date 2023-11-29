package com.aydakar.plus30backend.controller;

import com.aydakar.plus30backend.entity.Lobby;
import com.aydakar.plus30backend.entity.Summoner;
import com.aydakar.plus30backend.util.LCUConnector;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/lobby")
public class LobbyController {
    private final LCUConnector connector;
    private final ObjectMapper objectMapper;

    public LobbyController(LCUConnector connector, ObjectMapper objectMapper){
        this.connector = connector;
        this.objectMapper = objectMapper;
        connector.connect();
    }

    /*
    @GetMapping("all-lobbies")
    public Lobby[] allLobbies() throws JsonProcessingException {
        String data = connector.get("/lol-lobby/v1/custom-games").block();
        Lobby[] lobbies = objectMapper.readValue(data, Lobby[].class);
        return lobbies;
    }
    */



    //Change to post after frontend starts to work
    @GetMapping("create")
    public void createLobby(){
        String data ="""
                {"customGameLobby":{"configuration":
                    {"gameMode":"ARAM","gameMutator":"","gameServerRegion":"","mapId":12,"mutators":
                        {"id":1},
                    "spectatorPolicy":"AllAllowed","teamSize":5},"lobbyName":"z­ ­ ­ ­ ­ ­ ­ ­ ­zzz","lobbyPassword":"21"},
                "isCustom":true,"queueId":-1}
                """;
        connector.post("/lol-lobby/v2/lobby", data);
    }

    @GetMapping("auto-kicker")
    @Scheduled(fixedRate=1000)
        public void autoKicker() {
        try {
            System.out.println("Whats up!");
            JsonNode lobbyJson = connector.get("/lol-lobby/v2/lobby");
            JsonNode membersJson = lobbyJson.get("members");
            List<String> memberList = new ArrayList<>();
            for (JsonNode element : membersJson) {
                String temp = objectMapper.treeToValue(element.get("summonerId"), String.class);
                memberList.add(temp);
            }
            JsonNode blockedJson = connector.get("/lol-chat/v1/blocked-players");
            List<String> blockedList = new ArrayList<>();
            for (JsonNode element : blockedJson) {
                String temp = objectMapper.treeToValue(element.get("summonerId"), String.class);
                blockedList.add(temp);
            }
            for (String a : memberList) {
                for (String b : blockedList) {
                    if (a.equals(b)) {
                        String url = "lol-lobby/v2/lobby/members/" + a + "/kick";
                        connector.post(url);
                    }
                }
            }

            System.out.println(memberList + "\n" + blockedList);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
