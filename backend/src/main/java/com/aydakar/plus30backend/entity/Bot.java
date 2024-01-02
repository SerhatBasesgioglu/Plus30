package com.aydakar.plus30backend.entity;

import lombok.Data;

@Data
public class Bot {
    public enum Difficulty {
        EASY, MEDIUM, HARD, UBER;
    }

    private int championId;
    private Difficulty botDifficulty;
    private String teamId;
}
