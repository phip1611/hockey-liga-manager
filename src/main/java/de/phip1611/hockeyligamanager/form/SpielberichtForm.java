/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.form;

import de.phip1611.hockeyligamanager.domain.Spielbericht;
import de.phip1611.hockeyligamanager.domain.Spieler;
import de.phip1611.hockeyligamanager.domain.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public class SpielberichtForm {

    private UUID id;

    private UUID teamHeimId;

    private UUID teamGastId;

    private String Schiedsrichter1;

    private String Schiedsrichter2;

    private String zeitnehmer;

    private int zuschauer;

    private String ort;

    private String beginTimeString;

    private List<UUID> anwesendeSpielerHeim = new ArrayList<>();

    private List<UUID> anwesendeSpielerGast = new ArrayList<>();

    private List<SpielerTorEreignisForm> heimSpielerTorEreignisList = new ArrayList<>();

    private List<SpielerStrafEreignisForm> heimSpielerStrafEreignisList = new ArrayList<>();

    private List<SpielerTorEreignisForm> gastSpielerTorEreignisList = new ArrayList<>();

    private List<SpielerStrafEreignisForm> gastSpielerStrafEreignisList = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public SpielberichtForm setId(UUID id) {
        this.id = id;
        return this;
    }

    public UUID getTeamHeimId() {
        return teamHeimId;
    }

    public SpielberichtForm setTeamHeimId(UUID teamHeimId) {
        this.teamHeimId = teamHeimId;
        return this;
    }

    public UUID getTeamGastId() {
        return teamGastId;
    }

    public SpielberichtForm setTeamGastId(UUID teamGastId) {
        this.teamGastId = teamGastId;
        return this;
    }

    public String getSchiedsrichter1() {
        return Schiedsrichter1;
    }

    public void setSchiedsrichter1(String schiedsrichter1) {
        Schiedsrichter1 = schiedsrichter1;
    }

    public String getSchiedsrichter2() {
        return Schiedsrichter2;
    }

    public void setSchiedsrichter2(String schiedsrichter2) {
        Schiedsrichter2 = schiedsrichter2;
    }

    public String getZeitnehmer() {
        return zeitnehmer;
    }

    public void setZeitnehmer(String zeitnehmer) {
        this.zeitnehmer = zeitnehmer;
    }

    public int getZuschauer() {
        return zuschauer;
    }

    public void setZuschauer(int zuschauer) {
        this.zuschauer = zuschauer;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getBeginTimeString() {
        return beginTimeString;
    }

    public void setBeginTimeString(String beginTimeString) {
        this.beginTimeString = beginTimeString;
    }

    public List<SpielerTorEreignisForm> getHeimSpielerTorEreignisList() {
        return heimSpielerTorEreignisList;
    }

    public void setHeimSpielerTorEreignisList(List<SpielerTorEreignisForm> heimSpielerTorEreignisList) {
        this.heimSpielerTorEreignisList = heimSpielerTorEreignisList;
    }

    public List<SpielerStrafEreignisForm> getHeimSpielerStrafEreignisList() {
        return heimSpielerStrafEreignisList;
    }

    public void setHeimSpielerStrafEreignisList(List<SpielerStrafEreignisForm> heimSpielerStrafEreignisList) {
        this.heimSpielerStrafEreignisList = heimSpielerStrafEreignisList;
    }

    public List<SpielerTorEreignisForm> getGastSpielerTorEreignisList() {
        return gastSpielerTorEreignisList;
    }

    public void setGastSpielerTorEreignisList(List<SpielerTorEreignisForm> gastSpielerTorEreignisList) {
        this.gastSpielerTorEreignisList = gastSpielerTorEreignisList;
    }

    public List<SpielerStrafEreignisForm> getGastSpielerStrafEreignisList() {
        return gastSpielerStrafEreignisList;
    }

    public void setGastSpielerStrafEreignisList(List<SpielerStrafEreignisForm> gastSpielerStrafEreignisList) {
        this.gastSpielerStrafEreignisList = gastSpielerStrafEreignisList;
    }

    public List<UUID> getAnwesendeSpielerHeim() {
        return anwesendeSpielerHeim;
    }

    public void setAnwesendeSpielerHeim(List<UUID> anwesendeSpielerHeim) {
        this.anwesendeSpielerHeim = anwesendeSpielerHeim;
    }

    public List<UUID> getAnwesendeSpielerGast() {
        return anwesendeSpielerGast;
    }

    public void setAnwesendeSpielerGast(List<UUID> anwesendeSpielerGast) {
        this.anwesendeSpielerGast = anwesendeSpielerGast;
    }

    public Spielbericht build(Function<UUID, Optional<Team>> teamFinder, Function<UUID, Optional<Spieler>> spielerFinder) {
        return new Spielbericht(this, teamFinder, spielerFinder);
    }

}
