/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.form;

import de.phip1611.hockeyligamanager.domain.Spieler;
import de.phip1611.hockeyligamanager.domain.SpielerTorEreignis;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public class SpielerTorEreignisForm {
    private UUID id;

    // Spielminute >= 0
    private int time;

    private UUID schuetzeId;

    private UUID firstAssistId;

    private UUID secondAssistId;

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

    public UUID getSchuetzeId() {
        return schuetzeId;
    }

    public void setSchuetzeId(UUID schuetzeId) {
        this.schuetzeId = schuetzeId;
    }

    public UUID getFirstAssistId() {
        return firstAssistId;
    }

    public void setFirstAssistId(UUID firstAssistId) {
        this.firstAssistId = firstAssistId;
    }

    public UUID getSecondAssistId() {
        return secondAssistId;
    }

    public void setSecondAssistId(UUID secondAssistId) {
        this.secondAssistId = secondAssistId;
    }

    public SpielerTorEreignis build(Function<UUID, Optional<Spieler>> spielerFinder) {
        return new SpielerTorEreignis(this, spielerFinder);
    }

}
