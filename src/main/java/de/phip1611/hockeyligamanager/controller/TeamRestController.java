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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("team")
public class TeamRestController {

    private TeamService service;

    public TeamRestController(TeamService service) {
        this.service = service;
    }

    @GetMapping()
    public List<TeamDto> getAllTeams() {
        return this.service.findAll();
    }

    @GetMapping("{uuid}")
    public TeamDto getTeam(@PathVariable("uuid") UUID uuid) {
        return this.service.findById(uuid);
    }

}
