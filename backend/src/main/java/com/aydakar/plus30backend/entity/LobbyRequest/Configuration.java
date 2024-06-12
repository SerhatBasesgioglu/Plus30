package com.aydakar.plus30backend.entity.LobbyRequest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Configuration {
    private String gameMode = "ARAM";
    private int mapId = 12;
    private Mutators mutators;
    private String spectatorPolicy = "AllAllowed";
    private int teamSize = 5;
}
