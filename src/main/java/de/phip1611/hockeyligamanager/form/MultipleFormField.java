/*
   Copyright 2019 Philipp Schuster

   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de
   Twitter:  @phip1611
 */
package de.phip1611.hockeyligamanager.form;

/**
 * Ein Formfeld, das mehrfach in einem Form vorkommen kann.
 */
public interface MultipleFormField {

    /**
     * Damit kein ein Formfeld sagen, dass es empty ist.
     * So kann man überschüssige Formularfelder direkt herausfildern.
     */
    boolean isEmpty();
}
