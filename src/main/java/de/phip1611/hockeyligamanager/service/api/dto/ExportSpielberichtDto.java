/* 
   Copyright 2019 Philipp Schuster
   
   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de 
   Twitter:  @phip1611 
 */
package de.phip1611.hockeyligamanager.service.api.dto;

import de.phip1611.hockeyligamanager.domain.Spielbericht;
import de.phip1611.hockeyligamanager.domain.Spieler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

/**
 * TODO add description
 *
 * @author Philipp Schuster (@phip1611)
 * @created 2019-10-02
 */
public class ExportSpielberichtDto {

    private UUID id;

    private UUID teamHeimId;

    private UUID teamGastId;

    private List<UUID> anwesendeSpielerHeimIds;

    private List<UUID> anwesendeSpielerGastIds;

    private String schiedsrichter1;

    private String schiedsrichter2;

    private String zeitnehmer;

    private int zuschauer;

    private String ort;

    private LocalDateTime begin;

    private List<ExportSpielerTorEreignisDto> heimSpielerTorEreignisse;

    private List<ExportSpielerTorEreignisDto> gastSpielerTorEreignisse;

    private List<ExportSpielerStrafEreignisDto> heimSpielerStrafEreignisse;

    private List<ExportSpielerStrafEreignisDto> gastSpielerStrafEreignisse;

    // for objectmapper
    public ExportSpielberichtDto() {
    }

    public ExportSpielberichtDto(Spielbericht spielbericht) {
        this.id = spielbericht.getId();
        this.teamHeimId = spielbericht.getTeamHeim().getId();
        this.teamGastId = spielbericht.getTeamGast().getId();
        this.anwesendeSpielerHeimIds = spielbericht.getAnwesendeSpielerHeim().stream().map(Spieler::getId).collect(toList());
        this.anwesendeSpielerGastIds = spielbericht.getAnwesendeSpielerGast().stream().map(Spieler::getId).collect(toList());
        this.schiedsrichter1 = spielbericht.getSchiedsrichter1();
        this.schiedsrichter2 = spielbericht.getSchiedsrichter2();
        this.zeitnehmer = spielbericht.getZeitnehmer();
        this.zuschauer = spielbericht.getZuschauer();
        this.ort = spielbericht.getOrt();
        this.begin = spielbericht.getBegin();
        this.heimSpielerTorEreignisse = spielbericht.getHeimSpielerTorEreignisList().stream().map(ExportSpielerTorEreignisDto::new).collect(toList());
        this.gastSpielerTorEreignisse = spielbericht.getGastSpielerTorEreignisList().stream().map(ExportSpielerTorEreignisDto::new).collect(toList());
        this.heimSpielerStrafEreignisse = spielbericht.getHeimSpielerStrafEreignisList().stream().map(ExportSpielerStrafEreignisDto::new).collect(toList());
        this.gastSpielerStrafEreignisse = spielbericht.getGastSpielerStrafEreignisList().stream().map(ExportSpielerStrafEreignisDto::new).collect(toList());
    }

    public UUID getId() {
        return id;
    }

    public UUID getTeamHeimId() {
        return teamHeimId;
    }

    public UUID getTeamGastId() {
        return teamGastId;
    }

    public List<UUID> getAnwesendeSpielerHeimIds() {
        return anwesendeSpielerHeimIds;
    }

    public List<UUID> getAnwesendeSpielerGastIds() {
        return anwesendeSpielerGastIds;
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

    public List<ExportSpielerTorEreignisDto> getHeimSpielerTorEreignisse() {
        return heimSpielerTorEreignisse;
    }

    public List<ExportSpielerTorEreignisDto> getGastSpielerTorEreignisse() {
        return gastSpielerTorEreignisse;
    }

    public List<ExportSpielerStrafEreignisDto> getHeimSpielerStrafEreignisse() {
        return heimSpielerStrafEreignisse;
    }

    public List<ExportSpielerStrafEreignisDto> getGastSpielerStrafEreignisse() {
        return gastSpielerStrafEreignisse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExportSpielberichtDto that = (ExportSpielberichtDto) o;
        return zuschauer == that.zuschauer &&
                Objects.equals(id, that.id) &&
                Objects.equals(teamHeimId, that.teamHeimId) &&
                Objects.equals(teamGastId, that.teamGastId) &&
                Objects.equals(anwesendeSpielerHeimIds, that.anwesendeSpielerHeimIds) &&
                Objects.equals(anwesendeSpielerGastIds, that.anwesendeSpielerGastIds) &&
                Objects.equals(schiedsrichter1, that.schiedsrichter1) &&
                Objects.equals(schiedsrichter2, that.schiedsrichter2) &&
                Objects.equals(zeitnehmer, that.zeitnehmer) &&
                Objects.equals(ort, that.ort) &&
                Objects.equals(begin, that.begin) &&
                Objects.equals(heimSpielerTorEreignisse, that.heimSpielerTorEreignisse) &&
                Objects.equals(gastSpielerTorEreignisse, that.gastSpielerTorEreignisse) &&
                Objects.equals(heimSpielerStrafEreignisse, that.heimSpielerStrafEreignisse) &&
                Objects.equals(gastSpielerStrafEreignisse, that.gastSpielerStrafEreignisse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, teamHeimId, teamGastId, anwesendeSpielerHeimIds, anwesendeSpielerGastIds, schiedsrichter1, schiedsrichter2, zeitnehmer, zuschauer, ort, begin, heimSpielerTorEreignisse, gastSpielerTorEreignisse, heimSpielerStrafEreignisse, gastSpielerStrafEreignisse);
    }

    @Override
    public String toString() {
        return "ExportSpielberichtDto{" +
                "id=" + id +
                ", teamHeimId=" + teamHeimId +
                ", teamGastId=" + teamGastId +
                ", anwesendeSpielerHeimIds=" + anwesendeSpielerHeimIds +
                ", anwesendeSpielerGastIds=" + anwesendeSpielerGastIds +
                ", schiedsrichter1='" + schiedsrichter1 + '\'' +
                ", schiedsrichter2='" + schiedsrichter2 + '\'' +
                ", zeitnehmer='" + zeitnehmer + '\'' +
                ", zuschauer=" + zuschauer +
                ", ort='" + ort + '\'' +
                ", begin=" + begin +
                ", heimSpielerTorEreignisse=" + heimSpielerTorEreignisse +
                ", gastSpielerTorEreignisse=" + gastSpielerTorEreignisse +
                ", heimSpielerStrafEreignisse=" + heimSpielerStrafEreignisse +
                ", gastSpielerStrafEreignisse=" + gastSpielerStrafEreignisse +
                '}';
    }
}
