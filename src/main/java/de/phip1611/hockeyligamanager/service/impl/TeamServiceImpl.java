/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.service.impl;

import de.phip1611.hockeyligamanager.domain.Team;
import de.phip1611.hockeyligamanager.form.SpielerForm;
import de.phip1611.hockeyligamanager.form.TeamAndSpielerForm;
import de.phip1611.hockeyligamanager.repository.SpielerRepo;
import de.phip1611.hockeyligamanager.repository.TeamRepo;
import de.phip1611.hockeyligamanager.service.api.SpielerService;
import de.phip1611.hockeyligamanager.service.api.TeamService;
import de.phip1611.hockeyligamanager.service.api.dto.SpielerDto;
import de.phip1611.hockeyligamanager.service.api.dto.TeamDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
public class TeamServiceImpl implements TeamService {

    private TeamRepo repo;

    private SpielerRepo spielerRepo;

    private SpielerService spielerService;

    public TeamServiceImpl(TeamRepo repo, SpielerRepo spielerRepo,
                           SpielerService spielerService) {
        this.repo = repo;
        this.spielerRepo = spielerRepo;
        this.spielerService = spielerService;
    }

    /**
     * Schaut erst ob es noch Spieler gibt, die angelegt werden müssen.
     * Danach wird das Team angelegt und dann fügt sich das Team den
     * Spielern hinzu und das Team fügt sich die Spieler hinzu.
     */
    @Override
    @Transactional
    public TeamDto createOrUpdate(TeamAndSpielerForm form) {
        // alle Spieler die eine ID haben, aber nicht existieren in der DB
        // (bspw. bei Import der Initdaten) nun anlegen
        form.getSpieler().stream()
                .filter(x -> x.getId() != null)
                .filter(x -> !spielerRepo.existsById(x.getId()))
                .forEach(spielerService::createOrUpdate);

        // Nach dem wir sichergestellt haben, dass alle mit IDs existieren
        // speichern wir uns nun die IDs
        var teamSpielerIds = form.getSpieler().stream()
                .filter(x -> x.getId() != null)
                .map(SpielerForm::getId)
                .collect(toList());

        // Spieler die noch keine ID haben speichern
        var spielerDieNochAngelegtWerdenMuessen = form.getSpieler().stream()
                .filter(x -> x.getId() == null)
                .collect(toList());
        // diese nun anlegen
        var angelegteSpielerIds = spielerDieNochAngelegtWerdenMuessen.stream()
                .map(spielerService::createOrUpdate)
                .map(SpielerDto::getId)
                .collect(toList());

        // alle IDs von Spielern die im Team sind
        teamSpielerIds.addAll(angelegteSpielerIds);

        Team entity; // Entität speichern für später
        if (form.getId() == null) {
            // neue Entität speichern
            entity =  this.repo.save(form.build());
        } else {
            Optional<Team> entityOptional = this.repo.findById(form.getId());
            if (entityOptional.isPresent()) {
                // bestehende Entität updaten
                entity = entityOptional.get().update(form);
            } else {
                // neue Entität mit gegebener UUID speichern
                entity = this.repo.save(form.build());
            }
        }

        entity.setSpieler(teamSpielerIds, spielerRepo::findById);
        return entity.toDto();
    }

    @Override
    @Transactional(readOnly = true)
    public TeamDto findById(UUID id) {
        return this.repo.findById(id).map(Team::toDto).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeamDto> findAll() {
        return this.repo.findAll().stream().map(Team::toDto).sorted().collect(toList());
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        this.repo.deleteById(id);
    }
}
