/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.service.api.dto;

import java.util.List;
import java.util.Objects;

public class ExportDto {

    private List<ExportSpielerDto> spieler;

    private List<ExportSpielberichtDto> spielberichte;

    private List<ExportSpielerStrafEreignisDto> strafEreignisse;

    private List<ExportSpielerTorEreignisDto> torEreignisse;

    private List<ExportTeamDto> teams;

    // for objectmapper
    public ExportDto() {
    }

    public ExportDto(List<ExportSpielerDto> spieler,
                     List<ExportSpielberichtDto> spielberichte,
                     List<ExportSpielerStrafEreignisDto> strafEreignisse,
                     List<ExportSpielerTorEreignisDto> torEreignisse,
                     List<ExportTeamDto> teams) {
        this.spieler = spieler;
        this.spielberichte = spielberichte;
        this.strafEreignisse = strafEreignisse;
        this.torEreignisse = torEreignisse;
        this.teams = teams;
    }

    public List<ExportSpielerDto> getSpieler() {
        return spieler;
    }

    public List<ExportSpielberichtDto> getSpielberichte() {
        return spielberichte;
    }

    public List<ExportSpielerStrafEreignisDto> getStrafEreignisse() {
        return strafEreignisse;
    }

    public List<ExportSpielerTorEreignisDto> getTorEreignisse() {
        return torEreignisse;
    }

    public List<ExportTeamDto> getTeams() {
        return teams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExportDto exportDto = (ExportDto) o;
        return Objects.equals(spieler, exportDto.spieler) &&
                Objects.equals(spielberichte, exportDto.spielberichte) &&
                Objects.equals(strafEreignisse, exportDto.strafEreignisse) &&
                Objects.equals(torEreignisse, exportDto.torEreignisse) &&
                Objects.equals(teams, exportDto.teams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spieler, spielberichte, strafEreignisse, torEreignisse, teams);
    }

    @Override
    public String toString() {
        return "ExportDto{" +
                "spieler=" + spieler +
                ", spielberichte=" + spielberichte +
                ", strafEreignisse=" + strafEreignisse +
                ", torEreignisse=" + torEreignisse +
                ", teams=" + teams +
                '}';
    }
}
