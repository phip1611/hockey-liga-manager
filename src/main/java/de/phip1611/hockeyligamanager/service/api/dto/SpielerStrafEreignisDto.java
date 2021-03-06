/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.service.api.dto;

import de.phip1611.hockeyligamanager.domain.SpielerStrafEreignis;

import java.util.UUID;

public class SpielerStrafEreignisDto implements Comparable<SpielerStrafEreignisDto> {

    private UUID id;

    // Spielminute >= 0
    private int time;

    // Nummer des Regelverstoßes
    private int rv;

    private String spielerName;

    private int dauer;

    public SpielerStrafEreignisDto(SpielerStrafEreignis spielerStrafEreignis) {
        this.id = spielerStrafEreignis.getId();
        this.time = spielerStrafEreignis.getTime();
        this.rv = spielerStrafEreignis.getRv();
        this.dauer = spielerStrafEreignis.getDauer();
        this.spielerName = spielerStrafEreignis.getSpieler().getFullName();
    }

    public UUID getId() {
        return id;
    }

    public int getTime() {
        return time;
    }

    public int getRv() {
        return rv;
    }

    public String getSpielerId() {
        return spielerName;
    }

    public String getSpielerName() {
        return spielerName;
    }

    public int getDauer() {
        return dauer;
    }

    @Override
    public int compareTo(SpielerStrafEreignisDto o) {
        return time - o.time;
    }
}
