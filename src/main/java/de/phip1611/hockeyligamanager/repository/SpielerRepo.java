/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.repository;

import de.phip1611.hockeyligamanager.domain.Spieler;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SpielerRepo extends JpaRepository<Spieler, UUID> {

    List<Spieler> findAllByTeamId(UUID id);

}
