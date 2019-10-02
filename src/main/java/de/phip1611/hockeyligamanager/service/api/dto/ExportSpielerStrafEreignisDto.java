/* 
   Copyright 2019 Philipp Schuster
   
   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de 
   Twitter:  @phip1611 
 */
package de.phip1611.hockeyligamanager.service.api.dto;

import de.phip1611.hockeyligamanager.domain.SpielerStrafEreignis;

import java.util.Objects;
import java.util.UUID;

/**
 * TODO add description
 *
 * @author Philipp Schuster (@phip1611)
 * @created 2019-10-02
 */
public class ExportSpielerStrafEreignisDto {

    private UUID id;

    private int time;

    private int rv;

    private UUID spielerId;

    private int dauer;

    public ExportSpielerStrafEreignisDto(SpielerStrafEreignis spielerStrafEreignis) {
        this.id = spielerStrafEreignis.getId();
        this.time = spielerStrafEreignis.getTime();
        this.rv = spielerStrafEreignis.getRv();
        this.spielerId = spielerStrafEreignis.getSpieler().getId();
        this.dauer = spielerStrafEreignis.getDauer();
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

    public UUID getSpielerId() {
        return spielerId;
    }

    public int getDauer() {
        return dauer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExportSpielerStrafEreignisDto that = (ExportSpielerStrafEreignisDto) o;
        return time == that.time &&
                rv == that.rv &&
                dauer == that.dauer &&
                Objects.equals(id, that.id) &&
                Objects.equals(spielerId, that.spielerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, time, rv, spielerId, dauer);
    }

    @Override
    public String toString() {
        return "ExportSpielerStrafEreignisDto{" +
                "id=" + id +
                ", time=" + time +
                ", rv=" + rv +
                ", spielerId=" + spielerId +
                ", dauer=" + dauer +
                '}';
    }
}
