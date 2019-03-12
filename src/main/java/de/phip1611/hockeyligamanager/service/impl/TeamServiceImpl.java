/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.service.impl;

import de.phip1611.hockeyligamanager.domain.Team;
import de.phip1611.hockeyligamanager.form.TeamForm;
import de.phip1611.hockeyligamanager.repository.SpielerRepo;
import de.phip1611.hockeyligamanager.repository.TeamRepo;
import de.phip1611.hockeyligamanager.service.api.TeamService;
import de.phip1611.hockeyligamanager.service.api.dto.TeamDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
public class TeamServiceImpl implements TeamService {

    private TeamRepo repo;

    private SpielerRepo spielerRepo;

    public TeamServiceImpl(TeamRepo repo, SpielerRepo spielerRepo) {
        this.repo = repo;
        this.spielerRepo = spielerRepo;
    }

    @Override
    @Transactional
    public TeamDto createOrUpdate(TeamForm teamForm) {
        if (teamForm.getId() == null) {
            // neue Entität speichern
            return this.repo.save(teamForm.build())
                    .setSpieler(teamForm.getSpielerIdList(), spielerRepo::findById)
                    .toDto();
        }

        var e = this.repo.findById(teamForm.getId());
        if (e.isPresent()) {
            // bestehende Entität updaten
            return e.get().update(teamForm)
                    .setSpieler(teamForm.getSpielerIdList(), spielerRepo::findById)
                    .toDto();
        } else {
            // neue Entität mit gegebener UUID speichern
            return this.repo.save(teamForm.build())
                    .setSpieler(teamForm.getSpielerIdList(), spielerRepo::findById)
                    .toDto();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TeamDto findById(UUID id) {
        return this.repo.findById(id).map(Team::toDto).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeamDto> findAll() {
        return this.repo.findAll().stream().map(Team::toDto).collect(toList());
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        this.repo.deleteById(id);
    }
}
