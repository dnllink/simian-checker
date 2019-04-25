package br.com.bonaldo.simianchecker.domains;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DnaSample {
    private String[][] cells;
}