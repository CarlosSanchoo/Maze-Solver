package edu.epsevg.prop.ac1.cerca;

import edu.epsevg.prop.ac1.model.*;
import edu.epsevg.prop.ac1.resultat.ResultatCerca;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;


public class CercaBFS extends Cerca {
    
    public CercaBFS(boolean usarLNT) { super(usarLNT); }

    @Override
    public void ferCerca(Mapa inicial, ResultatCerca rc) {
        //Node(Mapa estat, Node pare, Moviment accio, int depth, int g) g es cost acumulat
        boolean fin = false;
        
        Queue<Node> LNO = new LinkedList<>();
        Set<Mapa> LNT = null;
        if (usarLNT) LNT = new HashSet<>();
        
        Node NodeInicial = new Node(inicial, null, null, 0, 0);
        LNO.add(NodeInicial);
        if (usarLNT) LNT.add(inicial);
        
        while(!LNO.isEmpty() && !fin){
            Node actual = LNO.poll();
            rc.incNodesExplorats();
            if (actual.estat.esMeta()){
                fin = true;
                rc.setCami(reconstruirCami(actual));
            }
            else{
                List <Moviment> ListaMovimientos = new ArrayList<>(actual.estat.getAccionsPossibles());
                for (Moviment mov: ListaMovimientos){ //añado todos los mapas a partir de los movimientos posibles
                    Mapa movido = new Mapa(actual.estat.mou(mov));
                    if (usarLNT){
                        if (!LNT.contains(movido)){
                            LNT.add(movido);
                            LNO.add(new Node(movido, actual, mov, actual.depth+1, actual.g+1) ); 
                            rc.updateMemoria(LNO.size()+LNT.size());
                        }
                        else{
                            rc.incNodesTallats();
                        }
                    }
                    if (!usarLNT){
                            LNO.add(new Node(movido, actual, mov, actual.depth+1, actual.g+1) ); 
                            rc.updateMemoria(LNO.size());
                    }
                }
            }

        }
    }
    
    
   
}
