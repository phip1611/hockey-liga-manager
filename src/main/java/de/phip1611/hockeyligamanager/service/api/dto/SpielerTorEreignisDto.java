/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.service.api.dto;

import de.phip1611.hockeyligamanager.domain.SpielerTorEreignis;

import java.util.UUID;

public class SpielerTorEreignisDto implements Comparable<SpielerTorEreignisDto> {
    private UUID id;

    // Spielminute >= 0
    private int time;

    private String schuetzeName;

    private String firstAssistName;

    public SpielerTorEreignisDto(SpielerTorEreignis spielerTorEreignis) {
        this.id = spielerTorEreignis.getId();
        this.time = spielerTorEreignis.getTime();
        this.schuetzeName = spielerTorEreignis.getSchuetze().getFullName();
        this.firstAssistName = spielerTorEreignis.getFirstAssist() != null ?
                spielerTorEreignis.getFirstAssist().getFullName() : null;
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

    @Override
    public int compareTo(SpielerTorEreignisDto o) {
        return time - o.time;
    }
}
