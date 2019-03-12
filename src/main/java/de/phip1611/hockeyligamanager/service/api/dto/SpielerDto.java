package de.phip1611.hockeyligamanager.service.api.dto;

import de.phip1611.hockeyligamanager.domain.Spieler;

import java.util.UUID;

public class SpielerDto {

    private UUID id;

    private String vorname;

    private String nachname;

    private String teamName;

    public SpielerDto(Spieler spieler) {
        this.id       = spieler.getId();
        this.vorname  = spieler.getVorname();
        this.nachname = spieler.getNachname();
        this.teamName = spieler.getTeam() != null ? spieler.getTeam().getName() : null;
    }

    public UUID getId() {
        return id;
    }

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public String getTeamName() {
        return teamName;
    }

}
