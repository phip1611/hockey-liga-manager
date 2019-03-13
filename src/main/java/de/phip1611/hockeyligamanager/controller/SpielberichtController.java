/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.controller;

import de.phip1611.hockeyligamanager.form.SpielberichtForm;
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
public class SpielberichtController {

    private SpielberichtService spielberichtService;

    private TeamService teamService;

    private SpielerService spielerService;

    public SpielberichtController(SpielberichtService spielberichtService,
                                  TeamService teamService,
                                  SpielerService spielerService) {
        this.spielberichtService = spielberichtService;
        this.teamService = teamService;
        this.spielerService = spielerService;
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

    @GetMapping("spielberichte")
    public String spielberichte(Model model) {
        model.addAttribute("berichte", spielberichtService.findAll());
        model.addAttribute("page", "spielberichte-overview");
        return "index";
    }

    @GetMapping("spielberichte/{id}")
    public String spielberichtDetail(Model model, @PathVariable("id") UUID id) {
        model.addAttribute("bericht", spielberichtService.findById(id));
        model.addAttribute("page", "spielbericht-detail");
        return "index";
    }

    @GetMapping("spielberichte/{id}/edit")
    public String spielberichtEdit(Model model, @PathVariable("id") UUID id) {
        var form = spielberichtService.createFormFromId(id);
        model.addAttribute("form", form);
        model.addAttribute("teams", teamService.findAll());
        model.addAttribute("spieler", spielerService.findAll());
        model.addAttribute("page", "spielbericht-edit");
        return "index";
    }

    @GetMapping("spielberichte/new")
    public String spielberichteNew(Model model) {
        model.addAttribute("form", new SpielberichtForm());
        model.addAttribute("page", "spielbericht-edit");
        return "index";
    }

    @PostMapping("spielberichte/submit")
    public String spielberichteSubmit(@ModelAttribute SpielberichtForm form) {
        var dto = spielberichtService.createOrUpdate(form);
        return "redirect:/spielberichte/" + dto.getId() + "/edit";
    }

}
