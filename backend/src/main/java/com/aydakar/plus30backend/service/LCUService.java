package com.aydakar.plus30backend.service;

import com.aydakar.plus30backend.util.CommandLine;
import com.aydakar.plus30backend.util.LCUConnector;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

@Service
public class LCUService {
    private final LCUConnector connector;

    public LCUService(LCUConnector connector) {
        this.connector = connector;
    }

    public static void resetClientUx() {
        CommandLine.resetClientUx();
    }

    public JsonNode getEndpointList() {
        return connector.get("/help", JsonNode.class);
    }

    public String getCredentials() {
        return connector.printInfo();
    }
}
