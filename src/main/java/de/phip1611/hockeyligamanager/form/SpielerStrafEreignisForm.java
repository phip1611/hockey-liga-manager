/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.form;

import de.phip1611.hockeyligamanager.domain.Spieler;
import de.phip1611.hockeyligamanager.domain.SpielerStrafEreignis;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public class SpielerStrafEreignisForm {

    private UUID id;

    // Spielminute >= 0
    private int time;

    // Nummer des Regelverstoßes
    private int rv;

    private UUID spielerId;

    // dauer der Strafe in Minuten
    private int dauer;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getRv() {
        return rv;
    }

    public void setRv(int rv) {
        this.rv = rv;
    }

    public int getDauer() {
        return dauer;
    }

    public void setDauer(int dauer) {
        this.dauer = dauer;
    }

    public UUID getSpielerId() {
        return spielerId;
    }

    public void setSpielerId(UUID spielerId) {
        this.spielerId = spielerId;
    }

    public SpielerStrafEreignis build(Function<UUID, Optional<Spieler>> spielerFinder) {
        return new SpielerStrafEreignis(this, spielerFinder);
    }

}
