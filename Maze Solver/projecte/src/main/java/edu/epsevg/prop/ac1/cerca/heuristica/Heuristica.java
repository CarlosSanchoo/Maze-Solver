package edu.epsevg.prop.ac1.cerca.heuristica;

import edu.epsevg.prop.ac1.model.Mapa;

/**
 * Interfície per totes les heurístiques
 */
public interface Heuristica {
    /**
     * Retorna el valor heurístic que li correspon al mapa
     * @param estat és el mapa que volem avaluar
     */
    int h(Mapa estat);
}
