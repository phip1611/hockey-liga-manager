/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.domain;

import de.phip1611.hockeyligamanager.form.SpielberichtForm;
import de.phip1611.hockeyligamanager.service.api.dto.SpielberichtDto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Entity
public class Spielbericht {

    @Id
    private UUID id;

    @JoinColumn
    @ManyToOne
    private Team team1;

    @JoinColumn
    @ManyToOne
    private Team team2;

    private int goalsTeam1;

    private int goalsTeam2;

    private Spielbericht() {
        /* hibernate default constructor */
    }

    public Spielbericht(SpielberichtForm form,
                        Function<UUID, Optional<Team>> teamFinder) {
        this.id = form.getId() == null ? UUID.randomUUID() : form.getId();
        this.update(form, teamFinder);
    }

    public Spielbericht update(SpielberichtForm form,
                               Function<UUID, Optional<Team>> teamFinder) {
        this.goalsTeam1 = form.getGoalsTeam1();
        this.goalsTeam2 = form.getGoalsTeam2();
        this.team1 = teamFinder.apply(form.getTeam1Id()).get();
        this.team2 = teamFinder.apply(form.getTeam2Id()).get();
        return this;
    }

    public UUID getId() {
        return id;
    }

    public Team getTeam1() {
        return team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public int getGoalsTeam1() {
        return goalsTeam1;
    }

    public int getGoalsTeam2() {
        return goalsTeam2;
    }

    public SpielberichtDto toDto() {
        return new SpielberichtDto(this);
    }
}
