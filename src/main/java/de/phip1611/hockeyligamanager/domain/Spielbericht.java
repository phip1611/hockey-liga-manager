/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.domain;

import de.phip1611.hockeyligamanager.form.SpielberichtForm;
import de.phip1611.hockeyligamanager.service.api.dto.ExportSpielberichtDto;
import de.phip1611.hockeyligamanager.service.api.dto.SpielberichtDto;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Entity
public class Spielbericht {

    public final static int REGULAR_GAME_DURATION = 60;

    @Id
    private UUID id;

    @JoinColumn
    @ManyToOne
    private Team teamHeim;

    @JoinColumn
    @ManyToOne
    private Team teamGast;

    @ManyToMany
    private List<Spieler> anwesendeSpielerHeim = new ArrayList<>();

    @ManyToMany
    private List<Spieler> anwesendeSpielerGast = new ArrayList<>();

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

    public Spielbericht(ExportSpielberichtDto export,
                        Function<UUID, Optional<Team>> teamFinder,
                        Function<UUID, Optional<Spieler>> spielerFinder) {
        this.id = export.getId();
        this.teamHeim = teamFinder.apply(export.getTeamHeimId()).orElseThrow();
        this.teamGast = teamFinder.apply(export.getTeamGastId()).orElseThrow();
        this.schiedsrichter1 = export.getSchiedsrichter1();
        this.schiedsrichter2 = export.getSchiedsrichter2();
        this.zeitnehmer = export.getZeitnehmer();
        this.ort = export.getOrt();
        this.zuschauer = export.getZuschauer();
        this.begin = export.getBegin();
        this.anwesendeSpielerHeim = export.getAnwesendeSpielerHeimIds().stream().map(spielerFinder).map(Optional::orElseThrow).collect(toList());
        this.anwesendeSpielerGast = export.getAnwesendeSpielerGastIds().stream().map(spielerFinder).map(Optional::orElseThrow).collect(toList());
        this.heimSpielerStrafEreignisList = export.getHeimSpielerStrafEreignisse().stream().map(spieler -> new SpielerStrafEreignis(spieler, spielerFinder)).collect(toList());
        this.gastSpielerStrafEreignisList = export.getGastSpielerStrafEreignisse().stream().map(spieler -> new SpielerStrafEreignis(spieler, spielerFinder)).collect(toList());

        this.heimSpielerTorEreignisList = export.getHeimSpielerTorEreignisse().stream().map(spieler -> new SpielerTorEreignis(spieler, spielerFinder)).collect(toList());
        this.gastSpielerTorEreignisList = export.getGastSpielerTorEreignisse().stream().map(spieler -> new SpielerTorEreignis(spieler, spielerFinder)).collect(toList());
    }

    public Spielbericht(SpielberichtForm form,
                        Function<UUID, Optional<Team>> teamFinder,
                        Function<UUID, Optional<Spieler>> spielerFinder,
                        DateTimeFormatter formatter) {
        this.id = form.getId() == null ? UUID.randomUUID() : form.getId();
        this.update(form, teamFinder, spielerFinder, formatter);
    }

    public Spielbericht update(SpielberichtForm form,
                               Function<UUID, Optional<Team>> teamFinder,
                               Function<UUID, Optional<Spieler>> spielerFinder,
                               DateTimeFormatter formatter) {
        this.teamHeim = teamFinder.apply(form.getTeamHeimId()).get();
        this.teamGast = teamFinder.apply(form.getTeamGastId()).get();
        this.schiedsrichter1 = form.getSchiedsrichter1();
        this.schiedsrichter2 = form.getSchiedsrichter2();
        this.zeitnehmer = form.getZeitnehmer();
        this.zuschauer = form.getZuschauer();
        this.ort = form.getOrt();
        this.begin = form.getBeginTimeString() != null ? LocalDateTime.parse(form.getBeginTimeString(), formatter) : null;

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
        this.anwesendeSpielerGast.clear();
        this.anwesendeSpielerHeim.clear();
        this.anwesendeSpielerHeim.addAll(
                form.getAnwesendeSpielerHeim().stream()
                        .map(spielerFinder)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(toList())
        );
        this.anwesendeSpielerGast.addAll(
                form.getAnwesendeSpielerGast().stream()
                        .map(spielerFinder)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(toList())
        );

        // vermutlich ineffizient, aber egal...
        this.heimSpielerStrafEreignisList.clear();
        this.heimSpielerTorEreignisList.clear();
        this.gastSpielerStrafEreignisList.clear();
        this.gastSpielerTorEreignisList.clear();

        this.heimSpielerStrafEreignisList.addAll(neueHeimSpielerStrafEreignisse);
        this.heimSpielerTorEreignisList.addAll(neueHeimSpielerTorEreignisse);
        this.gastSpielerStrafEreignisList.addAll(neueGastSpielerStrafEreignisse);
        this.gastSpielerTorEreignisList.addAll(neueGastSpielerTorEreignisse);

        // Alle Spieler die Tor- oder Strafereignisse bekommen haben, werden sofern nicht angegeben
        // den Anwesenden Spielern hinzugefügt
        // falls das im UI vergessen wurde; es ist an der Stelle aber logisch die Spieler als
        // Anwesend zu markieren und nimmt sogar Arbeit im Formular ab :)
        Stream.concat(
                this.heimSpielerStrafEreignisList.stream().map(SpielerStrafEreignis::getSpieler),
                this.heimSpielerTorEreignisList.stream().map(SpielerTorEreignis::getSchuetze)
        ).distinct().forEach(heimSpielerMitEreignis -> {
            if (!this.anwesendeSpielerHeim.contains(heimSpielerMitEreignis)) {
                this.anwesendeSpielerHeim.add(heimSpielerMitEreignis);
            }
        });
        Stream.concat(
                this.gastSpielerStrafEreignisList.stream().map(SpielerStrafEreignis::getSpieler),
                this.gastSpielerTorEreignisList.stream().map(SpielerTorEreignis::getSchuetze)
        ).distinct().forEach(gastSpielerMitEreignis -> {
            if (!this.anwesendeSpielerGast.contains(gastSpielerMitEreignis)) {
                this.anwesendeSpielerGast.add(gastSpielerMitEreignis);
            }
        });

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

    public List<Spieler> getAnwesendeSpielerHeim() {
        return anwesendeSpielerHeim;
    }

    public List<Spieler> getAnwesendeSpielerGast() {
        return anwesendeSpielerGast;
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
        return heimSpielerTorEreignisList.stream().sorted().collect(toList());
    }

    public List<SpielerStrafEreignis> getHeimSpielerStrafEreignisList() {
        return heimSpielerStrafEreignisList.stream().sorted().collect(toList());
    }

    public List<SpielerTorEreignis> getGastSpielerTorEreignisList() {
        return gastSpielerTorEreignisList.stream().sorted().collect(toList());
    }

    public List<SpielerStrafEreignis> getGastSpielerStrafEreignisList() {
        return gastSpielerStrafEreignisList.stream().sorted().collect(toList());
    }

    public SpielberichtDto toDto() {
        return new SpielberichtDto(this);
    }
}
