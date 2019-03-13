/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.domain;

import de.phip1611.hockeyligamanager.form.SpielerTorEreignisForm;
import de.phip1611.hockeyligamanager.service.api.dto.SpielerTorEreignisDto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import static de.phip1611.hockeyligamanager.domain.Spielbericht.REGULAR_GAME_DURATION;

@Entity
public class SpielerTorEreignis {

    @Id
    private UUID id;

    // Spielminute >= 0
    private int time;

    @ManyToOne
    private Spieler schuetze;

    @ManyToOne
    private Spieler firstAssist;

    @ManyToOne
    private Spieler secondAssist;

    private SpielerTorEreignis() {
        /* hibernate */
    }

    public SpielerTorEreignis(SpielerTorEreignisForm form,
                              Function<UUID, Optional<Spieler>> spielerFinder) {
        this.id = UUID.randomUUID();
        this.update(form, spielerFinder);
    }

    public SpielerTorEreignis update(SpielerTorEreignisForm form,
                                     Function<UUID, Optional<Spieler>> spielerFinder) {
        this.firstAssist  = form.getFirstAssistId() != null ?
                spielerFinder.apply(form.getFirstAssistId()).get()
                : null;
        this.secondAssist = form.getSecondAssistId() != null ?
                spielerFinder.apply(form.getSecondAssistId()).get()
                : null;
        this.schuetze = spielerFinder.apply(form.getSchuetzeId()).get();
        this.time = form.getTime();
        return this;
    }

    public UUID getId() {
        return id;
    }

    public int getTime() {
        return time;
    }

    public Spieler getSchuetze() {
        return schuetze;
    }

    public Spieler getFirstAssist() {
        return firstAssist;
    }

    public Spieler getSecondAssist() {
        return secondAssist;
    }

    public boolean isInRegTime() {
        return this.time < REGULAR_GAME_DURATION;
    }

    // Overtime oder Penaltiy Time, egal, müssen wir nicht genau unterscheiden
    public boolean isInOverTime() {
        return !this.isInRegTime();
    }

    public SpielerTorEreignisDto toDto() {
        return new SpielerTorEreignisDto(this);
    }
}
