package edu.epsevg.prop.ac1.cerca.heuristica;

import edu.epsevg.prop.ac1.model.Mapa;
import edu.epsevg.prop.ac1.model.Posicio;

import java.util.*;

/**
 * Heuristica avançada: Al vostre gust ;-)
 */
public class HeuristicaAvancada implements Heuristica {
    
      @Override
    public int h(Mapa estat) {
        List<Posicio> claus = estat.PosicionsClaus();
        List<Posicio> agents = estat.getAgents();
        Posicio sortida = estat.getSortidaPosicio();

        // Si ya no hay claves, usar la distancia mínima a la salida
        if (claus.isEmpty()) {
            int minDist = Integer.MAX_VALUE;
            for (Posicio agent : agents) {
                minDist = Math.min(minDist, manhattan(agent, sortida));
            }
            return minDist;
        }

        // Crear lista de todos los nodos relevantes
        List<Posicio> nodes = new ArrayList<>();
        nodes.addAll(agents);
        nodes.addAll(claus);
        nodes.add(sortida);

        int n = nodes.size();
        int[][] dist = new int[n][n];

        // Calcular todas las distancias Manhattan entre pares de nodos
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                dist[i][j] = dist[j][i] = manhattan(nodes.get(i), nodes.get(j));
            }
        }

        // Calcular MST con el algoritmo de Prim
        return primMST(dist, n);
    }

    /**
     * Calcula el MST usando el algoritmo de Prim.
     * @param dist matriz de distancias
     * @param n número de nodos
     * @return coste total del MST
     */
    private int primMST(int[][] dist, int n) {
        boolean[] visitado = new boolean[n];
        int[] minDist = new int[n];
        Arrays.fill(minDist, Integer.MAX_VALUE);
        minDist[0] = 0; // empezamos desde el primer nodo

        int costeTotal = 0;

        for (int i = 0; i < n; i++) {
            int u = -1;
            for (int j = 0; j < n; j++) {
                if (!visitado[j] && (u == -1 || minDist[j] < minDist[u])) {
                    u = j;
                }
            }

            visitado[u] = true;
            costeTotal += minDist[u];

            for (int v = 0; v < n; v++) {
                if (!visitado[v] && dist[u][v] < minDist[v]) {
                    minDist[v] = dist[u][v];
                }
            }
        }

        return costeTotal;
    }

    /**
     * Distancia Manhattan entre dos posiciones.
     */
    private int manhattan(Posicio a, Posicio b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

     
}
