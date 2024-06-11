package com.aydakar.plus30backend.repository;

import com.aydakar.plus30backend.entity.Summoner;
import org.springframework.data.repository.CrudRepository;

public interface SummonerRepository extends CrudRepository<Summoner, String> {
}
