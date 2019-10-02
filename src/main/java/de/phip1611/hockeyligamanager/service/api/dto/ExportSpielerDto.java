/* 
   Copyright 2019 Philipp Schuster
   
   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de 
   Twitter:  @phip1611 
 */
package de.phip1611.hockeyligamanager.service.api.dto;

import de.phip1611.hockeyligamanager.domain.Spieler;

import java.util.Objects;
import java.util.UUID;

/**
 * TODO add description
 *
 * @author Philipp Schuster (@phip1611)
 * @created 2019-10-02
 */
public class ExportSpielerDto {

    private UUID id;

    private String vorname;

    private String nachname;

    private int nummer;

    // Spieler kennt sein Team nicht, da der Team-Export bereits seine Spieler anhand der UUID kennt

    // for objectmapper
    public ExportSpielerDto() {
    }

    public ExportSpielerDto(Spieler spieler) {
        this.id = spieler.getId();
        this.vorname = spieler.getVorname();
        this.nachname = spieler.getNachname();
        this.nummer = spieler.getNummer();
    }

    public UUID getId() {
        return id;
    }

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public int getNummer() {
        return nummer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExportSpielerDto that = (ExportSpielerDto) o;
        return nummer == that.nummer &&
                Objects.equals(id, that.id) &&
                Objects.equals(vorname, that.vorname) &&
                Objects.equals(nachname, that.nachname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vorname, nachname, nummer);
    }

    @Override
    public String toString() {
        return "ExportSpielerDto{" +
                "id=" + id +
                ", vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                ", nummer=" + nummer +
                '}';
    }
}
