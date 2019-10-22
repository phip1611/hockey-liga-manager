/* 
   Copyright 2019 Philipp Schuster
   
   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de 
   Twitter:  @phip1611 
 */
package de.phip1611.hockeyligamanager.service.api.dto;

import de.phip1611.hockeyligamanager.domain.SpielerTorEreignis;

import java.util.Objects;
import java.util.UUID;

/**
 * TODO add description
 *
 * @author Philipp Schuster (@phip1611)
 * @created 2019-10-02
 */
public class ExportSpielerTorEreignisDto {

    private UUID id;

    private int time;

    private UUID schuetzeId;

    private UUID firstAssistId;

    // for objectmapper
    public ExportSpielerTorEreignisDto() {
    }

    public ExportSpielerTorEreignisDto(SpielerTorEreignis spielerTorEreignis) {
        this.id = spielerTorEreignis.getId();
        this.time = spielerTorEreignis.getTime();
        this.schuetzeId = spielerTorEreignis.getSchuetze().getId();

        // Werte können null sein
        if (spielerTorEreignis.getFirstAssist() != null) {
            this.firstAssistId = spielerTorEreignis.getFirstAssist().getId();
        }
    }

    public UUID getId() {
        return id;
    }

    public int getTime() {
        return time;
    }

    public UUID getSchuetzeId() {
        return schuetzeId;
    }

    public UUID getFirstAssistId() {
        return firstAssistId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExportSpielerTorEreignisDto that = (ExportSpielerTorEreignisDto) o;
        return time == that.time &&
                Objects.equals(id, that.id) &&
                Objects.equals(schuetzeId, that.schuetzeId) &&
                Objects.equals(firstAssistId, that.firstAssistId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, time, schuetzeId, firstAssistId);
    }

    @Override
    public String toString() {
        return "ExportSpielerTorEreignisDto{" +
                "id=" + id +
                ", time=" + time +
                ", schuetzeId=" + schuetzeId +
                ", firstAssistId=" + firstAssistId +
                '}';
    }
}
