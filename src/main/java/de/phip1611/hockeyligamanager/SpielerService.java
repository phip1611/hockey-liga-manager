package de.phip1611.hockeyligamanager;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class SpielerService {

    private SpielerRepo spielerRepo;

    public SpielerService(SpielerRepo spielerRepo) {
        this.spielerRepo = spielerRepo;
    }

    public Spieler createSpieler(Spieler spieler) {
        return this.spielerRepo.save(spieler);
    }


    public List<Spieler> getSpieler() {
        return this.spielerRepo.findAll();
    }
}
