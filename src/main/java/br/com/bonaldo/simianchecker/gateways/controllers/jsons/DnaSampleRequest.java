package br.com.bonaldo.simianchecker.gateways.controllers.jsons;

import lombok.Getter;

import java.util.Optional;

@Getter
public class DnaSampleRequest {
    private String[] dna;

    public int getSampleSize() {
        return Optional.ofNullable(dna)
                .map(lines -> lines.length)
                .orElse(0);
    }
}