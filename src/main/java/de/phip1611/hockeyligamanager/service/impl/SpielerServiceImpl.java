/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.service.impl;

import de.phip1611.hockeyligamanager.domain.Spieler;
import de.phip1611.hockeyligamanager.domain.SpielerTorEreignis;
import de.phip1611.hockeyligamanager.form.SpielerForm;
import de.phip1611.hockeyligamanager.repository.SpielberichtRepo;
import de.phip1611.hockeyligamanager.repository.SpielerRepo;
import de.phip1611.hockeyligamanager.repository.SpielerTorEreignisRepo;
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

    private SpielerTorEreignisRepo spielerTorEreignisRepo;

    public SpielerServiceImpl(SpielerRepo repo,
                              SpielberichtRepo spielberichtRepo,
                              SpielerTorEreignisRepo spielerTorEreignisRepo) {
        this.repo = repo;
        this.spielerTorEreignisRepo = spielerTorEreignisRepo;
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
    @Transactional
    public void deleteById(UUID id) {
        var o = this.repo.findById(id);
        if (o.isEmpty()) return;
        var e = o.get();
        // aus team entfernen
        e.getTeam().removeSpieler(e);
        // sich als first assist überall entfernen
        this.spielerTorEreignisRepo.findAllByFirstAssist(e).forEach(SpielerTorEreignis::invalidateFirstAssist);
        // die Tore bleiben erhalten, müssen dann aber einem neuen Spieler zugrodnet werden
        this.spielerTorEreignisRepo.findAllBySchuetze(e).forEach(SpielerTorEreignis::invalidateSchuetze);

        // den Nutzer selbst löschen
        this.repo.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAll() {
        this.repo.findAll().stream().map(Spieler::getId).forEach(this::deleteById);
    }

    @Override
    @Transactional
    public void saveAll(Iterable<Spieler> iterable) {
        this.repo.saveAll(iterable);
    }
}
