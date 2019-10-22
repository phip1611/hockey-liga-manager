/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.service.api.dto;

import de.phip1611.hockeyligamanager.domain.Spieler;

public class SchuetzenTabellenEintragDto implements Comparable<SchuetzenTabellenEintragDto> {

    private String vorname;

    private String nachname;

    private String teamName;

    private int tore;

    private int firstAssist;

    private int strafen;

    private int strafMinuten;

    public SchuetzenTabellenEintragDto(Spieler spieler) {
        this.vorname = spieler.getVorname();
        this.nachname = spieler.getNachname();
        this.teamName = spieler.getTeam() != null ? spieler.getTeam().getName() : null;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTore(int tore) {
        this.tore = tore;
    }

    public void setFirstAssist(int firstAssist) {
        this.firstAssist = firstAssist;
    }

    public void setStrafen(int strafen) {
        this.strafen = strafen;
    }

    public void setStrafMinuten(int strafMinuten) {
        this.strafMinuten = strafMinuten;
    }

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public int getTore() {
        return tore;
    }

    public int getFirstAssist() {
        return firstAssist;
    }

    public int getStrafen() {
        return strafen;
    }

    public int getStrafMinuten() {
        return strafMinuten;
    }

    public String getStrafenString() {
        return strafen + " (" + strafMinuten + ")";
    }

    @Override
    public int compareTo(SchuetzenTabellenEintragDto o) {
        if (o.tore != tore) {
            return o.tore - tore;
        }
        return o.firstAssist - firstAssist;
    }
}
