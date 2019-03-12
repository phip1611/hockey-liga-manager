/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.domain;

import de.phip1611.hockeyligamanager.form.SpielberichtForm;
import de.phip1611.hockeyligamanager.service.api.dto.SpielberichtDto;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@Entity
public class Spielbericht {

    private static int REGULAR_GAME_DURATION = 60;

    @Id
    private UUID id;

    @JoinColumn
    @ManyToOne
    private Team teamHeim;

    @JoinColumn
    @ManyToOne
    private Team teamGast;

    private String schiedsrichter1;

    private String schiedsrichter2;

    private String zeitnehmer;

    private int zuschauer;

    private String ort;

    private LocalDateTime begin;

    @OneToMany(cascade = CascadeType.ALL)
    private List<SpielerTorEreignis> heimSpielerTorEreignisList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<SpielerStrafEreignis> heimSpielerStrafEreignisList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<SpielerTorEreignis> gastSpielerTorEreignisList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<SpielerStrafEreignis> gastSpielerStrafEreignisList = new ArrayList<>();

    private Spielbericht() {
        /* hibernate default constructor */
    }

    public Spielbericht(SpielberichtForm form,
                        Function<UUID, Optional<Team>> teamFinder,
                        Function<UUID, Optional<Spieler>> spielerFinder) {
        this.id = form.getId() == null ? UUID.randomUUID() : form.getId();
        this.update(form, teamFinder, spielerFinder);
    }

    public Spielbericht update(SpielberichtForm form,
                               Function<UUID, Optional<Team>> teamFinder,
                               Function<UUID, Optional<Spieler>> spielerFinder) {
        this.teamHeim = teamFinder.apply(form.getTeamHeimId()).get();
        this.teamGast = teamFinder.apply(form.getTeamGastId()).get();
        this.schiedsrichter1 = form.getSchiedsrichter1();
        this.schiedsrichter2 = form.getSchiedsrichter2();
        this.zeitnehmer = form.getZeitnehmer();
        this.zuschauer = form.getZuschauer();
        this.ort = form.getOrt();
        this.begin = form.getBeginTimeString() != null ? LocalDateTime.parse(form.getBeginTimeString()) : null;

        this.heimSpielerStrafEreignisList = form.getHeimSpielerStrafEreignisList().stream()
                .map(x -> x.build(spielerFinder))
                .collect(toList());
        this.heimSpielerTorEreignisList = form.getHeimSpielerTorEreignisList().stream()
                .map(x -> x.build(spielerFinder))
                .collect(toList());

        this.gastSpielerStrafEreignisList = form.getGastSpielerStrafEreignisList().stream()
                .map(x -> x.build(spielerFinder))
                .collect(toList());
        this.gastSpielerTorEreignisList = form.getGastSpielerTorEreignisList().stream()
                .map(x -> x.build(spielerFinder))
                .collect(toList());

        return this;
    }

    public Team getSiegerTeam() {
        if (this.heimSpielerTorEreignisList.size() > this.gastSpielerTorEreignisList.size()) {
            return teamHeim;
        }
        return this.teamGast;
    }

    public boolean finishedInRegularTime() {
        var x = this.getGastSpielerTorEreignisList().stream()
                .filter(a -> a.getTime() >= REGULAR_GAME_DURATION)
                .findAny();
        if (x.isPresent()) return false;
        x = this.getHeimSpielerTorEreignisList().stream()
                .filter(a -> a.getTime() >= REGULAR_GAME_DURATION)
                .findAny();
        return x.isEmpty();
    }

    /**
     * Gibt an ob in der regulären Spielzeit ein Unentschieden erzielt wurde.
     * Dann kriegt jedes Team schon mal einen Punkt.
     */
    public boolean isUnentschieden() {
        return this.finishedInRegularTime() &&
                this.getHeimSpielerTorEreignisList().size() == this.getGastSpielerTorEreignisList().size();
    }

    /**
     * Gibt an ob in regulärer Zeit ein Sieg errungen wurde.
     * Dann gibt es 3 Punkte!
     */
    public boolean isWinInRegularTime() {
        return this.finishedInRegularTime()
                && this.getHeimSpielerTorEreignisList().size() != this.getGastSpielerTorEreignisList().size();
    }

    // nur "primitive/triviale getter"

    public UUID getId() {
        return id;
    }

    public Team getTeamHeim() {
        return teamHeim;
    }

    public Team getTeamGast() {
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

    public List<SpielerTorEreignis> getHeimSpielerTorEreignisList() {
        return heimSpielerTorEreignisList;
    }

    public List<SpielerStrafEreignis> getHeimSpielerStrafEreignisList() {
        return heimSpielerStrafEreignisList;
    }

    public List<SpielerTorEreignis> getGastSpielerTorEreignisList() {
        return gastSpielerTorEreignisList;
    }

    public List<SpielerStrafEreignis> getGastSpielerStrafEreignisList() {
        return gastSpielerStrafEreignisList;
    }

    public SpielberichtDto toDto() {
        return new SpielberichtDto(this);
    }
}
