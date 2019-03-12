/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.service.impl;

import de.phip1611.hockeyligamanager.domain.Spielbericht;
import de.phip1611.hockeyligamanager.domain.SpielerTorEreignis;
import de.phip1611.hockeyligamanager.domain.Team;
import de.phip1611.hockeyligamanager.form.SpielberichtForm;
import de.phip1611.hockeyligamanager.repository.SpielberichtRepo;
import de.phip1611.hockeyligamanager.repository.SpielerRepo;
import de.phip1611.hockeyligamanager.repository.TeamRepo;
import de.phip1611.hockeyligamanager.service.api.SpielberichtService;
import de.phip1611.hockeyligamanager.service.api.dto.LigatabellenEintragDto;
import de.phip1611.hockeyligamanager.service.api.dto.SpielberichtDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Service
public class SpielberichtServiceImpl implements SpielberichtService {

    private SpielberichtRepo repo;

    private TeamRepo teamRepo;

    private SpielerRepo spielerRepo;

    public SpielberichtServiceImpl(SpielberichtRepo repo,
                                   TeamRepo teamRepo,
                                   SpielerRepo spielerRepo) {
        this.repo = repo;
        this.teamRepo = teamRepo;
        this.spielerRepo = spielerRepo;
    }

    @Override
    @Transactional
    public SpielberichtDto createOrUpdate(SpielberichtForm spielberichtForm) {
        if (spielberichtForm.getId() == null) {
            // neue Entität speichern
            return this.repo.save(spielberichtForm.build(teamRepo::findById, spielerRepo::findById)).toDto();
        }

        var e = this.repo.findById(spielberichtForm.getId());
        if (e.isPresent()) {
            // bestehende Entität updaten
            return e.get().update(spielberichtForm, teamRepo::findById, spielerRepo::findById).toDto();
        } else {
            // neue Entität mit gegebener UUID speichern
            return this.repo.save(spielberichtForm.build(teamRepo::findById, spielerRepo::findById)).toDto();
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

    @Override
    @Transactional(readOnly = true)
    public List<LigatabellenEintragDto> erstelleLigatabelle() {
        var teams = this.teamRepo.findAll();

        List<LigatabellenEintragDto> entries = new ArrayList<>();

        for (Team team : teams) {
            var entry = new LigatabellenEintragDto(team.getName());
            entries.add(entry);

            // das ist jetzt nicht schön, aber es funktioniert erstmal xD
            var heimspiele = this.repo.findAllByTeamHeimId(team.getId());
            var auswaertsspiele = this.repo.findAllByTeamGastId(team.getId());

            entry.setAnzahlSpiele(heimspiele.size() + auswaertsspiele.size());

            var alleSpieleSieger = Stream.concat(heimspiele.stream(), auswaertsspiele.stream())
                    .filter(x -> x.getSiegerTeam().equals(team)).collect(toList());
            var alleSpieleVerlierer = Stream.concat(heimspiele.stream(), auswaertsspiele.stream())
                    .filter(x -> !x.getSiegerTeam().equals(team)).collect(toList());;


            var heimspieltore = getTore(heimspiele, Spielbericht::getHeimSpielerTorEreignisList);
            var ausweartsspieltore = getTore(auswaertsspiele, Spielbericht::getGastSpielerTorEreignisList);
            var heimspielGegentore = getTore(heimspiele, Spielbericht::getGastSpielerTorEreignisList);
            var ausweartsspielGegentore = getTore(auswaertsspiele, Spielbericht::getHeimSpielerTorEreignisList);
            entry.setTore(heimspieltore + ausweartsspieltore);
            entry.setGegentore(heimspielGegentore + ausweartsspielGegentore);



            var siege3P = alleSpieleSieger.stream().filter(Spielbericht::isWinInRegularTime).count();
            var siege2P = (long) alleSpieleSieger.size() - siege3P;
            var niederlagen0P = alleSpieleVerlierer.stream().filter(Spielbericht::isWinInRegularTime).count();
            var niederlagen1P = (long) alleSpieleVerlierer.size() - niederlagen0P;

            var punkte = siege3P * 3 + siege2P * 2 + niederlagen1P;
            entry.setAnzahlNiederlagen0P((int) niederlagen0P);
            entry.setAnzahlNiederlagen1P((int) niederlagen1P);
            entry.setAnzahlSiege2P((int) siege2P);
            entry.setAnzahlSiege3P((int) siege3P);
            entry.setPunkte((int) punkte);
        }

        Collections.sort(entries);
        for (int i = 0; i < entries.size(); i++) {
            entries.get(i).setPlatz(i + 1);
        }

        return entries;
    }

    private int getTore(List<Spielbericht> list, Function<Spielbericht, List<SpielerTorEreignis>> func) {
        return list.stream().map(func).map(List::size).reduce((a, b) -> a + b).orElse(0);
    }
}
