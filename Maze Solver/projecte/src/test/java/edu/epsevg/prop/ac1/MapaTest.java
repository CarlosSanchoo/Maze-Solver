package edu.epsevg.prop.ac1;

import edu.epsevg.prop.ac1.model.Mapa;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class MapaTest {
    @Test
    public void testParseMapA() throws Exception {
        Mapa m = new Mapa(Path.of("src/main/resources/mapA.map"));
        assertNotNull(m);
        assertEquals(2, m.getAgents().size()); // hi ha 1 i 2
    }

    @Test
    public void testParseMapB() throws Exception {
        Mapa m = new Mapa(Path.of("src/main/resources/mapB.map"));
        assertNotNull(m);
        assertEquals(1, m.getAgents().size());
    }
}
