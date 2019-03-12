/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.service.api;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 *
 * @param <E> Entitätstyp
 * @param <F> Standard-Form-Typ
 * @param <D> Standard-DTO-Typ
 */
@Service
public interface EntityService<E, F, D> {

    D createOrUpdate(F f);

    D findById(UUID id);

    List<D> findAll();

    void deleteById(UUID id);
}
