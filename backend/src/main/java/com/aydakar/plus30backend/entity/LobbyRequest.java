package com.aydakar.plus30backend.entity;

import lombok.Data;

@Data
public class LobbyRequest {
    private CustomGameLobby customGameLobby;
    private boolean isCustom;
    private int queueId;

    @Data
    private static class CustomGameLobby {
        private Configuration configuration;
        private String lobbyName;
        private String lobbyPassword;

        @Data
        private static class Configuration {
            private String gameMode;
            private String gameMutator;
            private String gameServerRegion;
            private int mapId;
            private Mutators mutators;
            private String spectatorPolicy;
            private int teamSize;

            @Data
            private static class Mutators {
                private int id;
            }
        }
    }
}

