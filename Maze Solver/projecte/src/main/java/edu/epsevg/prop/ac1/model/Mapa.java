package edu.epsevg.prop.ac1.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Representa l'estat del mapa: grid, posicions agents (indexades per id 1..n),
 * i bitmask de claus.
 *
 * Codifiquem:
 *  - PARET = -1
 *  - ESPAI = 0
 *  - SORTIDA = -2
 *  - claus: ascii 'a'..'z' (valors positius > 0)
 *  - portes: ascii 'A'..'Z' (valors positius > 0)
 */
public class Mapa {
    private final int n;
    private final int m;
    private final int[][] grid; // conservem caràcters ordinals o codis
    private final List<Posicio> agents; // agents indexats a partir de 1 (index 0 -> agent 1)
    private int clausMask;
    private Posicio sortida;
    
    /**
     * Definicions dels valors del grid: PARET
     */
    public static final int PARET = -1;
    /**
     * Definicions dels valors del grid: ESPAI
     */    
    public static final int ESPAI = 0;
    /**
     * Definicions dels valors del grid: SORTIDA
     */    
    public static final int SORTIDA = -2;

    /**
     * Constructor a partir d'un arxiu
     */
public Mapa(Path fitxer) throws IOException {
        List<String> lines = Files.readAllLines(fitxer);
        this.n = lines.size();
        this.m = lines.get(0).length();
        this.grid = new int[n][m];
        this.agents = new ArrayList<>();
        this.clausMask = 0;
        
        HashMap<Integer, Posicio> posicioAgent = new HashMap<>();
        
        sortida = null;
        
        for (int i = 0; i < n; i++) {
            String row = lines.get(i);
            for (int j = 0; j < m; j++) {
                char c = row.charAt(j);
                switch (c) {
                    case '#': grid[i][j] = PARET; break;
                    case ' ': grid[i][j] = ESPAI; break;
                    case '@': grid[i][j] = SORTIDA; sortida = new Posicio(i,j); break;
                    default:
                        if (Character.isDigit(c)) {
                            // posem l'agent, però __NO__ es situa a la graella
                            //agents.add(new Posicio(i, j));
                            posicioAgent.put(c-'0', new Posicio(i, j));
                            grid[i][j] = ESPAI;
                        } else if (Character.isLowerCase(c)) {
                            grid[i][j] = c; // desem directament la lletra
                        } else if (Character.isUpperCase(c)) {
                            grid[i][j] = c; // desem directament la lletra
                        } else {
                            grid[i][j] = ESPAI;
                        }
                }
            }
        }
        for(int i=1;i<=posicioAgent.size();i++) {
            Posicio p = posicioAgent.get(i);
            if(p==null) throw new RuntimeException("Mapa no vàlid: id's d'agents han de ser correlatius i començar per 1.");
            agents.add(p);
        }
        
        if(sortida==null) throw new RuntimeException("Sortida no definida.");
        if(agents.size()==0) throw new RuntimeException("Agents no definits.");
    }

    /** 
     * Constructor còpia 
     * Fa una "Deep copy" del mapa (duplica en memòria i copia tots els valors d'un a l'altre) 
     */
    public Mapa(Mapa other) {
        this.n = other.n;
        this.m = other.m;
        this.grid = new int[n][m];
        for (int i = 0; i < n; i++) System.arraycopy(other.grid[i], 0, this.grid[i], 0, m);
        
        this.agents = new ArrayList<>();
        for (Posicio p : other.agents) this.agents.add(new Posicio(p.x, p.y));
        this.clausMask = other.clausMask;
        this.sortida = other.sortida; // LINIA AFEGIDA !
    }

    /**
     * Número de columnes
     * @return el nombre de columnes
     */
    public int getN() { return n; }
    
    /**
     * Número de files
     * @return el nombre de files
     */
    public int getM() { return m; }
    
    /**
     * @return Retorna la llista immutable de la posició dels agents
     */
    public List<Posicio> getAgents() { return Collections.unmodifiableList(agents); }
    
    /**
     * @return la màscara binària en format int de les claus. Cada clau és un bit, començant per la a (bit menys significant),b,c...
     *    P.ex. Si hi ha 3 claus, a, b i c, i tenim agafada la b i la c, la màscara val 6 (110 en binari)
     *          cba
     *          110 
     */
    public int getClausMask() { return clausMask; }

    /**
     * Permet saber si una posició conté la sortida
     * @return true si la posició és la sortida, false altrament
     */
    public boolean esSortida(Posicio p) {
        return getCell(p) == SORTIDA;
    }

    /**
     * @return el valor de la cella (veure constants PARET, ESPAI, SORTIDA)
     */
    private int getCell(Posicio p) {
        if (p.x < 0 || p.x >= n || p.y < 0 || p.y >= m) return PARET;
        return grid[p.x][p.y];
    }

    /**
     * Indicar que una clau ha estat recollida
     */
    private void setClauRecollida(char key) {
        int idx = key - 'a';
        clausMask |= (1 << idx);
    }

    /**
     * Permet saber si una clau ha estat recollida
     * @param key la clau que volem preguntar
     * @return true si la tenim
     */
    public boolean teClau(char key) {
        int idx = key - 'a';
        return (clausMask & (1 << idx)) != 0;
    }

    /**
     * Permet saber si podem obrir una clau determinada
     * @param door la porta que volem obrir (caràcter majúscules)
     * @return true si podem obrir-la
     */
    public boolean portaObrible(char door) {
        char key = Character.toLowerCase(door);
        return teClau(key);
    }

