package de.phip1611.hockeyligamanager.service.api.dto;

import de.phip1611.hockeyligamanager.domain.Spieler;

import java.util.UUID;

public class SpielerDto implements Comparable<SpielerDto> {

    private UUID id;

    private String vorname;

    private String nachname;

    private String teamName;

    private String fullName;

    private String fullNameWithTeamPrefix;

    private int nummer;

    public SpielerDto(Spieler spieler) {
        this.id       = spieler.getId();
        this.vorname  = spieler.getVorname();
        this.nachname = spieler.getNachname();
        this.nummer   = spieler.getNummer();
        this.teamName = spieler.getTeam() != null ? spieler.getTeam().getName() : null;
        this.fullName = spieler.getFullName();
        this.fullNameWithTeamPrefix = "[" + teamName + "] " + fullName;
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

    public String getFullName() {
        return fullName;
    }

    public String getFullNameWithTeamPrefix() {
        return fullNameWithTeamPrefix;
    }

    public int getNummer() {
        return nummer;
    }

    @Override
    public int compareTo(SpielerDto o) {
        // getFullNameWithTeamPrefix ist das Anzeigewert im UI, darum sortieren wir auch danach
        return this.fullNameWithTeamPrefix.compareTo(o.fullNameWithTeamPrefix);
    }
}
