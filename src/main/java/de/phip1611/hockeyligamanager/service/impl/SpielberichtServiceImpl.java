/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.service.impl;

import de.phip1611.hockeyligamanager.domain.Spielbericht;
import de.phip1611.hockeyligamanager.form.SpielberichtForm;
import de.phip1611.hockeyligamanager.repository.SpielberichtRepo;
import de.phip1611.hockeyligamanager.repository.TeamRepo;
import de.phip1611.hockeyligamanager.service.api.SpielberichtService;
import de.phip1611.hockeyligamanager.service.api.dto.SpielberichtDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
public class SpielberichtServiceImpl implements SpielberichtService {

    private SpielberichtRepo repo;

    private TeamRepo teamRepo;

    public SpielberichtServiceImpl(SpielberichtRepo repo,
                                   TeamRepo teamRepo) {
        this.repo = repo;
        this.teamRepo = teamRepo;
    }

    @Override
    @Transactional
    public SpielberichtDto createOrUpdate(SpielberichtForm spielberichtForm) {
        if (spielberichtForm.getId() == null) {
            // neue Entität speichern
            return this.repo.save(spielberichtForm.build(teamRepo::findById)).toDto();
        }

        var e = this.repo.findById(spielberichtForm.getId());
        if (e.isPresent()) {
            // bestehende Entität updaten
            return e.get().update(spielberichtForm, teamRepo::findById).toDto();
        } else {
            // neue Entität mit gegebener UUID speichern
            return this.repo.save(spielberichtForm.build(teamRepo::findById)).toDto();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public SpielberichtDto findById(UUID id) {
        return this.repo.findById(id).map(Spielbericht::toDto).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SpielberichtDto> findAll() {
        return this.repo.findAll().stream().map(Spielbericht::toDto).collect(toList());
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        this.repo.deleteById(id);
    }
}
