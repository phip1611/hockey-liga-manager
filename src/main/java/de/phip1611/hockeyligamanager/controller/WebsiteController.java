/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.controller;

import de.phip1611.hockeyligamanager.service.api.SpielberichtService;
import de.phip1611.hockeyligamanager.service.api.SpielerService;
import de.phip1611.hockeyligamanager.service.api.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Controller
public class WebsiteController {

    private SpielberichtService spielberichtService;

    private TeamService teamService;

    private SpielerService spielerService;

    public WebsiteController(SpielberichtService spielberichtService,
                             TeamService teamService,
                             SpielerService spielerService) {
        this.spielberichtService = spielberichtService;
        this.teamService = teamService;
        this.spielerService = spielerService;
    }

    @GetMapping("")
    public String welcome() {
        return "redirect:/ligatabelle";
    }

    @GetMapping("ligatabelle")
    public String ligatabelle(Model model) {
        model.addAttribute("page", "ligatabelle");
        model.addAttribute("entries", spielberichtService.erstelleLigatabelle());
        return "index";
    }
    @GetMapping("schuetzentabelle")
    public String schuetzentabelle(Model model) {
        model.addAttribute("page", "schuetzentabelle");
        model.addAttribute("entries", spielberichtService.erstelleSchuetzentabelle());
        return "index";
    }

    @GetMapping("spieler")
    public String teams(Model model) {
        model.addAttribute("page", "spieler-overview");
        model.addAttribute("spielerList", spielerService.findAll());
        return "index";
    }

    @GetMapping("teams")
    public String spieler(Model model) {
        model.addAttribute("page", "teams-overview");
        model.addAttribute("teams", teamService.findAll());
        return "index";
    }

    @GetMapping("team-bearbeiten/{id}")
    public String teamBearbeiten(Model model,
                                 @PathVariable(name = "id") UUID id) {
        model.addAttribute("page", "team-bearbeiten");
        return "index";
    }

    @GetMapping("spieler-bearbeiten/{id}")
    public String spielerBearbeiten(Model model,
                                    @PathVariable(name = "id") UUID id) {
        model.addAttribute("page", "spieler-bearbeiten");
        return "index";
    }

    @GetMapping("spielberichte")
    public String spielberichte(Model model) {
        model.addAttribute("berichte", spielberichtService.findAll());
        model.addAttribute("page", "spielberichte-overview");
        return "index";
    }

}
