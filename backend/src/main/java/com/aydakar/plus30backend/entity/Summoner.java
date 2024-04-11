package com.aydakar.plus30backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
public class Summoner {
    String summonerId;
    String accountId;
    String displayName;
    String internalName;
    int profileIconId;
    int summonerLevel;
    @Id
    String puuid;
    String gameName;
    String tagLine;
    boolean isLeader;
}
