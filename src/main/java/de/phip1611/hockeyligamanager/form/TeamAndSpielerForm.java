/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.form;

import de.phip1611.hockeyligamanager.domain.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeamAndSpielerForm {

    private UUID id;

    private String name;

    private List<SpielerForm> spieler = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public TeamAndSpielerForm setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public TeamAndSpielerForm setName(String name) {
        this.name = name;
        return this;
    }

    public List<SpielerForm> getSpieler() {
        return spieler;
    }

    public void setSpieler(List<SpielerForm> spieler) {
        this.spieler = spieler;
    }

    public Team build() {
        return new Team(this);
    }
}
