package edu.epsevg.prop.ac1.cerca.heuristica;

import edu.epsevg.prop.ac1.model.Mapa;
import edu.epsevg.prop.ac1.model.Posicio;
import java.util.List;


/** 
 * Distància de Manhattan a la clau més propera 
 * (si queden per recollir) o a la sortida.
 */
public class HeuristicaBasica implements Heuristica {
    @Override
    public int h(Mapa estat) {
        //@TODO: reemplaceu tot el codi per la vostra heurística.
        List<Posicio> posClaus = estat.PosicionsClaus();
        Posicio sortida = estat.getSortidaPosicio();
        
        int min = -1;
        
        for (Posicio agent: estat.getAgents() ){
            for (Posicio clau: posClaus){
                int dist = DistManhatan(agent, clau);
                if (dist == 0) return 0;
                if (min<0) min = dist; //primera iteració deixo la primera distancia
                else{
                    if (dist < min) min = dist;
                }
            }
        }
        
        if (min == -1){ //no hi ha claus per tant comparo les dists dels agents a la sortida
            for (Posicio agent: estat.getAgents()){
                int dist = DistManhatan(agent, sortida);
                if (dist == 0) return 0;
                if (min<0) min = dist;
                else{
                    if (dist < min) min = dist;
                }
            }
        }
        
        return min;
    }
    
    private int DistManhatan(Posicio p1, Posicio p2){
        return Math.abs(p1.x-p2.x)+Math.abs((p1.y-p2.y));
    }
    
}
