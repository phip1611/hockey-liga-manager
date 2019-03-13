/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.service.impl;

import de.phip1611.hockeyligamanager.domain.Spieler;
import de.phip1611.hockeyligamanager.form.SpielerForm;
import de.phip1611.hockeyligamanager.repository.SpielerRepo;
import de.phip1611.hockeyligamanager.service.api.SpielerService;
import de.phip1611.hockeyligamanager.service.api.dto.SpielerDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
public class SpielerServiceImpl implements SpielerService {

    private SpielerRepo repo;

    public SpielerServiceImpl(SpielerRepo repo) {
        this.repo = repo;
    }

    @Override
    @Transactional
    public SpielerDto createOrUpdate(SpielerForm form) {
        if (form.getId() == null) {
            // neue Entität speichern
            return this.repo.save(form.build()).toDto();
        }

        var e = this.repo.findById(form.getId());
        if (e.isPresent()) {
            // bestehende Entität updaten
            return e.get().update(form).toDto();
        } else {
            // neue Entität mit gegebener UUID speichern
            return this.repo.save(form.build()).toDto();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public SpielerDto findById(UUID id) {
        return this.repo.findById(id).map(Spieler::toDto).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SpielerDto> findAll() {
        return this.repo.findAll().stream().map(Spieler::toDto).sorted().collect(toList());
    }

    @Override
    public void deleteById(UUID id) {
        this.repo.deleteById(id);
    }
}
