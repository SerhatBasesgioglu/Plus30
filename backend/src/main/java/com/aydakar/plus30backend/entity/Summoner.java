package com.aydakar.plus30backend.entity;

import lombok.Data;

@Data
public class Summoner {
    String summonerId;
    String accountId;
    String displayName;
    String internalName;
    int profileIconId;
    int summonerLevel;
    String puuid;
    String gameName;
    String tagLine;
}
