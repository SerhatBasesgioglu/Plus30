package com.aydakar.plus30backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Summoner {
    @Id
    String puuid;
    String gameName;
    String tagLine;
    int summonerLevel;
}
