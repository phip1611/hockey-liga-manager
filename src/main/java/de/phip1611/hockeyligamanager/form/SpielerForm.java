/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.form;

import de.phip1611.hockeyligamanager.domain.Spieler;
import de.phip1611.hockeyligamanager.service.api.dto.SpielerDto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;
import java.util.UUID;

public class SpielerForm implements MultipleFormField {

    private UUID id;

    @NotEmpty
    private String vorname;

    @NotEmpty
    private String nachname;

    @Min(0)
    private int nummer;

    public SpielerForm() {
        // default konstruktor für @ConfigurationProperties
    }

    public SpielerForm(SpielerDto dto) {
        this.id = dto.getId();
        this.vorname = dto.getVorname();
        this.nachname = dto.getNachname();
        this.nummer = dto.getNummer();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public int getNummer() {
        return nummer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpielerForm that = (SpielerForm) o;
        return nummer == that.nummer &&
                Objects.equals(vorname, that.vorname) &&
                Objects.equals(nachname, that.nachname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vorname, nachname, nummer);
    }

    public void setNummer(int nummer) {
        this.nummer = nummer;
    }

    public Spieler build() {
        return new Spieler(this);
    }

    @Override
    public boolean isEmpty() {
        return vorname.isEmpty() && nachname.isEmpty();
    }
}
