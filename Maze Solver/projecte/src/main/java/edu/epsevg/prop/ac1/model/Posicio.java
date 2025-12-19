package edu.epsevg.prop.ac1.model;

/**
 * Classe que representa una posició a la graella
 */
public final class Posicio {
    public final int x;
    public final int y;

    /**
     * Constructor princial
     * @param x 
     * @param y
     */
    public Posicio(int x, int y) { this.x = x; this.y = y; }

    
    /**
     * Calcular la nova posició aplicant un moviment en la direcció proposada. NO AFECTA
     * LA POSICIÓ ACTUAL.
     * @return la nova posició calculada.
     */
    public Posicio translate(Direccio d) { return new Posicio(x + d.dx, y + d.dy); }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Posicio)) return false;
        Posicio p = (Posicio) o;
        return x == p.x && y == p.y;
    }

    @Override
    public int hashCode() { return 101 * x + y; }

    @Override
    public String toString() { return "(" + x + "," + y + ")"; }
}
