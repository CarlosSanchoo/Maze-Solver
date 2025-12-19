package edu.epsevg.prop.ac1.cerca;

import edu.epsevg.prop.ac1.model.Mapa;
import edu.epsevg.prop.ac1.model.Moviment;
import edu.epsevg.prop.ac1.resultat.ResultatCerca;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public abstract class Cerca {
    protected final boolean usarLNT;

    public Cerca(boolean usarLNT) {
        this.usarLNT = usarLNT;
    }

    public abstract void ferCerca(Mapa inicial, ResultatCerca rc);
    
    protected List<Moviment> reconstruirCami(Node node){
        List<Moviment> cami = new ArrayList<>();
        Node actual = node;
        while (actual.pare != null){
            cami.add(actual.accio);
            actual = actual.pare;
        }
        Collections.reverse(cami);
        return cami;
    }
}
