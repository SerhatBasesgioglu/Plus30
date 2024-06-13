package com.aydakar.plus30backend.entity.LobbyRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LobbyRequest {
    private CustomGameLobby customGameLobby;
    private Boolean isCustom = true;
}


