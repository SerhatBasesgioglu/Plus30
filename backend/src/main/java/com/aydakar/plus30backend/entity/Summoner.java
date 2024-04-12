package com.aydakar.plus30backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    int puuid;
    String gameName;
    String tagLine;
    boolean isLeader;
}
