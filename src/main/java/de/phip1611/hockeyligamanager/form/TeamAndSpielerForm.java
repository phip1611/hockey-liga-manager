/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.form;

import de.phip1611.hockeyligamanager.domain.Team;
import de.phip1611.hockeyligamanager.service.api.dto.TeamDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

public class TeamAndSpielerForm implements Form {

    private UUID id;

    @NotEmpty
    private String name;

    @NotNull
    private List<SpielerForm> spieler = new ArrayList<>();

    public TeamAndSpielerForm() {
        // default konstruktor für @ConfigurationProperties
    }

    public TeamAndSpielerForm(TeamDto dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.spieler = dto.getSpieler().stream().map(SpielerForm::new).collect(toList());
    }

    // fügt fünf leere Felder hinzu, damit man im UI mehr Spieler hinzufügen kann
    private void addEmptySpielerFields() {
        for (int i = 0; i < 5; i++) {
            this.spieler.add(new SpielerForm());
        }
    }

    public UUID getId() {
        return id;
    }

    public TeamAndSpielerForm setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public TeamAndSpielerForm setName(String name) {
        this.name = name;
        return this;
    }

    public List<SpielerForm> getSpieler() {
        return spieler;
    }

    public void setSpieler(List<SpielerForm> spieler) {
        this.spieler = spieler;
    }

    public Team build() {
        return new Team(this);
    }

    @Override
    public void addExtraFields() {
        this.addEmptySpielerFields();
    }

    @Override
    public void removeEmptyFields() {
        var invalideFelder = this.spieler.stream()
                .filter(s -> s.getVorname().isEmpty() && s.getNachname().isEmpty())
                .collect(toList());
        this.spieler.removeAll(invalideFelder);
    }
}
