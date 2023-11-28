package com.aydakar.plus30backend.entity;

import lombok.Data;

@Data
public class Lobby {
    private int filledPlayerSlots;
    private int filledSpectatorSlots;
    private String gameType;
    private boolean hasPassword;
    private long id;
    private String lobbyName;
    private int mapId;
    private int maxPlayerSlots;
    private int maxSpectatorSlots;
    private String ownerDisplayName;
    private String partyId;
    private String passbackUrl;
    private String spectatorPolicy;
}