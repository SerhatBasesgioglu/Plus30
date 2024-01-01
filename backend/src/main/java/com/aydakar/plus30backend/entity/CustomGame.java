package com.aydakar.plus30backend.entity;

import lombok.Data;

@Data
public class CustomGame {
    private int id;
    private String gameType;
    private int mapId;
    private String ownerDisplayName;
    private String spectatorPolicy;
    private int filledSpectatorSlots;
    private int maxSpectatorSlots;
    private int filledPlayerSlots;
    private int maxPlayerSlots;
    private String lobbyName;
    private boolean hasPassword;
    private String passbackUrl;
    private String partyId;
}