    /** 
     * Aplica el moviment SOBRE UNA CÒPIA (no altera el mapa actual)
     * @return  la nova instància amb el moviment
     * ja fet.
     */
    public Mapa mou(Moviment acc) {
        Mapa nou = new Mapa(this);
        int aid = acc.getAgentId();
        if (aid < 1 || aid > nou.agents.size()) throw new IllegalArgumentException("Agent id invalid");
        Posicio actual = nou.agents.get(aid - 1);
        Posicio dest = actual.translate(acc.getDireccio());

        int cell = nou.getCell(dest);
        if (cell == PARET) throw new IllegalArgumentException("Moviment cap a mur");
        if (Character.isUpperCase(cell)) {
            // porta
            if (!nou.portaObrible((char) cell)) throw new IllegalArgumentException("Porta tancada");
        }
        // no permetre col·lisions
        for (int i = 0; i < nou.agents.size(); i++) {
            if (i == aid-1) continue;
            Posicio p = nou.agents.get(i);
            if (p.equals(dest)) throw new IllegalArgumentException("Colisio amb altre agent");
        }
        // aplicar moviment
        nou.agents.set(aid - 1, dest);
        // si hi ha clau i no la teniem, recollir-la (si acc.isRecullClau() es true o si la cell té clau)
        if (Character.isLowerCase(cell)) {
            char key = (char) cell;
            if (!nou.teClau(key)) {
                // recollir
                nou.setClauRecollida(key);                
                nou.grid[dest.x][dest.y] = ESPAI;
            }
        }
        return nou;
    }

    /** 
     * Obtenir els moviments possibles des de l'estat actual
     * @return la llista de moviments possibles des de l'estat actual:
     *  - per cada agent (1..k) i cada direcció valida (que no sigui mur, si és una porta ha de ser obrible i sense col·lisió amb d'altres agents)
     *  - indica recullClau=true si el destí té una clau que encara no s'ha recollit
     */
    public List<Moviment> getAccionsPossibles() {
        List<Moviment> res = new ArrayList<>();
        // ===============================================
        //@TODO: A IMPLEMENTAR !!!!!!
        // ===============================================
        
        /*comprovacions necessaries:
            -paret, espai, sortida, claus, agents, portes
        */
        
        for (int i = 0; i < agents.size(); i++){
            Posicio posAgent = agents.get(i);
            for (Direccio dir: Direccio.values()){
                Posicio pos = new Posicio(posAgent.x, posAgent.y);
                pos = pos.translate(dir);
                int celda = this.getCell(pos);
                boolean noAgent = true;
                boolean portaObrible = true;
                
                if (celda == -1) continue; //comprovacio de PARET
                //comprovació de que no hi ha altres agents a la celda a on es pot moure
                int e = 0;
                while (noAgent && e<agents.size()){
                    if (e != i && agents.get(e).equals(pos)) noAgent = false;
                    else e++;
                }
                //comprovació portes
                if (Character.isUpperCase(celda) && !this.portaObrible((char) celda) ){
                    portaObrible = false;
                }
                
                //se puede mover //2 opciones-> (movimiento a una clave o a un espacio vacio)
                if (noAgent && portaObrible && (celda == 0 || celda == -2 || Character.isLowerCase((char) celda) || Character.isUpperCase((char) celda) ) ){ 
                    //es una clave y no la hemos recogido
                    if (Character.isLowerCase((char) celda) && !this.teClau((char) celda)) res.add(new Moviment(i+1, dir, true));
                    else res.add(new Moviment(i+1, dir, false));
                }      
            }
        }
        return res;
    }

    /** 
     * Permet saber si algú ha arribat a la sortida
     * @return true si algun agent ha arribat a la sortida 
     */
    public boolean esMeta() {
        for (Posicio p : agents) if (esSortida(p)) return true;
        return false;
    }

    @Override
    public boolean equals(Object o) {
        
        // ===============================================
        //@TODO: A IMPLEMENTAR !!!!!!
        // ===============================================
        if (this == o) return true;
        if (!(o instanceof Mapa)) return false;
        Mapa other = (Mapa) o;
        if (this.getN()!=other.getN() || this.getM()!=other.getM()) return false;
        boolean iguals = true;
        
        for (int i = 0; i < n; i++){
            for (int j = 0; j < m; j++){
                if (this.grid[i][j] != other.getCell(new Posicio(i,j))){
                    iguals = false;
                    break;
                }
            }
        }
        if ( !(this.agents.equals(other.agents)) ) return false;
        if (this.getClausMask() != other.getClausMask()) return false;
        
        return iguals;
    }

    @Override
    public int hashCode() {
        // ===============================================
        //@TODO: A IMPLEMENTAR !!!!!!
        // ===============================================
        int result = Objects.hash(n, m, clausMask, agents);
        // Agregamos el contenido de grid
        result = 31 * result + Arrays.deepHashCode(grid);
        return result;

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Agents:");
        for (int i = 0; i < agents.size(); i++) sb.append(" ").append(i+1).append(agents.get(i));
        sb.append(" clausMask=").append(Integer.toBinaryString(clausMask));
        return sb.toString();
    }

    /** 
     * @return la posició de sortida del mapa
     */
    public Posicio getSortidaPosicio() {
        return sortida;
    }

    
    //===================================================================
    // Aquí van les vostres ampliacions (nous mètodes d'utilitat)
    //===================================================================
    
    //@TODO: (opcionalment) el que cregueu convenient per ampliar la classe.
    
    public List<Posicio> PosicionsClaus (){
        List<Posicio> posClaus = new ArrayList<>();
        for (int i=0; i<n; i++){
            for (int j=0; j<m; j++){
                if (Character.isLowerCase(grid[i][j]) ) posClaus.addLast(new Posicio(i,j));
            }
        }
        return posClaus;
    }
    
    public void setSortida(Posicio p){
        this.sortida = p;
    }

}
