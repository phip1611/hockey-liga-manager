/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.controller;

import de.phip1611.hockeyligamanager.config.DateTimeFormatterConfiguration;
import de.phip1611.hockeyligamanager.form.SpielberichtForm;
import de.phip1611.hockeyligamanager.service.api.SpielberichtService;
import de.phip1611.hockeyligamanager.service.api.SpielerService;
import de.phip1611.hockeyligamanager.service.api.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.logging.Logger;

@Controller
public class SpielberichtController {

    private Logger LOGGER = Logger.getLogger("SpielberichtController");

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
    public String ligatabelle(Model model, @RequestParam(value = "sort", required = false) String sort) {
        model.addAttribute("page", "ligatabelle");
        model.addAttribute("entries", spielberichtService.erstelleLigatabelle(sort));
        return "index";
    }
    @GetMapping("schuetzentabelle")
    public String schuetzentabelle(Model model, @RequestParam(value = "sort", required = false) String sort) {
        model.addAttribute("page", "schuetzentabelle");
        model.addAttribute("entries", spielberichtService.erstelleSchuetzentabelle(sort));
        return "index";
    }

    @GetMapping("spielberichte")
    public String spielberichte(Model model) {
        model.addAttribute("berichte", spielberichtService.findAll());
        model.addAttribute("page", "spielberichte-overview");
        model.addAttribute("pattern", DateTimeFormatterConfiguration.PATTERN);
        return "index";
    }

    @GetMapping("spielberichte/{id}")
    public String spielberichtDetail(Model model, @PathVariable("id") UUID id) {
        model.addAttribute("bericht", spielberichtService.findById(id));
        model.addAttribute("page", "spielbericht-detail");
        model.addAttribute("pattern", DateTimeFormatterConfiguration.PATTERN);
        return "index";
    }

    @GetMapping("spielberichte/{id}/edit")
    public String spielberichtEdit(Model model, @PathVariable("id") UUID id) {
        var form = spielberichtService.createFormFromId(id);
        form.addExtraFields();
        model.addAttribute("form", form);
        model.addAttribute("teams", teamService.findAll());
        model.addAttribute("spieler", spielerService.findAll());
        model.addAttribute("page", "spielbericht-edit");
        return "index";
    }

    @GetMapping("spielberichte/{id}/delete")
    public String spielberichtEdit(@PathVariable("id") UUID id) {
        spielberichtService.deleteById(id);
        LOGGER.info("deleted spielbericht " + id);
        return "redirect:/spielberichte";
    }

    @GetMapping("spielberichte/new")
    public String spielberichteNew(Model model) {
        var form = new SpielberichtForm();
        form.addExtraFields();
        model.addAttribute("form", form)
                .addAttribute("teams", teamService.findAll())
                .addAttribute("spieler", spielerService.findAll())
                .addAttribute("page", "spielbericht-edit");
        return "index";
    }

    @PostMapping("spielberichte/submit")
    public String spielberichteSubmit(@ModelAttribute SpielberichtForm form) {
        var dto = spielberichtService.createOrUpdate(form);
        return "redirect:/spielberichte/" + dto.getId() + "/edit";
    }

}
