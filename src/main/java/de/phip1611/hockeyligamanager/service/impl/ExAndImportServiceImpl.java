/* 
   Copyright 2019 Philipp Schuster
   
   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de 
   Twitter:  @phip1611 
 */
package de.phip1611.hockeyligamanager.service.impl;

import de.phip1611.hockeyligamanager.domain.Spielbericht;
import de.phip1611.hockeyligamanager.domain.Spieler;
import de.phip1611.hockeyligamanager.repository.*;
import de.phip1611.hockeyligamanager.service.api.ExAndImportService;
import de.phip1611.hockeyligamanager.service.api.SpielberichtService;
import de.phip1611.hockeyligamanager.service.api.SpielerService;
import de.phip1611.hockeyligamanager.service.api.TeamService;
import de.phip1611.hockeyligamanager.service.api.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;

/**
 * TODO add description
 *
 * @author Philipp Schuster (@phip1611)
 * @created 2019-10-02
 */
@Service
public class ExAndImportServiceImpl implements ExAndImportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExAndImportServiceImpl.class);

    private SpielerRepo spielerRepo;

    private TeamRepo teamRepo;

    private SpielberichtRepo spielberichtRepo;

    private SpielerStrafEreignisRepo spielerStrafEreignisRepo;

    private SpielerTorEreignisRepo spielerTorEreignisRepo;

    private SpielerService spielerService;

    private TeamService teamService;

    private SpielberichtService spielberichtService;

    public ExAndImportServiceImpl(SpielerRepo spielerRepo,
                                  TeamRepo teamRepo,
                                  SpielberichtRepo spielberichtRepo,
                                  SpielerStrafEreignisRepo spielerStrafEreignisRepo,
                                  SpielerTorEreignisRepo spielerTorEreignisRepo,
                                  SpielerService spielerService,
                                  TeamService teamService,
                                  SpielberichtService spielberichtService) {
        this.spielerRepo = spielerRepo;
        this.teamRepo = teamRepo;
        this.spielberichtRepo = spielberichtRepo;
        this.spielerStrafEreignisRepo = spielerStrafEreignisRepo;
        this.spielerTorEreignisRepo = spielerTorEreignisRepo;
        this.spielerService = spielerService;
        this.teamService = teamService;
        this.spielberichtService = spielberichtService;
    }

    @Override
    @Transactional(readOnly = true)
    public ExportDto exportData() {
        LOGGER.info("Export");
        return new ExportDto(
                this.findAllAndMap(spielerRepo::findAll, ExportSpielerDto::new),
                this.findAllAndMap(spielberichtRepo::findAll, ExportSpielberichtDto::new),
                this.findAllAndMap(spielerStrafEreignisRepo::findAll, ExportSpielerStrafEreignisDto::new),
                this.findAllAndMap(spielerTorEreignisRepo::findAll, ExportSpielerTorEreignisDto::new),
                this.findAllAndMap(teamRepo::findAll, ExportTeamDto::new)
        );
    }

    private <T, E> List<E> findAllAndMap(Supplier<List<T>> supplier, Function<T, E> mapper) {
        return supplier.get().stream().map(mapper).collect(toList());
    }

    @Override
    //@Transactional NICHT, da die einzeln aufgerufenen unter Methoden einzelne Transaktionen sein sollen
    public void importData(ExportDto dto) {
        LOGGER.info("Import started");
        // da am spielbereicht cascade all dran ist geht das auch so this.spielerTorEreignisRepo.deleteAll();
        // this.spielerStrafEreignisRepo.deleteAll();
        /*this.spielberichtRepo.deleteAll();
        this.teamRepo.deleteAll();
        this.spielerRepo.deleteAll();*/
        this.spielberichtService.deleteAll();
        this.teamService.deleteAll();
        // wenn ein team gelöscht wird, werden alle seine spieler auch gelöscht
        // this.spielerService.deleteAll();

        this.spielerService.saveAll(dto.getSpieler().stream().map(Spieler::new).collect(toList()));
        //this.teamService.saveAll(dto.getTeams().stream().map(Team::new).collect(toList()));
        this.teamService.importTeam(dto.getTeams());

        this.spielberichtService.saveAll(dto.getSpielberichte().stream().map(bericht -> new Spielbericht(bericht, teamRepo::findById, spielerRepo::findById)).collect(toList()));
        LOGGER.info("Import done");

    }
}
