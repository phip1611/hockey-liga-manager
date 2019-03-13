/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.controller;

import de.phip1611.hockeyligamanager.service.api.SpielerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.util.logging.Logger;

@Controller
public class SpielerController {

    private SpielerService spielerService;

    private Logger LOGGER = Logger.getLogger("SpielerController");

    public SpielerController(SpielerService spielerService) {
        this.spielerService = spielerService;
    }


    @GetMapping("spieler/{id}/delete")
    public String spielerDelete(@PathVariable(name = "id") UUID id,
                                HttpServletRequest req) {
        spielerService.deleteById(id);
        LOGGER.info("deleted user " + id);
        return "redirect:" + req.getHeader("referer");
    }




}
