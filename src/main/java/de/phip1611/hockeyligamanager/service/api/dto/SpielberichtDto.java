/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.service.api.dto;

import de.phip1611.hockeyligamanager.domain.Spielbericht;
import de.phip1611.hockeyligamanager.domain.Spieler;
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

    private String schiedsrichter1;

    private String schiedsrichter2;

    private String zeitnehmer;

    private int zuschauer;

    private String ort;

    private LocalDateTime begin;

    private int heimToreCountInRegTime;

    private int gastToreCountInRegTime;

    private int heimToreCountInOverTime;

    private int gastToreCountInOverTime;

    private List<String> anwesendeSpielerHeim = new ArrayList<>();

    private List<String> anwesendeSpielerGast = new ArrayList<>();

    private List<SpielerTorEreignisDto> heimSpielerTorEreignisList = new ArrayList<>();

    private List<SpielerStrafEreignisDto> heimSpielerStrafEreignisList = new ArrayList<>();

    private List<SpielerTorEreignisDto> gastSpielerTorEreignisList = new ArrayList<>();

    private List<SpielerStrafEreignisDto> gastSpielerStrafEreignisList = new ArrayList<>();

    public SpielberichtDto(Spielbericht spielbericht) {
        this.id      = spielbericht.getId();
        this.teamGast = spielbericht.getTeamGast() != null ? spielbericht.getTeamGast().getName() : null;
        this.teamHeim = spielbericht.getTeamHeim() != null ? spielbericht.getTeamHeim().getName() : null;
        this.schiedsrichter1 = spielbericht.getSchiedsrichter1();
        this.schiedsrichter2 = spielbericht.getSchiedsrichter2();
        this.zeitnehmer = spielbericht.getZeitnehmer();
        this.zuschauer = spielbericht.getZuschauer();
        this.ort = spielbericht.getOrt();
        this.begin = spielbericht.getBegin();

        this.heimToreCountInRegTime = spielbericht.getHeimToreCountInRegTime();
        this.gastToreCountInRegTime = spielbericht.getGastToreCountInRegTime();
        this.heimToreCountInOverTime = spielbericht.getHeimToreCountInOverTime();
        this.gastToreCountInOverTime = spielbericht.getGastToreCountInOverTime();

        this.anwesendeSpielerHeim.addAll(
                spielbericht.getAnwesendeSpielerHeim().stream()
                        .map(Spieler::getFullName)
                        .sorted()
                        .collect(toList())
        );
        this.anwesendeSpielerGast.addAll(
                spielbericht.getAnwesendeSpielerGast().stream()
                        .map(Spieler::getFullName)
                        .sorted()
                        .collect(toList())
        );

        this.heimSpielerStrafEreignisList = spielbericht.getHeimSpielerStrafEreignisList().stream()
                .map(SpielerStrafEreignis::toDto).collect(toList());
        this.heimSpielerTorEreignisList = spielbericht.getHeimSpielerTorEreignisList().stream()
                .map(SpielerTorEreignis::toDto).collect(toList());
        this.gastSpielerStrafEreignisList = spielbericht.getGastSpielerStrafEreignisList().stream()
                .map(SpielerStrafEreignis::toDto).collect(toList());
        this.gastSpielerTorEreignisList = spielbericht.getGastSpielerTorEreignisList().stream()
                .map(SpielerTorEreignis::toDto).collect(toList());
    }

    // gibt den formatierten Ergebnisstring zurück
    public String getErgebnisString() {
        var heimtore = heimToreCountInRegTime + heimToreCountInOverTime;
        var gasttore = gastToreCountInRegTime + gastToreCountInOverTime;
        var str =  heimToreCountInRegTime + ":" + gastToreCountInRegTime;
        if (heimToreCountInOverTime + gastToreCountInOverTime == 0) {
            str += " (-:-)";
        } else {
            str += " (" + heimtore + ":" + gasttore + ")";
        }
        return str;
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

    public String getSchiedsrichter1() {
        return schiedsrichter1;
    }

    public String getSchiedsrichter2() {
        return schiedsrichter2;
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

    public int getHeimToreCountInRegTime() {
        return heimToreCountInRegTime;
    }

    public int getGastToreCountInRegTime() {
        return gastToreCountInRegTime;
    }

    public int getHeimToreCountInOverTime() {
        return heimToreCountInOverTime;
    }

    public int getGastToreCountInOverTime() {
        return gastToreCountInOverTime;
    }

    public List<String> getAnwesendeSpielerHeim() {
        return anwesendeSpielerHeim;
    }

    public List<String> getAnwesendeSpielerGast() {
        return anwesendeSpielerGast;
    }

    @Override
    public int compareTo(SpielberichtDto o) {
        return (begin != null && o.begin != null) ? begin.compareTo(o.begin) : 0;
    }
}
