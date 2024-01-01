package com.aydakar.plus30backend.dto;

import lombok.Data;

@Data
public class CustomGameDTO {
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
}