package de.phip1611.hockeyligamanager;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Spieler {

    @Id
    private UUID id;

    private String vorname;

    private String nachname;

    public Spieler() {
        this.id = UUID.randomUUID();
    }

    public Spieler(String vorname, String nachname) {
        this();
        this.vorname = vorname;
        this.nachname = nachname;
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
}
