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

public class TeamForm {

    private UUID id;

    private String name;

    private List<UUID> spielerIdList = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public TeamForm setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public TeamForm setName(String name) {
        this.name = name;
        return this;
    }

    public List<UUID> getSpielerIdList() {
        return spielerIdList;
    }

    public TeamForm setSpielerIdList(List<UUID> spielerIdList) {
        this.spielerIdList = spielerIdList;
        return this;
    }

    public Team build() {
        return new Team(this);
    }
}
