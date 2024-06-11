package com.aydakar.plus30backend.controller;

import com.aydakar.plus30backend.service.LCUService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class LCUController {
    private final LCUService lcuService;

    public LCUController(LCUService lcuService) {
        this.lcuService = lcuService;
    }

    @GetMapping("/client/reset")
    public void resetClientUx() {
        LCUService.resetClientUx();
    }

    @GetMapping("/client/info")
    public String getInfo() {
        return lcuService.getCredentials();
    }

    @GetMapping("/client/help")
    public JsonNode getEndpointList() {
        return lcuService.getEndpointList();
    }
}
