/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.controller;

import de.phip1611.hockeyligamanager.form.TeamAndSpielerForm;
import de.phip1611.hockeyligamanager.service.api.SpielberichtService;
import de.phip1611.hockeyligamanager.service.api.SpielerService;
import de.phip1611.hockeyligamanager.service.api.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("teams")
    public String teams(Model model) {
        model.addAttribute("page", "teams-overview");
        model.addAttribute("teams", teamService.findAll());
        return "index";
    }

    @GetMapping("teams/{id}")
    public String teamDetail(Model model,
                             @PathVariable(name = "id") UUID id) {
        model.addAttribute("team", teamService.findById(id));
        model.addAttribute("page", "team-detail");
        return "index";
    }

    @GetMapping("teams/{id}/edit")
    public String teamBearbeiten(Model model,
                                 @PathVariable(name = "id") UUID id) {
        var form = new TeamAndSpielerForm(teamService.findById(id));
        form.addExtraFields();
        model.addAttribute("teamForm", form);
        model.addAttribute("page", "team-edit");
        return "index";
    }

    @PostMapping("teams/submit")
    public String teamBearbeiten(@ModelAttribute /*@Valid TODO*/ TeamAndSpielerForm form) {
        form.removeEmptyFields();
        this.teamService.createOrUpdate(form);

        // formular erneut bearbeiten
        return "redirect:/teams/" + form.getId() + "/edit";
    }

    @GetMapping("spielberichte")
    public String spielberichte(Model model) {
        model.addAttribute("berichte", spielberichtService.findAll());
        model.addAttribute("page", "spielberichte-overview");
        return "index";
    }

    @GetMapping("spielberichte/{id}")
    public String spielberichte(Model model, @PathVariable("id") UUID id) {
        model.addAttribute("bericht", spielberichtService.findById(id));
        model.addAttribute("page", "spielbericht-detail");
        return "index";
    }

}
