/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.service.impl;

import de.phip1611.hockeyligamanager.domain.*;
import de.phip1611.hockeyligamanager.form.SpielberichtForm;
import de.phip1611.hockeyligamanager.form.SpielerStrafEreignisForm;
import de.phip1611.hockeyligamanager.form.SpielerTorEreignisForm;
import de.phip1611.hockeyligamanager.repository.*;
import de.phip1611.hockeyligamanager.service.api.SpielberichtService;
import de.phip1611.hockeyligamanager.service.api.dto.LigatabellenEintragDto;
import de.phip1611.hockeyligamanager.service.api.dto.SchuetzenTabellenEintragDto;
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

    private SpielerTorEreignisRepo spielerTorEreignisRepo;

    private SpielerStrafEreignisRepo spielerStrafEreignisRepo;

    public SpielberichtServiceImpl(SpielberichtRepo repo,
                                   TeamRepo teamRepo,
                                   SpielerRepo spielerRepo,
                                   SpielerTorEreignisRepo spielerTorEreignisRepo,
                                   SpielerStrafEreignisRepo spielerStrafEreignisRepo) {
        this.repo = repo;
        this.teamRepo = teamRepo;
        this.spielerRepo = spielerRepo;
        this.spielerTorEreignisRepo = spielerTorEreignisRepo;
        this.spielerStrafEreignisRepo = spielerStrafEreignisRepo;
    }

    @Override
    @Transactional
    public SpielberichtDto createOrUpdate(SpielberichtForm form) {
        form.removeEmptyFields();

        if (form.getId() == null) {
            // neue Entität speichern
            return this.repo.save(form.build(teamRepo::findById, spielerRepo::findById)).toDto();
        }

        var e = this.repo.findById(form.getId());
        if (e.isPresent()) {
            // bestehende Entität updaten
            return e.get().update(form, teamRepo::findById, spielerRepo::findById).toDto();
        } else {
            // neue Entität mit gegebener UUID speichern
            return this.repo.save(form.build(teamRepo::findById, spielerRepo::findById)).toDto();
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
        return this.repo.findAll().stream().map(Spielbericht::toDto).sorted().collect(toList());
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        this.repo.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAll() {
        this.repo.deleteAll();;
    }

    @Override
    @Transactional
    public void saveAll(Iterable<Spielbericht> iterable) {
        this.repo.saveAll(iterable);
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

    @Override
    @Transactional
    public List<SchuetzenTabellenEintragDto> erstelleSchuetzentabelle() {
        List<SchuetzenTabellenEintragDto> rows = new ArrayList<>();
        var tore = spielerTorEreignisRepo.findAll();
        var strafen = spielerStrafEreignisRepo.findAll();
        for (Spieler spieler : spielerRepo.findAll()) {
            var row = new SchuetzenTabellenEintragDto(spieler);
            rows.add(row);

            var torespieler = tore.stream().filter(x -> x.getSchuetze().equals(spieler)).count();
            var firstassistspieler = tore.stream()
                    .filter(x -> x.getFirstAssist() != null)
                    .filter(x -> x.getFirstAssist().equals(spieler))
                    .count();
            var secondassistspieler = tore.stream()
                    .filter(x -> x.getSecondAssist() != null)
                    .filter(x -> x.getSecondAssist().equals(spieler))
                    .count();
            var strafenspieler = strafen.stream().filter(x -> x.getSpieler().equals(spieler)).collect(toList());
            row.setFirstAssist((int) firstassistspieler);
            row.setSecondAssist((int) secondassistspieler);
            row.setTore((int) torespieler);
            row.setStrafen(strafenspieler.size());
            row.setStrafMinuten((int) strafenspieler.stream().map(SpielerStrafEreignis::getDauer).count());
        }
        Collections.sort(rows);
        return rows;
    }

    @Override
    @Transactional(readOnly = true)
    public SpielberichtForm createFormFromId(UUID id) {
        var entity = repo.findById(id).get();
        var form = new SpielberichtForm();
        form.setId(id);
        form.setTeamGastId(entity.getTeamGast().getId());
        form.setTeamHeimId(entity.getTeamHeim().getId());
        form.setSchiedsrichter1(entity.getSchiedsrichter1());
        form.setSchiedsrichter2(entity.getSchiedsrichter2());
        form.setZeitnehmer(entity.getZeitnehmer());
        form.setZuschauer(entity.getZuschauer());
        form.setOrt(entity.getOrt());
        form.setBeginTimeString(entity.getBegin().toString());
        form.setAnwesendeSpielerGast(entity.getAnwesendeSpielerGast().stream().map(Spieler::getId).collect(toList()));
        form.setAnwesendeSpielerHeim(entity.getAnwesendeSpielerHeim().stream().map(Spieler::getId).collect(toList()));
        form.setHeimSpielerTorEreignisList(entity.getHeimSpielerTorEreignisList().stream().map(SpielerTorEreignisForm::new).collect(toList()));
        form.setGastSpielerTorEreignisList(entity.getGastSpielerTorEreignisList().stream().map(SpielerTorEreignisForm::new).collect(toList()));
        form.setHeimSpielerStrafEreignisList(entity.getHeimSpielerStrafEreignisList().stream().map(SpielerStrafEreignisForm::new).collect(toList()));
        form.setGastSpielerStrafEreignisList(entity.getGastSpielerStrafEreignisList().stream().map(SpielerStrafEreignisForm::new).collect(toList()));
        return form;
    }

    private int getTore(List<Spielbericht> list, Function<Spielbericht, List<SpielerTorEreignis>> func) {
        return list.stream().map(func).map(List::size).reduce(Integer::sum).orElse(0);
    }
}
