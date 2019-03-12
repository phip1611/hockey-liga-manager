/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.service.api.dto;

public class LigatabellenEintragDto implements Comparable<LigatabellenEintragDto> {

    private int platz;

    private String teamName;

    private int anzahlSpiele;

    private int anzahlSiege3P;

    private int anzahlSiege2P;

    private int anzahlNiederlagen1P;

    private int anzahlNiederlagen0P;

    private int tore;

    private int gegentore;

    private int punkte;

    public LigatabellenEintragDto(String teamName) {
        this.teamName = teamName;
    }

    public void setPlatz(int platz) {
        this.platz = platz;
    }

    public void setAnzahlSpiele(int anzahlSpiele) {
        this.anzahlSpiele = anzahlSpiele;
    }

    public void setAnzahlSiege3P(int anzahlSiege3P) {
        this.anzahlSiege3P = anzahlSiege3P;
    }

    public void setAnzahlNiederlagen1P(int anzahlNiederlagen1P) {
        this.anzahlNiederlagen1P = anzahlNiederlagen1P;
    }

    public void setTore(int tore) {
        this.tore = tore;
    }

    public void setGegentore(int gegentore) {
        this.gegentore = gegentore;
    }

    public int getPlatz() {
        return platz;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getAnzahlSpiele() {
        return anzahlSpiele;
    }

    public int getAnzahlSiege3P() {
        return anzahlSiege3P;
    }

    public int getAnzahlNiederlagen1P() {
        return anzahlNiederlagen1P;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getAnzahlSiege2P() {
        return anzahlSiege2P;
    }

    public void setAnzahlSiege2P(int anzahlSiege2P) {
        this.anzahlSiege2P = anzahlSiege2P;
    }

    public int getAnzahlNiederlagen0P() {
        return anzahlNiederlagen0P;
    }

    public void setAnzahlNiederlagen0P(int anzahlNiederlagen0P) {
        this.anzahlNiederlagen0P = anzahlNiederlagen0P;
    }

    public int getTore() {
        return tore;
    }

    public int getGegentore() {
        return gegentore;
    }

    public int getPunkte() {
        return punkte;
    }

    public void setPunkte(int punkte) {
        this.punkte = punkte;
    }

    @Override
    public int compareTo(LigatabellenEintragDto o) {
        return o.punkte - punkte;
    }
}
