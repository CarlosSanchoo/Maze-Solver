package edu.epsevg.prop.ac1.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvWriter {
    private final String filename;
    private final String separator;

    public CsvWriter(String filename) { this(filename, ","); }
    public CsvWriter(String filename, String separator) { this.filename = filename; this.separator = separator; }

    public void write(List<String[]> rows) throws IOException {
        try (FileWriter fw = new FileWriter(filename)) {
            for (String[] r : rows) {
                fw.write(String.join(separator, r));
                fw.write("\n");
            }
        }
    }
}
