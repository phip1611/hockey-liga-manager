/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.phip1611.hockeyligamanager.service.api.ExAndImportService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Controller
public class ExAndImportController {

    private ExAndImportService exAndImportService;

    public ExAndImportController(ExAndImportService exAndImportService) {
        this.exAndImportService = exAndImportService;
    }

    @GetMapping(value = "/export")
    public void export(HttpServletResponse response) throws IOException {
        String filename = LocalDateTime.now().toString() + "__export_hockey_liga_manager.json";
        var obm = new ObjectMapper();
        var export = this.exAndImportService.exportData();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename +"\"");
        obm.writeValue(response.getOutputStream(), export);
    }
}
