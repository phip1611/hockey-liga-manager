package de.phip1611.hockeyligamanager.service.api.dto;

import de.phip1611.hockeyligamanager.domain.Spieler;

import java.util.UUID;

public class SpielerDto implements Comparable<SpielerDto> {

    private UUID id;

    private String vorname;

    private String nachname;

    private String teamName;

    private String fullName;

    private String fullNameWithTeamPraefix;

    private int nummer;

    public SpielerDto(Spieler spieler) {
        this.id       = spieler.getId();
        this.vorname  = spieler.getVorname();
        this.nachname = spieler.getNachname();
        this.nummer   = spieler.getNummer();
        this.teamName = spieler.getTeam() != null ? spieler.getTeam().getName() : null;
        this.fullName = spieler.getFullName();
        this.fullNameWithTeamPraefix = "[" + teamName + "] " + fullName;
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

    public String getFullNameWithTeamPraefix() {
        return fullNameWithTeamPraefix;
    }

    public int getNummer() {
        return nummer;
    }

    @Override
    public int compareTo(SpielerDto o) {
        var a = teamName.compareTo(o.teamName);
        var b = nachname.compareTo(o.nachname);
        var c = vorname.compareTo(o.vorname);
        var d = nummer - o.nummer;
        if (a != 0) return a;
        if (b != 0) return b;
        if (c != 0) return c;
        return d;
    }
}
