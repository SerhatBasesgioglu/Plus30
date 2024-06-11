package com.aydakar.plus30backend.entity.LobbyRequest;

import lombok.Data;

@Data
public class CustomGameLobby {
    private Configuration configuration;
    private String lobbyName;
    private String lobbyPassword;
}
