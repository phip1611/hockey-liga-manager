/* 
   Copyright 2019 Philipp Schuster
   
   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de 
   Twitter:  @phip1611 
 */
package de.phip1611.hockeyligamanager.service.api;

import de.phip1611.hockeyligamanager.service.api.dto.ExportDto;

/**
 * Importiert und Exportiert den gesamten Datenbestand.
 *
 * @author Philipp Schuster (@phip1611)
 * @created 2019-10-02
 */
public interface ExAndImportService {

    ExportDto exportData();

    void importData(ExportDto dto);

}
