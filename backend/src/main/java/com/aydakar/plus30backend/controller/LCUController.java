package com.aydakar.plus30backend.controller;

import com.aydakar.plus30backend.service.LCUService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class LCUController {
    @GetMapping("/reset")
    public void resetClientUx(){
        LCUService.resetClientUx();
    }
}
