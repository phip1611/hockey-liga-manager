package de.phip1611.hockeyligamanager.form;

import de.phip1611.hockeyligamanager.domain.Spieler;

import java.util.UUID;

public class SpielerForm {

    private UUID id;

    private String vorname;

    private String nachname;

    public UUID getId() {
        return id;
    }

    public SpielerForm setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getVorname() {
        return vorname;
    }

    public SpielerForm setVorname(String vorname) {
        this.vorname = vorname;
        return this;
    }

    public String getNachname() {
        return nachname;
    }

    public SpielerForm setNachname(String nachname) {
        this.nachname = nachname;
        return this;
    }

    public Spieler build() {
        return new Spieler(this);
    }
}
