/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.service.api;

import de.phip1611.hockeyligamanager.domain.Team;
import de.phip1611.hockeyligamanager.form.TeamForm;
import de.phip1611.hockeyligamanager.service.api.dto.TeamDto;

public interface TeamService extends EntityService<Team, TeamForm, TeamDto> {
}
