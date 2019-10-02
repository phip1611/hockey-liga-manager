/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.service.api;

import org.springframework.stereotype.Service;

/**
 * Bietet Methoden für Services, die Ex- und Import-Logik bereitstellen. Diese müssen alle Daten
 * löschen können und alle übergebenen Daten auf einmal anlegen können.
 *
 * @param <E> Entitätstyp
 * @param <F> Standard-Form-Typ
 * @param <D> Standard-DTO-Typ
 */
@Service
public interface ExAndImportEntityService<E, F, D> extends EntityService<E, F, D> {

    void deleteAll();

    void saveAll(Iterable<E> iterable);
}
