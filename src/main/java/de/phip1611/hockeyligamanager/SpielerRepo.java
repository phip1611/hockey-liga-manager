package de.phip1611.hockeyligamanager;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpielerRepo extends JpaRepository<Spieler, UUID> {
}
