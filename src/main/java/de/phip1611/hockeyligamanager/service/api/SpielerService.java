/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.service.api;

import de.phip1611.hockeyligamanager.domain.Spieler;
import de.phip1611.hockeyligamanager.form.SpielerForm;
import de.phip1611.hockeyligamanager.service.api.dto.SpielerDto;

public interface SpielerService extends ExAndImportEntityService<Spieler, SpielerForm, SpielerDto> {
}
