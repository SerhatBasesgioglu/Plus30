package com.aydakar.plus30backend.entity;

import lombok.Data;

@Data
public class Summoner {
    private long accountId;
    private String displayName;
    private String gameName;
    private String internalName;
    private boolean nameChangeFlag;
    private int percentCompleteForNextLevel;
    private String privacy;
    private int profileIconId;
    private String puuid;

    private RerollPoints rerollPoints;

    private long summonerId;
    private int summonerLevel;
    private String tagLine;
    private boolean unnamed;

    private int xpSinceLastLevel;
    private int xpUntilNextLevel;

    @Data
    public static class RerollPoints {
        private int currentPoints;
        private int maxRolls;
        private int numberOfRolls;
        private int pointsCostToRoll;
        private int pointsToReroll;
    }
}