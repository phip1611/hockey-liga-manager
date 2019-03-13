/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.form;

public interface Form {

    /**
     * Fügt zusätzliche Felder hinzu, damit im Frontend nicht nur
     * bestehende Felder bearbeitet werden könne, sondern auch neue
     * hinzugefügt werden.
     */
    void addExtraFields();

    /**
     * Löscht alle leeren Felder um nur die "wirklichen" Felder zum
     * Backend durchzureichen.
     */
    void removeEmptyFields();
}
