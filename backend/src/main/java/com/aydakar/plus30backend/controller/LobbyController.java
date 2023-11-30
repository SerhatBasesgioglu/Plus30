package com.aydakar.plus30backend.controller;

import com.aydakar.plus30backend.util.LCUConnector;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/lobby")
public class LobbyController {
    private final LCUConnector connector;
    private final ObjectMapper objectMapper;

    @Autowired
    public LobbyController(LCUConnector connector, ObjectMapper objectMapper){
        this.connector = connector;
        this.objectMapper = objectMapper;
        connector.connect();
    }

    /*
    @GetMapping("all-lobbies")
    public Lobby[] allLobbies() {
        JsonNode data = connector.get("/lol-lobby/v1/custom-games");
        Lobby[] lobbies = objectMapper.readValue(data, Lobby[].class);
        return lobbies;
    }
    */




    //Change to post after frontend starts to work
    @GetMapping("create")
    public String createLobby(){
        try {
            String data = """
                    {"customGameLobby":{"configuration":
                        {"gameMode":"ARAM","gameMutator":"","gameServerRegion":"","mapId":12,"mutators":
                            {"id":1},
                        "spectatorPolicy":"AllAllowed","teamSize":5},"lobbyName":"z­ ­ ­ ­ ­ ­ ­ ­ ­","lobbyPassword":"21"},
                    "isCustom":true,"queueId":-1}
                    """;
            connector.post("/lol-lobby/v2/lobby", data);
            return "Lobby created";
        }
        catch(Exception ignored){
            return "Lobby could not be created";
        }

    }

    @GetMapping("auto-kicker")
    @Scheduled(fixedRate=1000)
        public void autoKicker() {
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
                        connector.post(url);
                    }
                }
            }
        }
        catch(Exception ignored){
        }
    }
}
