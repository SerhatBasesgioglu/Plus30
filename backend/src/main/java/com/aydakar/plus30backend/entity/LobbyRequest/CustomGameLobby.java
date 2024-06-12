package com.aydakar.plus30backend.entity.LobbyRequest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomGameLobby {
    private Configuration configuration;
    private String lobbyName = "Test";
    private String lobbyPassword = null;

}
