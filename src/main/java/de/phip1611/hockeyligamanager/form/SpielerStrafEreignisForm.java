/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.form;

import de.phip1611.hockeyligamanager.domain.Spieler;
import de.phip1611.hockeyligamanager.domain.SpielerStrafEreignis;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public class SpielerStrafEreignisForm implements MultipleFormField {

    private UUID id;

    // Spielminute >= 0
    @Min(0)
    private int time;

    // Nummer des Regelverstoßes
    @Min(0)
    private int rv;

    @NotNull
    private UUID spielerId;

    // dauer der Strafe in Minuten
    @Min(1)
    private int dauer = 1; // Damit der Wert im Formular mit dem min=""-Feld übereinstimmt

    public SpielerStrafEreignisForm() {
    }

    public SpielerStrafEreignisForm(SpielerStrafEreignis entity) {
        this.id = entity.getId();
        this.time = entity.getTime();
        this.rv = entity.getRv();
        this.spielerId = entity.getSpieler().getId();
        this.dauer = entity.getDauer();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getRv() {
        return rv;
    }

    public void setRv(int rv) {
        this.rv = rv;
    }

    public int getDauer() {
        return dauer;
    }

    public void setDauer(int dauer) {
        this.dauer = dauer;
    }

    public UUID getSpielerId() {
        return spielerId;
    }

    public void setSpielerId(UUID spielerId) {
        this.spielerId = spielerId;
    }

    public SpielerStrafEreignis build(Function<UUID, Optional<Spieler>> spielerFinder) {
        return new SpielerStrafEreignis(this, spielerFinder);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpielerStrafEreignisForm that = (SpielerStrafEreignisForm) o;
        return time == that.time &&
                rv == that.rv &&
                dauer == that.dauer &&
                Objects.equals(spielerId, that.spielerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, rv, spielerId, dauer);
    }

    @Override
    public boolean isEmpty() {
        return spielerId == null;
    }
}
