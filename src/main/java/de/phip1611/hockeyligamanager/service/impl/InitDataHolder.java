/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.service.impl;

import de.phip1611.hockeyligamanager.form.SpielberichtForm;
import de.phip1611.hockeyligamanager.form.TeamAndSpielerForm;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "initdata")
public class InitDataHolder {

    private List<TeamAndSpielerForm> teams;

    private List<SpielberichtForm> spielberichte;

    public List<TeamAndSpielerForm> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamAndSpielerForm> teams) {
        this.teams = teams;
    }

    public List<SpielberichtForm> getSpielberichte() {
        return spielberichte;
    }

    public void setSpielberichte(List<SpielberichtForm> spielberichte) {
        this.spielberichte = spielberichte;
    }
}
