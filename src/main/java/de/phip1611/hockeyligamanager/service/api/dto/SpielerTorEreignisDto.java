/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.service.api.dto;

import de.phip1611.hockeyligamanager.domain.SpielerTorEreignis;

import java.util.UUID;

public class SpielerTorEreignisDto {
    private UUID id;

    // Spielminute >= 0
    private int time;

    private String schuetzeName;

    private String firstAssistName;

    private String secondAssistName;

    public SpielerTorEreignisDto(SpielerTorEreignis spielerTorEreignis) {
        this.id = spielerTorEreignis.getId();
        this.time = spielerTorEreignis.getTime();
        this.schuetzeName = spielerTorEreignis.getSchuetze().getFullName();
        this.firstAssistName = spielerTorEreignis.getFirstAssist() != null ?
                spielerTorEreignis.getFirstAssist().getFullName() : null;
        this.secondAssistName = spielerTorEreignis.getSecondAssist() != null ?
                spielerTorEreignis.getSecondAssist().getFullName() : null;
    }

    public UUID getId() {
        return id;
    }

    public int getTime() {
        return time;
    }

    public String getSchuetzeName() {
        return schuetzeName;
    }

    public String getFirstAssistName() {
        return firstAssistName;
    }

    public String getSecondAssistName() {
        return secondAssistName;
    }
}
