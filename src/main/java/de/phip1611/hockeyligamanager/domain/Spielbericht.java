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
import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@Entity
public class Spielbericht {

    public static int REGULAR_GAME_DURATION = 60;

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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SpielerTorEreignis> heimSpielerTorEreignisList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SpielerStrafEreignis> heimSpielerStrafEreignisList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SpielerTorEreignis> gastSpielerTorEreignisList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
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

        var neueHeimSpielerStrafEreignisse = form.getHeimSpielerStrafEreignisList().stream()
                .map(x -> x.build(spielerFinder))
                .collect(toList());
        var neueHeimSpielerTorEreignisse = form.getHeimSpielerTorEreignisList().stream()
                .map(x -> x.build(spielerFinder))
                .collect(toList());

        var neueGastSpielerStrafEreignisse = form.getGastSpielerStrafEreignisList().stream()
                .map(x -> x.build(spielerFinder))
                .collect(toList());
        var neueGastSpielerTorEreignisse = form.getGastSpielerTorEreignisList().stream()
                .map(x -> x.build(spielerFinder))
                .collect(toList());

        // vermutlich ineffizient, aber egal...
        this.heimSpielerStrafEreignisList.clear();
        this.heimSpielerTorEreignisList.clear();
        this.gastSpielerStrafEreignisList.clear();
        this.gastSpielerTorEreignisList.clear();

        this.heimSpielerStrafEreignisList.addAll(neueHeimSpielerStrafEreignisse);
        this.heimSpielerTorEreignisList.addAll(neueHeimSpielerTorEreignisse);
        this.gastSpielerStrafEreignisList.addAll(neueGastSpielerStrafEreignisse);
        this.gastSpielerTorEreignisList.addAll(neueGastSpielerTorEreignisse);

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
                .filter(SpielerTorEreignis::isInOverTime)
                .findAny();
        if (x.isPresent()) return false;
        x = this.getHeimSpielerTorEreignisList().stream()
                .filter(SpielerTorEreignis::isInOverTime)
                .findAny();
        return x.isEmpty();
    }

    /**
     * Gibt an ob in regulärer Zeit ein Sieg errungen wurde.
     * Dann gibt es 3 Punkte!
     */
    public boolean isWinInRegularTime() {
        return this.finishedInRegularTime()
                && this.getHeimSpielerTorEreignisList().size() != this.getGastSpielerTorEreignisList().size();
    }

    public int getHeimToreCountInRegTime() {
        return (int) this.heimSpielerTorEreignisList.stream()
                .filter(SpielerTorEreignis::isInRegTime)
                .count();
    }

    public int getGastToreCountInRegTime() {
        return (int) this.gastSpielerTorEreignisList.stream()
                .filter(SpielerTorEreignis::isInRegTime)
                .count();
    }

    public int getHeimToreCountInOverTime() {
        return (int) this.heimSpielerTorEreignisList.stream()
                .filter(SpielerTorEreignis::isInOverTime)
                .count();
    }

    public int getGastToreCountInOverTime() {
        return (int) this.gastSpielerTorEreignisList.stream()
                .filter(SpielerTorEreignis::isInOverTime)
                .count();
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
