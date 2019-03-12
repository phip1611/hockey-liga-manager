/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.controller;

import de.phip1611.hockeyligamanager.service.api.SpielberichtService;
import de.phip1611.hockeyligamanager.service.api.dto.LigatabellenEintragDto;
import de.phip1611.hockeyligamanager.service.api.dto.SpielberichtDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("spielbericht")
public class SpielberichtRestController {

    private SpielberichtService service;

    public SpielberichtRestController(SpielberichtService service) {
        this.service = service;
    }

    @GetMapping("all")
    public List<SpielberichtDto> getAllSpielberichte() {
        return this.service.findAll();
    }

    @GetMapping("tabelle")
    public List<LigatabellenEintragDto> getLigatabelle() {
        return this.service.erstelleLigatabelle();
    }
}
