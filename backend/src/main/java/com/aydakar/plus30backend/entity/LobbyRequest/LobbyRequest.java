package com.aydakar.plus30backend.entity.LobbyRequest;

import lombok.Data;

@Data
public class LobbyRequest {
    private CustomGameLobby customGameLobby;
    private Boolean isCustom;
    private int queueId;
}


