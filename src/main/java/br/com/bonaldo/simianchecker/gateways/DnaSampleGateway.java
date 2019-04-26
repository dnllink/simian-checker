package br.com.bonaldo.simianchecker.gateways;

import br.com.bonaldo.simianchecker.domains.DnaSample;

public interface DnaSampleGateway {
    void save(DnaSample dnaSample);
}
