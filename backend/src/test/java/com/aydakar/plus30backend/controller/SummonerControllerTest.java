package com.aydakar.plus30backend.controller;

import com.aydakar.plus30backend.entity.Summoner;
import com.aydakar.plus30backend.service.SummonerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SummonerControllerTest {
    @Mock
    private SummonerService summonerService;

    @InjectMocks
    private SummonerController summonerController;

    @Test
    public void testCurrentSummoner(){
        Summoner expectedSummoner = new Summoner();
        when(summonerService.currentSummoner()).thenReturn(expectedSummoner);

        Summoner result = summonerController.currentSummoner();
        verify(summonerService).currentSummoner();
    }
}
