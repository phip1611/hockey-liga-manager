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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Controller
public class TeamController {

    private SpielberichtService spielberichtService;

    private TeamService teamService;

    private SpielerService spielerService;

    public TeamController(SpielberichtService spielberichtService,
                          TeamService teamService,
                          SpielerService spielerService) {
        this.spielberichtService = spielberichtService;
        this.teamService = teamService;
        this.spielerService = spielerService;
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

    @GetMapping("teams/new")
    public String teamNeu(Model model) {
        var form = new TeamAndSpielerForm();
        form.addExtraFields();
        model.addAttribute("teamForm", form);
        model.addAttribute("page", "team-edit");
        return "index";
    }

    @PostMapping("teams/submit")
    public String teamBearbeiten(@ModelAttribute /*@Valid TODO*/ TeamAndSpielerForm form) {
        var dto = this.teamService.createOrUpdate(form);

        // formular erneut bearbeiten
        return "redirect:/teams/" + dto.getId() + "/edit";
    }


}
