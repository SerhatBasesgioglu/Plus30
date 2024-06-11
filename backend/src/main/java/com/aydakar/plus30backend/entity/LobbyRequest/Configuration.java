package com.aydakar.plus30backend.entity.LobbyRequest;

import lombok.Data;

@Data
public class Configuration {
    private String gameMode;
    private String gameMutator;
    private String gameServerRegion;
    private int mapId;
    private Mutators mutators;
    private Boolean spectatorDelayEnabled;
    private String spectatorPolicy;
    private int teamSize;
}
