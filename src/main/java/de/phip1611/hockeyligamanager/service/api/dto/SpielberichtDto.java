/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.service.api.dto;

import de.phip1611.hockeyligamanager.domain.Spielbericht;
import de.phip1611.hockeyligamanager.domain.SpielerStrafEreignis;
import de.phip1611.hockeyligamanager.domain.SpielerTorEreignis;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

public class SpielberichtDto implements Comparable<SpielberichtDto> {

    private UUID id;

    private String teamHeim;

    private String teamGast;

    private String schiedrsrichter1;

    private String schiedrsrichter2;

    private String zeitnehmer;

    private int zuschauer;

    private String ort;

    private LocalDateTime begin;

    private List<SpielerTorEreignisDto> heimSpielerTorEreignisList = new ArrayList<>();

    private List<SpielerStrafEreignisDto> heimSpielerStrafEreignisList = new ArrayList<>();

    private List<SpielerTorEreignisDto> gastSpielerTorEreignisList = new ArrayList<>();

    private List<SpielerStrafEreignisDto> gastSpielerStrafEreignisList = new ArrayList<>();

    public SpielberichtDto(Spielbericht spielbericht) {
        this.id      = spielbericht.getId();
        this.teamGast = spielbericht.getTeamGast() != null ? spielbericht.getTeamGast().getName() : null;
        this.teamHeim = spielbericht.getTeamHeim() != null ? spielbericht.getTeamHeim().getName() : null;
        this.schiedrsrichter1 = spielbericht.getSchiedsrichter1();
        this.schiedrsrichter2 = spielbericht.getSchiedsrichter2();
        this.zeitnehmer = spielbericht.getZeitnehmer();
        this.zuschauer = spielbericht.getZuschauer();
        this.ort = spielbericht.getOrt();
        this.begin = spielbericht.getBegin();
        this.heimSpielerStrafEreignisList = spielbericht.getHeimSpielerStrafEreignisList().stream()
                .map(SpielerStrafEreignis::toDto).collect(toList());
        this.heimSpielerTorEreignisList = spielbericht.getHeimSpielerTorEreignisList().stream()
                .map(SpielerTorEreignis::toDto).collect(toList());
        this.gastSpielerStrafEreignisList = spielbericht.getGastSpielerStrafEreignisList().stream()
                .map(SpielerStrafEreignis::toDto).collect(toList());
        this.gastSpielerTorEreignisList = spielbericht.getGastSpielerTorEreignisList().stream()
                .map(SpielerTorEreignis::toDto).collect(toList());
    }

    public UUID getId() {
        return id;
    }

    public String getTeamHeim() {
        return teamHeim;
    }

    public String getTeamGast() {
        return teamGast;
    }

    public String getSchiedrsrichter1() {
        return schiedrsrichter1;
    }

    public String getSchiedrsrichter2() {
        return schiedrsrichter2;
    }

    public String getZeitnehmer() {
        return zeitnehmer;
    }

    public int getZuschauer() {
        return zuschauer;
    }

    public String getOrt() {
        return ort;
    }

    public LocalDateTime getBegin() {
        return begin;
    }

    public List<SpielerTorEreignisDto> getGastSpielerTorEreignisList() {
        return gastSpielerTorEreignisList;
    }

    public List<SpielerStrafEreignisDto> getGastSpielerStrafEreignisList() {
        return gastSpielerStrafEreignisList;
    }

    public List<SpielerTorEreignisDto> getHeimSpielerTorEreignisList() {
        return heimSpielerTorEreignisList;
    }

    public List<SpielerStrafEreignisDto> getHeimSpielerStrafEreignisList() {
        return heimSpielerStrafEreignisList;
    }

    @Override
    public int compareTo(SpielberichtDto o) {
        return begin.compareTo(o.begin);
    }
}
