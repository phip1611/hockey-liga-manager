package de.phip1611.hockeyligamanager.domain;

import de.phip1611.hockeyligamanager.form.SpielerForm;
import de.phip1611.hockeyligamanager.service.api.dto.SpielerDto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Spieler {

    @Id
    private UUID id;

    private String vorname;

    private String nachname;

    @JoinColumn()
    @ManyToOne
    private Team team;

    private Spieler() {
        /* hibernate default constructor */
    }

    public Spieler(SpielerForm form) {
        this.id = form.getId() == null ? UUID.randomUUID() : form.getId();
        this.update(form);
    }

    public Spieler update(SpielerForm form) {
        this.vorname  = form.getVorname();
        this.nachname = form.getNachname();
        // aktualisiert NICHT das TEAM
        // Das Team verwaltet selbst, welche Spieler zu ihm gehören und macht
        // das den Spielern bekannt
        return this;
    }

    /**
     * Ein Team kann einem Spieler bekannt machen, dass er nun zum Team gehört.
     */
    public Spieler setTeam(Team team) {
        this.team = team;
        return this;
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

    public Team getTeam() {
        return team;
    }

    public String getFullName() {
        return vorname + " " + nachname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spieler spieler = (Spieler) o;
        return Objects.equals(vorname, spieler.vorname) &&
                Objects.equals(nachname, spieler.nachname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vorname, nachname);
    }

    public SpielerDto toDto() {
        return new SpielerDto(this);
    }
}
