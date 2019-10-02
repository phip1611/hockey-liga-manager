/* 
   Copyright 2019 Philipp Schuster
   
   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de 
   Twitter:  @phip1611 
 */
package de.phip1611.hockeyligamanager.service.api.dto;


import de.phip1611.hockeyligamanager.domain.Spieler;
import de.phip1611.hockeyligamanager.domain.Team;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

public class ExportTeamDto {

    private UUID id;

    private String name;

    private List<UUID> spielerList;

    public ExportTeamDto(Team team) {
        this.id = team.getId();
        this.name = team.getName();
        this.spielerList = team.getSpielerList().stream().map(Spieler::getId).collect(toList());
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<UUID> getSpielerList() {
        return spielerList;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExportTeamDto that = (ExportTeamDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(spielerList, that.spielerList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, spielerList);
    }

    @Override
    public String toString() {
        return "ExportTeamDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", spielerList=" + spielerList +
                '}';
    }
}
