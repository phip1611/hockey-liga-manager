package de.phip1611.hockeyligamanager;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpielerController {

    private SpielerService spielerService;

    public SpielerController(SpielerService spielerService) {
        this.spielerService = spielerService;
    }

    @GetMapping("spieler")
    public List<Spieler> getSpieler() {
        return this.spielerService.getSpieler();
    }


}
