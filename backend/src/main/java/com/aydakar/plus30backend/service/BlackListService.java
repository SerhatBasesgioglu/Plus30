package com.aydakar.plus30backend.service;

import com.aydakar.plus30backend.entity.Summoner;
import com.aydakar.plus30backend.repository.BlackListRepository;
import com.aydakar.plus30backend.util.LCUConnector;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlackListService {
    private final LCUConnector connector;
    private final ObjectMapper objectMapper;
    private final BlackListRepository blackListRepository;

    public BlackListService(LCUConnector connector, ObjectMapper objectMapper, BlackListRepository blackListRepository) {
        this.connector = connector;
        this.objectMapper = objectMapper;
        this.blackListRepository = blackListRepository;
    }

    public List<Summoner> getSummoners() {
        return (List<Summoner>) blackListRepository.findAll();
    }

    public Optional<Summoner> getSummoner(String id) {
        return blackListRepository.findById(id);
    }

    public void addSummoner(Summoner summoner) {
        blackListRepository.save(summoner);
    }

    public void deleteSummoner(Summoner summoner) {
        blackListRepository.delete(summoner);
    }

    public void deleteAllSummoners() {
        blackListRepository.deleteAll();
    }
}
