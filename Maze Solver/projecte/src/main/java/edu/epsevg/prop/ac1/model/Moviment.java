package edu.epsevg.prop.ac1.model;

/**
 * Representa un moviment en un tauler rectangular en les quatre direccions bàsiques
 * Ens indica quin agent l'ha fet i si el moviment implica recollir una clau.
 */
public class Moviment {
    private final int agentId;
    private final Direccio direccio;
    private final boolean recullClau;

    /**
     * Constructor principal
     * @param agentId el identificador de l'agent (1,2,....)
     * @param direccio és la direcció del moviment
     * @param recullClau indica true si el moviment ha recollit una clau.
     */
    public Moviment(int agentId, Direccio direccio, boolean recullClau) {
        this.agentId = agentId;
        this.direccio = direccio;
        this.recullClau = recullClau;
    }

    /**
     * @return l'Id (1,2,..) de l'agent que ha mogut
     */
    public int getAgentId() { return agentId; }
    /**
     * @return la direcció de moviment
     */    
    public Direccio getDireccio() { return direccio; }
    
    /**
     * @return true si ha recollit alguna clau
     */    
    public boolean isRecullClau() { return recullClau; }

    @Override
    public String toString() {
        return "Agent " + agentId + " -> " + direccio + (recullClau ? " (clau)" : "");
    }
}
