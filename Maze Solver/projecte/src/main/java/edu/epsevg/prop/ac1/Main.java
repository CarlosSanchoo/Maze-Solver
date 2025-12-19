package edu.epsevg.prop.ac1;

import edu.epsevg.prop.ac1.cerca.*;
import edu.epsevg.prop.ac1.cerca.heuristica.HeuristicaAvancada;
import edu.epsevg.prop.ac1.cerca.heuristica.HeuristicaBasica;
import edu.epsevg.prop.ac1.model.Mapa;
import edu.epsevg.prop.ac1.resultat.ResultatCerca;
import edu.epsevg.prop.ac1.utils.CsvWriter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        Mapa mapaA = new Mapa(Path.of("src/main/resources/mapA.map"));
        Mapa mapaB = new Mapa(Path.of("src/main/resources/mapB.map"));
        Mapa mapaC = new Mapa(Path.of("src/main/resources/mapC.map"));
        Mapa mapaD = new Mapa(Path.of("src/main/resources/mapD.map"));

        boolean usarLNT = true;

        List<String[]> rows = new ArrayList<>();
        rows.add(new String[] {"Algoritme","Mapa","Trobat","Longitud","NodesExplorats","NodesTallats","MemoriaPic","TempsMs"});
        
    
        System.out.println("==================================================================");
        System.out.println("==                        MAPA A                                ==");
        System.out.println("==================================================================");        
        executarIRecopilarResultats("BFS", new CercaBFS(usarLNT), mapaA, "mapA", rows);
        executarIRecopilarResultats("DFS", new CercaDFS(usarLNT), mapaA, "mapA", rows);
        executarIRecopilarResultats("IDS", new CercaIDS(usarLNT), mapaA, "mapA", rows);
        executarIRecopilarResultats("A* Basica", new CercaAStar(usarLNT, new HeuristicaBasica()), mapaA, "mapA", rows);
        executarIRecopilarResultats("A* Avancada", new CercaAStar(usarLNT, new HeuristicaAvancada()), mapaA, "mapA", rows);

        System.out.println("==================================================================");
        System.out.println("==                        MAPA B                                ==");
        System.out.println("==================================================================");
        executarIRecopilarResultats("BFS", new CercaBFS(usarLNT), mapaB, "mapB", rows);
        executarIRecopilarResultats("DFS", new CercaDFS(usarLNT), mapaB, "mapB", rows);
        executarIRecopilarResultats("IDS", new CercaIDS(usarLNT), mapaB, "mapB", rows);
        executarIRecopilarResultats("A* Basica", new CercaAStar(usarLNT, new HeuristicaBasica()), mapaB, "mapB", rows);
        executarIRecopilarResultats("A* Avancada", new CercaAStar(usarLNT, new HeuristicaAvancada()), mapaB, "mapB", rows);

        System.out.println("==================================================================");
        System.out.println("==                        MAPA C                                ==");
        System.out.println("==================================================================");
        executarIRecopilarResultats("BFS", new CercaBFS(usarLNT), mapaC, "mapC", rows);
        executarIRecopilarResultats("DFS", new CercaDFS(usarLNT), mapaC, "mapC", rows);
        executarIRecopilarResultats("IDS", new CercaIDS(usarLNT), mapaC, "mapC", rows);
        executarIRecopilarResultats("A* Basica", new CercaAStar(usarLNT, new HeuristicaBasica()), mapaC, "mapC", rows);
        executarIRecopilarResultats("A* Avancada", new CercaAStar(usarLNT, new HeuristicaAvancada()), mapaC, "mapC", rows);
        
        System.out.println("==================================================================");
        System.out.println("==                        MAPA D                                ==");
        System.out.println("==================================================================");
        executarIRecopilarResultats("BFS", new CercaBFS(usarLNT), mapaD, "mapD", rows);
        executarIRecopilarResultats("DFS", new CercaDFS(usarLNT), mapaD, "mapD", rows);
        executarIRecopilarResultats("IDS", new CercaIDS(usarLNT), mapaD, "mapD", rows);
        executarIRecopilarResultats("A* Basica", new CercaAStar(usarLNT, new HeuristicaBasica()), mapaD, "mapD", rows);
        executarIRecopilarResultats("A* Avancada", new CercaAStar(usarLNT, new HeuristicaAvancada()), mapaD, "mapD", rows);
        
        
        
        CsvWriter cw = new CsvWriter("results.csv");
        cw.write(rows);
        System.out.println("CSV escrit a results.csv");
    }

    private static void executarIRecopilarResultats(String nom, Cerca c, Mapa mapa, String nomMapa, List<String[]> rows) {
        System.out.println("Executant " + nom + " sobre " + nomMapa);
        String error="";
        
        ResultatCerca r = new ResultatCerca();
        r.startTime(); // Engegar el temporitzador
        
        try {
                        
            c.ferCerca(mapa, r);      
            
            System.out.println(">SOLUCIO:"+r.getCami());
                       
        } catch(OutOfMemoryError ooem){
            
            error = "OUT OF MEMORY ERROR";
            System.out.println(error);
            
        } catch(Exception ex){
            ex.printStackTrace();
            error = ex.getMessage();
        }
        
        r.stopTime(); // Aturar el temporitzador
        
        rows.add(new String[] {
            nom, nomMapa,
            (r.getCami()!=null) ? "YES" : "NO"+(error.isEmpty()?"":"("+error+")" )+")",
            (r.getCami()!=null) ? String.valueOf(r.getCami().size()) : "-",
            String.valueOf(r.getNodesExplorats()),
            String.valueOf(r.getNodesTallats()),
            String.valueOf(r.getMemoriaPic()),
            String.valueOf(r.getTempsMs())
        });
        System.out.println("\t"+nom + " -> " + r);
    }
}
