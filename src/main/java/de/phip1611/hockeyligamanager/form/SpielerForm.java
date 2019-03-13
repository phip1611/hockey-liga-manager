/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.form;

import de.phip1611.hockeyligamanager.domain.Spieler;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

public class SpielerForm {

    private UUID id;

    @NotEmpty
    private String vorname;

    @NotEmpty
    private String nachname;

    @Min(0)
    private int nummer;

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

    public void setNummer(int nummer) {
        this.nummer = nummer;
    }

    public Spieler build() {
        return new Spieler(this);
    }
}
