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

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class SpielberichtForm implements Form {

    private UUID id;

    @NotNull
    private UUID teamHeimId;

    @NotNull
    private UUID teamGastId;

    private String schiedsrichter1;

    private String schiedsrichter2;

    private String zeitnehmer;

    private int zuschauer;

    private String ort;

    @NotNull
    private String beginTimeString;

    @NotEmpty
    private List<UUID> anwesendeSpielerHeim = new ArrayList<>();

    @NotEmpty
    private List<UUID> anwesendeSpielerGast = new ArrayList<>();

    @NotNull
    private List<SpielerTorEreignisForm> heimSpielerTorEreignisList = new ArrayList<>();

    @NotNull
    private List<SpielerStrafEreignisForm> heimSpielerStrafEreignisList = new ArrayList<>();

    @NotNull
    private List<SpielerTorEreignisForm> gastSpielerTorEreignisList = new ArrayList<>();

    @NotNull
    private List<SpielerStrafEreignisForm> gastSpielerStrafEreignisList = new ArrayList<>();

    public SpielberichtForm() {
    }

    public SpielberichtForm(Spielbericht entity, DateTimeFormatter formatter) {
        this.setId(entity.getId());
        this.setTeamGastId(entity.getTeamGast().getId());
        this.setTeamHeimId(entity.getTeamHeim().getId());
        this.setSchiedsrichter1(entity.getSchiedsrichter1());
        this.setSchiedsrichter2(entity.getSchiedsrichter2());
        this.setZeitnehmer(entity.getZeitnehmer());
        this.setZuschauer(entity.getZuschauer());
        this.setOrt(entity.getOrt());
        this.setBeginTimeString(formatter.format(entity.getBegin()));
        this.setAnwesendeSpielerGast(entity.getAnwesendeSpielerGast().stream().map(Spieler::getId).collect(toList()));
        this.setAnwesendeSpielerHeim(entity.getAnwesendeSpielerHeim().stream().map(Spieler::getId).collect(toList()));
        this.setHeimSpielerTorEreignisList(entity.getHeimSpielerTorEreignisList().stream().map(SpielerTorEreignisForm::new).collect(toList()));
        this.setGastSpielerTorEreignisList(entity.getGastSpielerTorEreignisList().stream().map(SpielerTorEreignisForm::new).collect(toList()));
        this.setHeimSpielerStrafEreignisList(entity.getHeimSpielerStrafEreignisList().stream().map(SpielerStrafEreignisForm::new).collect(toList()));
        this.setGastSpielerStrafEreignisList(entity.getGastSpielerStrafEreignisList().stream().map(SpielerStrafEreignisForm::new).collect(toList()));
    }

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
        return schiedsrichter1;
    }

    public void setSchiedsrichter1(String schiedsrichter1) {
        this.schiedsrichter1 = schiedsrichter1;
    }

    public String getSchiedsrichter2() {
        return schiedsrichter2;
    }

    public void setSchiedsrichter2(String schiedsrichter2) {
        this.schiedsrichter2 = schiedsrichter2;
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

    public Spielbericht build(Function<UUID, Optional<Team>> teamFinder,
                              Function<UUID, Optional<Spieler>> spielerFinder,
                              DateTimeFormatter formatter) {
        return new Spielbericht(this, teamFinder, spielerFinder, formatter);
    }

    @Override
    public void addExtraFields() {
        for (int i = 0; i < EXTRA_FIELDS_SIZE; i++) {
            this.gastSpielerStrafEreignisList.add(new SpielerStrafEreignisForm());
            this.heimSpielerStrafEreignisList.add(new SpielerStrafEreignisForm());
            this.gastSpielerTorEreignisList.add(new SpielerTorEreignisForm());
            this.heimSpielerTorEreignisList.add(new SpielerTorEreignisForm());
        }
    }

    @Override
    public void removeEmptyFields() {
        this.heimSpielerTorEreignisList.removeAll(
                this.heimSpielerTorEreignisList.stream()
                        .filter(MultipleFormField::isEmpty)
                        .collect(toList())
        );
        this.heimSpielerStrafEreignisList.removeAll(
                this.heimSpielerStrafEreignisList.stream()
                        .filter(MultipleFormField::isEmpty)
                        .collect(toList())
        );
        this.gastSpielerTorEreignisList.removeAll(
                this.gastSpielerTorEreignisList.stream()
                        .filter(MultipleFormField::isEmpty)
                        .collect(toList())
        );
        this.gastSpielerStrafEreignisList.removeAll(
                this.gastSpielerStrafEreignisList.stream()
                        .filter(MultipleFormField::isEmpty)
                        .collect(toList())
        );
    }
}
