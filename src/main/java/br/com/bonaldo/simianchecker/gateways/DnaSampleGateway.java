package br.com.bonaldo.simianchecker.gateways;

import br.com.bonaldo.simianchecker.domains.DnaSample;
import br.com.bonaldo.simianchecker.domains.DnaStats;

import java.util.List;

public interface DnaSampleGateway {
    DnaSample save(DnaSample dnaSample);
    List<DnaStats> getStatistics();
}