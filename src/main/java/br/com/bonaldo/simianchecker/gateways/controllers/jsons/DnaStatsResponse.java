package br.com.bonaldo.simianchecker.gateways.controllers.jsons;

import br.com.bonaldo.simianchecker.domains.DnaStats;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class DnaStatsResponse {

    @JsonProperty("count_mutant_dna")
    private Long countMutantDna;

    @JsonProperty("count_human_dna")
    private Long countHumanDna;

    private Double ratio;

    public DnaStatsResponse(final List<DnaStats> dnaStats) {

        this.countMutantDna = dnaStats.stream()
                .filter(DnaStats::isSimian)
                .map(DnaStats::getTotal)
                .findAny()
                .orElse(0L);

        this.countHumanDna = dnaStats.stream()
                .filter(DnaStats::isHuman)
                .map(DnaStats::getTotal)
                .findAny()
                .orElse(0L);

        if (0 == countHumanDna) {
            this.ratio = 0D;
        } else {
            this.ratio = (double) countMutantDna / (double) countHumanDna;
        }
    }
}