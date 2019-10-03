/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.service.impl;

import de.phip1611.hockeyligamanager.service.api.SpielberichtService;
import de.phip1611.hockeyligamanager.service.api.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty("generate-testdata")
public class TestDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestDataService.class);

    private TeamService teamService;

    private SpielberichtService spielberichtService;

    private TestDataHolder data;

    public TestDataService(TeamService teamService,
                           SpielberichtService spielberichtService,
                           TestDataHolder data) {
        this.teamService = teamService;
        this.spielberichtService = spielberichtService;
        this.data = data;

        LOGGER.info("Starting Test Data Generation");

        this.initTeamsAndSpieler();
        this.initSpielberichte();
    }

    private void initSpielberichte() {
        if (this.data.getSpielberichte() == null) return;
        LOGGER.info("init Spielberichte");
        this.data.getSpielberichte().forEach(spielberichtService::createOrUpdate);
    }

    private void initTeamsAndSpieler() {
        if (this.data.getTeams() == null) return;
        LOGGER.info("init Teams and Spieler");
        this.data.getTeams().forEach(teamService::createOrUpdate);
    }
}
