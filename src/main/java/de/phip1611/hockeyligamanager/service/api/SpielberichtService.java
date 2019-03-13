/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.service.api;

import de.phip1611.hockeyligamanager.domain.Spielbericht;
import de.phip1611.hockeyligamanager.form.SpielberichtForm;
import de.phip1611.hockeyligamanager.service.api.dto.LigatabellenEintragDto;
import de.phip1611.hockeyligamanager.service.api.dto.SchuetzenTabellenEintragDto;
import de.phip1611.hockeyligamanager.service.api.dto.SpielberichtDto;

import java.util.List;
import java.util.UUID;

public interface SpielberichtService extends EntityService<Spielbericht, SpielberichtForm, SpielberichtDto> {

    List<LigatabellenEintragDto> erstelleLigatabelle();

    List<SchuetzenTabellenEintragDto> erstelleSchuetzentabelle();

    SpielberichtForm createFormFromId(UUID id);

}
