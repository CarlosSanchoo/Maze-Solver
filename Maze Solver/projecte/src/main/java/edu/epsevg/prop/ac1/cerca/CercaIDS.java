package edu.epsevg.prop.ac1.cerca;


import edu.epsevg.prop.ac1.model.*;
import edu.epsevg.prop.ac1.resultat.ResultatCerca;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;


public class CercaIDS extends Cerca {
    public CercaIDS(boolean usarLNT) { super(usarLNT); }

    @Override
    public void ferCerca(Mapa inicial, ResultatCerca rc) {
        int MAX_DEPTH_PERMITIDO = 50;
        int MAX_DEPTH_ACTUAL = 0;
        boolean trobat = false;

        
        while(!trobat && MAX_DEPTH_ACTUAL < MAX_DEPTH_PERMITIDO){
            Deque<Node> LNO = new ArrayDeque<>();
            Map<Mapa, Integer> LNT = null;
            if (usarLNT) LNT = new HashMap<>();
                            //estat pare accio depth g
            LNO.push(new Node(inicial, null, null, 0, 0));   
            if (usarLNT) LNT.put(inicial, 0);
            
            while (!trobat && !LNO.isEmpty()){
                
                Node actual = LNO.pop();  
                rc.incNodesExplorats();
                
                if (actual.estat.esMeta()){
                    trobat = true;
                    rc.setCami(reconstruirCami(actual));
                    
                }
                
                else if (!trobat && (actual.depth < MAX_DEPTH_ACTUAL) ){            
                    List<Moviment> movimientos = actual.estat.getAccionsPossibles();
                    for (Moviment mov: movimientos){
                        Mapa movido = actual.estat.mou(mov);
                        boolean descartar = false;
                        if (usarLNT){
                           
                            if (LNT.get(movido) != null && (LNT.get(movido)<=(actual.depth+1)) ){ //no inserto pq la prof es mayor
                                descartar=true;
                                rc.incNodesTallats();
                            }
                            if (!descartar){
                                if (usarLNT) LNT.put(movido, actual.depth+1);
                                LNO.push(new Node(movido, actual, mov, actual.depth+1, actual.g+1));
                            }
                        }
                        
                    }
                    //checkeo memoria despues de generar todos los hijos
                    if (usarLNT) rc.updateMemoria(LNO.size()+LNT.size());
                    else rc.updateMemoria(LNO.size());
                    
                } 
            }
                                           
            MAX_DEPTH_ACTUAL++;
        }
    }
        
}
