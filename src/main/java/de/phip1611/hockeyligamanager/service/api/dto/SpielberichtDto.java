/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.service.api.dto;

import de.phip1611.hockeyligamanager.domain.Spielbericht;

import java.util.UUID;

public class SpielberichtDto {

    private UUID id;

    private UUID team1Id;

    private UUID team2Id;

    private int goalsTeam1;

    private int goalsTeam2;

    public SpielberichtDto(Spielbericht spielbericht) {
        this.id      = spielbericht.getId();
        this.team1Id = spielbericht.getTeam1().getId();
        this.team2Id = spielbericht.getTeam1().getId();
        this.goalsTeam1 = spielbericht.getGoalsTeam1();
        this.goalsTeam2 = spielbericht.getGoalsTeam2();
    }

    public UUID getId() {
        return id;
    }

    public UUID getTeam1Id() {
        return team1Id;
    }

    public UUID getTeam2Id() {
        return team2Id;
    }

    public int getGoalsTeam1() {
        return goalsTeam1;
    }

    public int getGoalsTeam2() {
        return goalsTeam2;
    }

}
