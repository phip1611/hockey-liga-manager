/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.domain;

import de.phip1611.hockeyligamanager.form.TeamAndSpielerForm;
import de.phip1611.hockeyligamanager.service.api.dto.ExportTeamDto;
import de.phip1611.hockeyligamanager.service.api.dto.TeamDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@Entity
public class Team {

    @Id
    private UUID id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "team")
    private List<Spieler> spielerList = new ArrayList<>();

    private Team() {
        /* hibernate default constructor */
    }

    public Team(ExportTeamDto export) {
        this.id = export.getId();
        this.name = export.getName();
        // in Hiberbante funktioniert es nicht im Konstruktor auf eine andere Entität zu verweisen (außer Cascade is aktiv)
    }

    public Team(TeamAndSpielerForm form) {
        this.id = form.getId() == null ? UUID.randomUUID() : form.getId();
        this.update(form);
    }

    public Team update(TeamAndSpielerForm form) {
        this.name = form.getName();
        return this;
    }

    /**
     * Eine getrennte Methode, da wir eine Entität erst erzeugen müssen, bevor wir ihr Spieler hinzufügen.
     * Das liegt daran, dass auch die Spieler das Team kennen. Und das können sie erst, wenn es in der
     * Datenbank ist. Dadurch ist createOrUpdate der "primitiven" Properties hiervon getrennt!
     */
    public Team setSpieler(List<UUID> spielerIdList,
                           Function<UUID, Optional<Spieler>> spielerFinder) {
        var ausTeamGeflogeneSpieler = this.spielerList.stream()
                // Liste der IDs der Liste in der Entität kennt diese ID nicht --> diese wollen wir filtern
                .filter(x -> !spielerIdList.contains(x.getId()))
                .collect(toList());

        // Allen Spieler die aus dem Team geflogen sind bekannt machen,
        // dass sie aus dem Team geflogen sind
        ausTeamGeflogeneSpieler.forEach(x -> x.setTeam(null));

        this.spielerList.removeAll(ausTeamGeflogeneSpieler);

        var alleNeuenSpieler = spielerIdList.stream()
                .map(spielerFinder)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(x -> !this.spielerList.contains(x))
                .collect(toList());

        this.spielerList.addAll(alleNeuenSpieler);

        // Allen Spielern die nun neu im Team sind mitteilen, dass sie zu diesem
        // Team gehören
        alleNeuenSpieler.forEach(x -> x.setTeam(this));

        return this;
    }

    public void removeSpieler(Spieler spieler) {
        this.spielerList.remove(spieler);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Spieler> getSpielerList() {
        return spielerList;
    }

    public TeamDto toDto() {
        return new TeamDto(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(name, team.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
