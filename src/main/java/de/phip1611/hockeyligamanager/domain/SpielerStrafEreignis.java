/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.domain;

import de.phip1611.hockeyligamanager.form.SpielerStrafEreignisForm;
import de.phip1611.hockeyligamanager.service.api.dto.ExportSpielerStrafEreignisDto;
import de.phip1611.hockeyligamanager.service.api.dto.SpielerStrafEreignisDto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Entity
public class SpielerStrafEreignis implements Comparable<SpielerStrafEreignis> {

    @Id
    private UUID id;

    // Spielminute >= 0
    private int time;

    // Nummer des Regelverstoßes
    private int rv;

    @ManyToOne
    private Spieler spieler;

    private int dauer; // dauer der STrafe

    private SpielerStrafEreignis() {
        /* hibernate */
    }

    public SpielerStrafEreignis(ExportSpielerStrafEreignisDto export,
                                Function<UUID, Optional<Spieler>> spielerFinder) {
        this.id = export.getId();
        this.time = export.getTime();
        this.rv = export.getRv();
        this.dauer = export.getDauer();
        this.spieler = spielerFinder.apply(export.getSpielerId()).orElseThrow(() -> new IllegalArgumentException("Es gibt keinen Spieler mit der ID"));
    }

    public SpielerStrafEreignis(SpielerStrafEreignisForm form,
                                Function<UUID, Optional<Spieler>> spielerFinder) {
        this.id = UUID.randomUUID();
        this.update(form, spielerFinder);
    }

    public SpielerStrafEreignis update(SpielerStrafEreignisForm form,
                                       Function<UUID, Optional<Spieler>> spielerFinder) {
        this.dauer = form.getDauer();
        this.rv = form.getRv();
        this.time = form.getTime();
        this.spieler = spielerFinder.apply(form.getSpielerId()).orElseThrow(() -> new IllegalArgumentException("An einem Strafereignis muss ein valider Spieler hängen!"));
        return this;
    }

    public UUID getId() {
        return id;
    }

    public int getTime() {
        return time;
    }

    public int getRv() {
        return rv;
    }

    public Spieler getSpieler() {
        return spieler;
    }

    public int getDauer() {
        return dauer;
    }

    public SpielerStrafEreignisDto toDto() {
        return new SpielerStrafEreignisDto(this);
    }

    @Override
    public int compareTo(SpielerStrafEreignis o) {
        return this.time - o.time;
    }
}
