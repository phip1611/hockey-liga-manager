/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.service.impl;

import de.phip1611.hockeyligamanager.service.api.SpielberichtService;
import de.phip1611.hockeyligamanager.service.api.SpielerService;
import de.phip1611.hockeyligamanager.service.api.TeamService;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class InitDataService {

    private SpielerService spielerService;

    private TeamService teamService;

    private SpielberichtService spielberichtService;

    private InitDataHolder data;

    private static Logger LOGGER = Logger.getLogger("InitDataService");

    public InitDataService(SpielerService spielerService,
                           TeamService teamService,
                           SpielberichtService spielberichtService,
                           InitDataHolder data) {
        this.spielerService = spielerService;
        this.teamService = teamService;
        this.spielberichtService = spielberichtService;
        this.data = data;

        this.initSpieler(); // erst Spieler
        this.initTeams();   // dann ordnen die Teams sich die Spieler zu!!!
        this.initSpielberichte();
    }

    private void initSpielberichte() {
        if (this.data.getSpielberichte() == null) return;
        LOGGER.info("initSpielberichte()");
        this.data.getSpielberichte().forEach(spielberichtService::createOrUpdate);
    }

    private void initSpieler() {
        if (this.data.getSpieler() == null) return;
        LOGGER.info("initSpieler()");
        this.data.getSpieler().forEach(spielerService::createOrUpdate);
    }

    private void initTeams() {
        if (this.data.getTeams() == null) return;
        LOGGER.info("initTeams()");
        this.data.getTeams().forEach(teamService::createOrUpdate);
    }
}
