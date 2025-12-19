package edu.epsevg.prop.ac1.cerca;

import edu.epsevg.prop.ac1.cerca.heuristica.Heuristica;
import edu.epsevg.prop.ac1.cerca.heuristica.HeuristicaBasica;
import edu.epsevg.prop.ac1.model.*;
import edu.epsevg.prop.ac1.resultat.ResultatCerca;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;


public class CercaAStar extends Cerca {

    private final Heuristica heur;

    public CercaAStar(boolean usarLNT, Heuristica heur) { 
        super(usarLNT); 
        this.heur = heur; 
    }

    @Override
    public  void ferCerca(Mapa inicial, ResultatCerca rc) {
        boolean solucio = false;
        
        Queue<Node> LNO = new PriorityQueue<>( (node1, node2) ->{ 
            int f1 = node1.g+heur.h(node1.estat);
            int f2 = node2.g+heur.h(node2.estat);
            return Integer.compare(f1,f2);
        });
        Map<Mapa, Integer> LNT = new HashMap<>();
                        //estat| pare|accio |depth|  g
        LNO.add(new Node(inicial, null, null, 0, 0));
        
        
        while (!LNO.isEmpty() && !solucio){
   
            rc.incNodesExplorats();                        
            Node actual = LNO.poll(); 
            LNT.put(actual.estat, actual.g);
            if (actual.estat.esMeta()){
                solucio = true;
                rc.setCami(reconstruirCami(actual));
            }
            
            else{            
                List<Moviment> movimientos = actual.estat.getAccionsPossibles();
                for (Moviment mov: movimientos){
                    //creando nodo
                    Mapa movido = actual.estat.mou(mov); 
                    int nouG = actual.g+1;
                    if (!LNT.containsKey(movido)){// nodo repetido
                        LNT.put(movido, nouG);
                        Node node = new Node(movido, actual, mov, actual.depth + 1,nouG);
                        LNO.add(node);
                        rc.updateMemoria(LNO.size()+LNT.size());
                    }
                    else {
                        int costAnt = LNT.get(movido);
                        if (nouG < costAnt) {
                            LNT.put(movido, nouG);
                            Node node = new Node(movido, actual, mov, actual.depth + 1,nouG);
                            LNO.add(node);
                            rc.updateMemoria(LNO.size()+LNT.size());
                        }
                        else {
                            rc.incNodesTallats();
                            rc.updateMemoria(LNO.size()+LNT.size());
                        }
                    }
                }
            }

        }

    }
}
