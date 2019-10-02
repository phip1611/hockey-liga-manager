/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.service.api;

import de.phip1611.hockeyligamanager.domain.Team;
import de.phip1611.hockeyligamanager.form.TeamAndSpielerForm;
import de.phip1611.hockeyligamanager.service.api.dto.ExportTeamDto;
import de.phip1611.hockeyligamanager.service.api.dto.TeamDto;

import java.util.List;

public interface TeamService extends ExAndImportEntityService<Team, TeamAndSpielerForm, TeamDto> {

    /**
     * Legt alle Team-Entitäten an und reichert sie danach mit den Spielern an. Das geht nicht in einem Schritt (im Konstruktor)
     *
     * @param teams
     */
    void importTeam(List<ExportTeamDto> teams);

}
