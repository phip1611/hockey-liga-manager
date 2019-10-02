/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.phip1611.hockeyligamanager.service.api.ExAndImportService;
import de.phip1611.hockeyligamanager.service.api.dto.ExportDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Controller
public class ExAndImportController {

    private ObjectMapper mapper;

    private ExAndImportService exAndImportService;

    public ExAndImportController(ExAndImportService exAndImportService,
                                 ObjectMapper mapper) {
        this.exAndImportService = exAndImportService;
        this.mapper = mapper;
    }

    @GetMapping(value = "/export")
    public void export(HttpServletResponse response) throws IOException {
        String filename = LocalDateTime.now().toString() + "__export_hockey_liga_manager.json";
        var export = this.exAndImportService.exportData();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename +"\"");
        mapper.writeValue(response.getOutputStream(), export);
    }

    @GetMapping(value = "/import")
    public String importFileGet(Model model) {
        model.addAttribute("page", "import");
        return "index";
    }

    @PostMapping(value = "/import")
    public String importFilePost(@RequestParam(name = "file") MultipartFile file) throws IOException {
        var dto = mapper.readValue(this.convert(file.getInputStream()), ExportDto.class);
        this.exAndImportService.importData(dto);
        return "redirect:/";
    }

    private String convert(InputStream inputStream) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return br.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }
}
