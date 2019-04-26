package br.com.bonaldo.simianchecker.gateways.mongo;

import br.com.bonaldo.simianchecker.domains.DnaSample;
import br.com.bonaldo.simianchecker.gateways.DnaSampleGateway;
import br.com.bonaldo.simianchecker.gateways.repositories.DnaSampleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DnaSampleMongoGateway implements DnaSampleGateway {

    private final DnaSampleRepository repository;

    @Override
    public DnaSample save(final DnaSample dnaSample) {
        return repository.save(dnaSample);
    }
}