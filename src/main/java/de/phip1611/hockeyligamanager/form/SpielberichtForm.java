/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.form;

import de.phip1611.hockeyligamanager.domain.Spielbericht;
import de.phip1611.hockeyligamanager.domain.Team;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public class SpielberichtForm {

    private UUID id;

    private UUID team1Id;

    private UUID team2Id;

    private int goalsTeam1;

    private int goalsTeam2;

    public UUID getId() {
        return id;
    }

    public SpielberichtForm setId(UUID id) {
        this.id = id;
        return this;
    }

    public UUID getTeam1Id() {
        return team1Id;
    }

    public SpielberichtForm setTeam1Id(UUID team1Id) {
        this.team1Id = team1Id;
        return this;
    }

    public UUID getTeam2Id() {
        return team2Id;
    }

    public SpielberichtForm setTeam2Id(UUID team2Id) {
        this.team2Id = team2Id;
        return this;
    }

    public int getGoalsTeam1() {
        return goalsTeam1;
    }

    public SpielberichtForm setGoalsTeam1(int goalsTeam1) {
        this.goalsTeam1 = goalsTeam1;
        return this;
    }

    public int getGoalsTeam2() {
        return goalsTeam2;
    }

    public SpielberichtForm setGoalsTeam2(int goalsTeam2) {
        this.goalsTeam2 = goalsTeam2;
        return this;
    }

    public Spielbericht build(Function<UUID, Optional<Team>> teamFinder) {
        return new Spielbericht(this, teamFinder);
    }
}
