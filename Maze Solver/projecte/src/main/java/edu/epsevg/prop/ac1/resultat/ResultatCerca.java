package edu.epsevg.prop.ac1.resultat;

import edu.epsevg.prop.ac1.model.Moviment;
import java.util.Date;
import java.util.List;


/**
 * Classe per emmagatzemar els resultats d'una cerca.
 * La cerca ha de cridar els mètodes per actualitzar-ne les dades:
 * <ul>
 *     <li>setCami(...): Useu-lo per informar de la solució (quan es troba).</li>* 
 *     <li>incNodesExplorats(): Useu-lo per anar comptant nodes explorats.</li>
 *     <li>incNodesTallats(): Useu-lo per anar comptant nodes tallats.</li>
 *     <li>updateMemoria(int): Useu-lo per informar de la memòria consumida. </li> 
 *     </ul>
 * Els mètodes startTime() i stiopTime() ja es criden des del mètode executarIRecollir().
 */
public class ResultatCerca {
    private List<Moviment> cami;
    private int nodesExplorats;
    private int nodesTallats;
    private int memoriaPic;
        
    private long inici;
    private long fi;

    /**
     * Constructor buit, dades de cerca a 0, i seteja el temps d'inici.
     */
    public ResultatCerca() {
        this.cami = null;
        this.nodesExplorats = 0;
        this.nodesTallats = 0;
        this.memoriaPic = 0;
        inici = (new Date()).getTime();
    }


    /**
     * IMPORTANT! Informa de la solució trobada
     * @param cami : la llista de passos per aconseguir arribar a la sortida
     */
    public void setCami(List<Moviment> cami) { this.cami = cami; }
    
    /**
     * Incrementa en 1 els nodes explorats
     */
    public void incNodesExplorats(){
        nodesExplorats++;
    }
    
    /**
     * Incrementa en 1 els nodes tallats (prunning)
     */    
    public void incNodesTallats(){
        this.nodesTallats++;
    }
    
    /**
     * Permet informar de l'us de memòria actual. Si és el màxim històric, ResultatCerca el registra, sinó l'ignora.
     */
    public void updateMemoria(int memoriaActual){
        if(memoriaActual>this.memoriaPic) {
            this.memoriaPic = memoriaActual;
        }
    }
    
    /**
     * Engega el cronòmetre per temporitzar la cerca.
     */
    public void startTime(){
        inici = (new Date()).getTime();
    }

    /**
     * Atura el cronòmetre de la cerca.
     */    
    public void stopTime(){
        fi = (new Date()).getTime();
    }

    // ==================================================
    // Getters
    // ==================================================
    
    public List<Moviment> getCami() { return cami; }
    public int getNodesExplorats() { return nodesExplorats; }
    public int getNodesTallats() { return nodesTallats; }
    public int getMemoriaPic() { return memoriaPic; }
    public long getTempsMs() { return fi-inici; }

    @Override
    public String toString() {
        return "Resultat: " + (cami != null ? cami.size()+" moviments" : "sense solució") +
               ", explorats=" + nodesExplorats +
               ", tallats=" + nodesTallats +
               ", memòria=" + memoriaPic +
               ", temps=" + getTempsMs() + "ms";
    }
}
