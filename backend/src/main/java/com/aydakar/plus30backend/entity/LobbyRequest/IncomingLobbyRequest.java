package com.aydakar.plus30backend.entity.LobbyRequest;

import lombok.Data;

@Data
public class IncomingLobbyRequest {
    private String lobbyName = "Test";
    private String lobbyPassword = "";
    private int mapId = 12;
    private int teamSize = 5;
    private String spectatorPolicy = "AllAllowed";
}
