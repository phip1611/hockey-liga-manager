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

    private double toreProSpiel;

    private int gegentore;

    private float gegentoreProSpiel;

    private int punkte;

    // Tore minus Gegentore
    private int gegenTorSchnitt;

    public LigatabellenEintragDto() {
    }

    public int getPlatz() {
        return platz;
    }

    public void setPlatz(int platz) {
        this.platz = platz;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getAnzahlSpiele() {
        return anzahlSpiele;
    }

    public void setAnzahlSpiele(int anzahlSpiele) {
        this.anzahlSpiele = anzahlSpiele;
    }

    public int getAnzahlSiege3P() {
        return anzahlSiege3P;
    }

    public void setAnzahlSiege3P(int anzahlSiege3P) {
        this.anzahlSiege3P = anzahlSiege3P;
    }

    public int getAnzahlNiederlagen1P() {
        return anzahlNiederlagen1P;
    }

    public void setAnzahlNiederlagen1P(int anzahlNiederlagen1P) {
        this.anzahlNiederlagen1P = anzahlNiederlagen1P;
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

    public void setTore(int tore) {
        this.tore = tore;
    }

    public double getToreProSpiel() {
        return toreProSpiel;
    }

    public void setToreProSpiel(double toreProSpiel) {
        this.toreProSpiel = toreProSpiel;
    }

    public float getGegentoreProSpiel() {
        return gegentoreProSpiel;
    }

    public void setGegentoreProSpiel(float gegentoreProSpiel) {
        this.gegentoreProSpiel = gegentoreProSpiel;
    }

    public int getGegentore() {
        return gegentore;
    }

    public void setGegentore(int gegentore) {
        this.gegentore = gegentore;
    }

    public int getPunkte() {
        return punkte;
    }

    public void setPunkte(int punkte) {
        this.punkte = punkte;
    }

    public int getGegenTorSchnitt() {
        return gegenTorSchnitt;
    }

    public void setGegenTorSchnitt(int gegenTorSchnitt) {
        this.gegenTorSchnitt = gegenTorSchnitt;
    }

    @Override
    public int compareTo(LigatabellenEintragDto o) {
        return o.punkte - punkte;
    }
}
