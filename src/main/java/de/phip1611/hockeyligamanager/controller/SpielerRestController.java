/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.controller;

import de.phip1611.hockeyligamanager.service.api.SpielerService;
import de.phip1611.hockeyligamanager.service.api.dto.SpielerDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("spieler")
public class SpielerRestController {

    private SpielerService spielerService;

    public SpielerRestController(SpielerService spielerService) {
        this.spielerService = spielerService;
    }

    @GetMapping("all")
    public List<SpielerDto> getAllSpieler() {
        return this.spielerService.findAll();
    }
}
