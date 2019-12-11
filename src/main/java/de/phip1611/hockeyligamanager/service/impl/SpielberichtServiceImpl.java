/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.service.impl;

import de.phip1611.hockeyligamanager.domain.*;
import de.phip1611.hockeyligamanager.form.SpielberichtForm;
import de.phip1611.hockeyligamanager.repository.*;
import de.phip1611.hockeyligamanager.service.api.SpielberichtService;
import de.phip1611.hockeyligamanager.service.api.dto.LigatabellenEintragDto;
import de.phip1611.hockeyligamanager.service.api.dto.SchuetzenTabellenEintragDto;
import de.phip1611.hockeyligamanager.service.api.dto.SpielberichtDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.*;
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

    private DateTimeFormatter formatter;

    public SpielberichtServiceImpl(SpielberichtRepo repo,
                                   TeamRepo teamRepo,
                                   SpielerRepo spielerRepo,
                                   SpielerTorEreignisRepo spielerTorEreignisRepo,
                                   SpielerStrafEreignisRepo spielerStrafEreignisRepo,
                                   DateTimeFormatter formatter) {
        this.repo = repo;
        this.teamRepo = teamRepo;
        this.spielerRepo = spielerRepo;
        this.spielerTorEreignisRepo = spielerTorEreignisRepo;
        this.spielerStrafEreignisRepo = spielerStrafEreignisRepo;
        this.formatter = formatter;
    }

    @Override
    @Transactional
    public SpielberichtDto createOrUpdate(SpielberichtForm form) {
        form.removeEmptyFields();

        if (form.getTeamHeimId().equals(form.getTeamGastId())) {
            throw new IllegalArgumentException("Heim-Team und Gast-Team sind gleich!");
        }

        if (form.getId() == null) {
            // neue Entität speichern
            return this.repo.save(form.build(teamRepo::findById, spielerRepo::findById, formatter)).toDto();
        }

        var e = this.repo.findById(form.getId());
        if (e.isPresent()) {
            // bestehende Entität updaten
            return e.get().update(form, teamRepo::findById, spielerRepo::findById, formatter).toDto();
        } else {
            // neue Entität mit gegebener UUID speichern
            return this.repo.save(form.build(teamRepo::findById, spielerRepo::findById, formatter)).toDto();
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
        return erstelleLigatabelle(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LigatabellenEintragDto> erstelleLigatabelle(String sort) {
        var teams = this.teamRepo.findAll();

        List<LigatabellenEintragDto> entries = new ArrayList<>();

        for (Team team : teams) {
            var entry = new LigatabellenEintragDto();
            entries.add(entry);

            // das ist jetzt nicht schön, aber es funktioniert erstmal xD
            entry.setTeamName(team.getName());

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


            var toreProSpiel = entry.getAnzahlSpiele() == 0 ? 0
                    : entry.getTore() / (float)entry.getAnzahlSpiele();
            var gegenToreProSpiel = entry.getAnzahlSpiele() == 0 ? 0
                    : entry.getGegentore() / (float)entry.getAnzahlSpiele();
            entry.setToreProSpiel(toreProSpiel);
            entry.setGegentoreProSpiel(gegenToreProSpiel);
        }

        // Sortieren nun einmal "normal" um den Platz bestimmen zu können
        Collections.sort(entries);
        for (int i = 0; i < entries.size(); i++) {
            entries.get(i).setPlatz(i + 1);
        }

        // und jetzt nach dem alle properties gesetzt sind "richtig"
        if (sort != null) {
            entries.sort(getLigatabelleComparator(sort));
        }

        return entries;
    }

    @Override
    @Transactional
    public List<SchuetzenTabellenEintragDto> erstelleSchuetzentabelle(Optional<UUID> filterTeam,
                                                                      Optional<String> sortProperty) {
        List<SchuetzenTabellenEintragDto> rows = new ArrayList<>();
        var tore = spielerTorEreignisRepo.findAll();
        var strafen = spielerStrafEreignisRepo.findAll();
        var spiele = repo.findAll(); // laden an der stelle alle, da wir alle brauchen für den Bericht

        List<Spieler> alleSpieler = filterTeam.map(spielerRepo::findAllByTeamId)
                .orElseGet(() -> spielerRepo.findAll());
        for (Spieler spieler : alleSpieler) {
            var row = new SchuetzenTabellenEintragDto(spieler);
            rows.add(row);

            var torespieler = tore.stream().filter(x -> x.getSchuetze().equals(spieler)).count();
            var firstassistspieler = tore.stream()
                    .map(SpielerTorEreignis::getFirstAssist)
                    .filter(Objects::nonNull)
                    .filter(x -> x.equals(spieler))
                    .count();
            row.setFirstAssist((int) firstassistspieler);
            var strafenspieler = strafen.stream().filter(x -> x.getSpieler().equals(spieler)).collect(toList());
            row.setTore((int) torespieler);
            row.setStrafen(strafenspieler.size());
            row.setStrafMinuten(strafenspieler.stream()
                    .map(SpielerStrafEreignis::getDauer)
                    .reduce(Integer::sum).orElse(0)
            );

            row.setAnzahlSpiele(getAnzahlSpieleVonSpieler(spiele, spieler.getId()));
        }
        if (sortProperty.isEmpty()) {
            Collections.sort(rows);
        } else {
            rows.sort(getSchuetzenComparator(sortProperty.get()));
        }
        return rows;
    }

    @Override
    @Transactional(readOnly = true)
    public SpielberichtForm createFormFromId(UUID id) {
        var entity = repo.findById(id).get();
        return new SpielberichtForm(entity, formatter);
    }

    private int getTore(List<Spielbericht> list, Function<Spielbericht, List<SpielerTorEreignis>> func) {
        return list.stream().map(func).map(List::size).reduce(Integer::sum).orElse(0);
    }

    private int getAnzahlSpieleVonSpieler(List<Spielbericht> alleSpiele, UUID spieler) {
        return (int) alleSpiele.stream().filter(spiel -> {
            var spielteHeimSpiel = spiel.getAnwesendeSpielerHeim().stream()
                    .map(Spieler::getId)
                    .anyMatch(id -> id.equals(spieler));
            var spielteGastSpiel = spiel.getAnwesendeSpielerGast().stream()
                    .map(Spieler::getId)
                    .anyMatch(id -> id.equals(spieler));
            return  spielteHeimSpiel || spielteGastSpiel;
        }).count();
    }

    private Comparator<SchuetzenTabellenEintragDto> getSchuetzenComparator(String sortProperty) {
        switch (sortProperty) {
            case "nachname": {
                return Comparator.comparing(SchuetzenTabellenEintragDto::getNachname);
            }
            case "vorname": {
                return Comparator.comparing(SchuetzenTabellenEintragDto::getVorname);
            }
            case "team": {
                return Comparator.comparing(SchuetzenTabellenEintragDto::getTeamName);
            }
            case "tore": {
                return (o1, o2) -> o2.getTore() - o1.getTore();
            }
            case "torejespiel": {
                return (o1, o2) -> (int) (o2.getToreJeSpiel() - o1.getToreJeSpiel());
            }
            case "spiele": {
                return (o1, o2) -> o2.getAnzahlSpiele() - o1.getAnzahlSpiele();
            }
            case "1ass": {
                return (o1, o2) -> o2.getFirstAssist() - o1.getFirstAssist();
            }
            case "strafminuten": {
                // nach anzahl strafen, nicht anzahl der gesamtminuten
                return (o1, o2) -> o2.getStrafMinuten() - o1.getStrafMinuten();
            }
            default:
                return (o1, o2) -> 0;
        }
    }

    private Comparator<LigatabellenEintragDto> getLigatabelleComparator(String sortProperty) {
        switch (sortProperty) {
            case "#": {
                return Comparator.comparingInt(LigatabellenEintragDto::getPlatz);
            }
            case "team": {
                return Comparator.comparing(LigatabellenEintragDto::getTeamName);
            }
            case "spiele": {
                return (o1, o2) -> o2.getAnzahlSpiele() - o1.getAnzahlSpiele();
            }
            case "s3": {
                return (o1, o2) -> o2.getAnzahlSiege3P() - o1.getAnzahlSiege3P();
            }
            case "s2": {
                return (o1, o2) -> o2.getAnzahlSiege2P() - o1.getAnzahlSiege2P();
            }
            case "n1": {
                return (o1, o2) -> o2.getAnzahlNiederlagen1P() - o1.getAnzahlNiederlagen1P();
            }
            case "n0": {
                return (o1, o2) -> o2.getAnzahlNiederlagen0P() - o1.getAnzahlNiederlagen0P();
            }
            case "t": {
                return (o1, o2) -> o2.getTore() - o1.getTore();
            }
            case "t_pro_spiel": {
                return (o1, o2) -> Double.compare(o2.getToreProSpiel(), o1.getToreProSpiel());
            }
            case "gt": {
                return (o1, o2) -> o2.getGegentore() - o1.getGegentore();
            }
            case "gt_pro_spiel": {
                return (o1, o2) -> Double.compare(o2.getGegentoreProSpiel(), o1.getGegentoreProSpiel());
            }
            case "punkte": {
                return (o1, o2) -> o2.getPunkte() - o1.getPunkte();
            }
            default:
                return (o1, o2) -> 0;
        }
    }
}
