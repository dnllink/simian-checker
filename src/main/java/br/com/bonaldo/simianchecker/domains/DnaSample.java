package br.com.bonaldo.simianchecker.domains;

import lombok.Data;

@Data
public class DnaSample {
    private String[][] cells;
    private boolean isSimian;

    public DnaSample(final String[][] cells) {
        this.cells = cells;
    }
}