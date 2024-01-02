package com.aydakar.plus30backend.dao;

import com.aydakar.plus30backend.entity.Summoner;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class SummonerDAO {
    private EntityManager entityManager;

    public SummonerDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Summoner> findAll() {
        TypedQuery<Summoner> theQuery = entityManager.createQuery("from Summoner", Summoner.class);
        List<Summoner> summoners = theQuery.getResultList();
        return summoners;
    }

    @Transactional
    public void save(Summoner summoner) {
        entityManager.persist(summoner);
    }
}
