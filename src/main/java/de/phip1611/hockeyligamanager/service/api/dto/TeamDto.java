/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.service.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.phip1611.hockeyligamanager.domain.Spieler;
import de.phip1611.hockeyligamanager.domain.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

public class TeamDto implements Comparable<TeamDto> {

    private UUID id;

    private String name;

    private List<SpielerDto> spieler = new ArrayList<>();

    public TeamDto(Team team) {
        this.id   = team.getId();
        this.name = team.getName();
        this.spieler.addAll(
                team.getSpielerList().stream()
                        .map(SpielerDto::new)
                        .collect(toList())
        );
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<SpielerDto> getSpieler() {
        return spieler;
    }

    @Override
    public int compareTo(TeamDto o) {
        return name.compareTo(o.name);
    }

    private class SpielerDto extends de.phip1611.hockeyligamanager.service.api.dto.SpielerDto {

        public SpielerDto(Spieler spieler) {
            super(spieler);
        }

        @Override
        @JsonIgnore
        public String getTeamName() {
            return null;
        }
    }

}
