/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.controller;

import de.phip1611.hockeyligamanager.service.api.TeamService;
import de.phip1611.hockeyligamanager.service.api.dto.TeamDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("team")
public class TeamRestController {

    private TeamService service;

    public TeamRestController(TeamService service) {
        this.service = service;
    }

    @GetMapping("all")
    public List<TeamDto> getAllTeams() {
        return this.service.findAll();
    }

}
