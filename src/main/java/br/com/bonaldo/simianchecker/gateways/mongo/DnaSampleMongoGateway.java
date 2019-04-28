package br.com.bonaldo.simianchecker.gateways.mongo;

import br.com.bonaldo.simianchecker.domains.DnaSample;
import br.com.bonaldo.simianchecker.domains.DnaStats;
import br.com.bonaldo.simianchecker.gateways.DnaSampleGateway;
import br.com.bonaldo.simianchecker.gateways.repositories.DnaSampleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DnaSampleMongoGateway implements DnaSampleGateway {

    private static final String COLLECTION_NAME = "samples";
    private static final String IS_SIMIAN = "isSimian";
    private static final String TOTAL = "total";

    private final DnaSampleRepository repository;
    private final MongoOperations mongoOperations;

    @Override
    public DnaSample save(final DnaSample dnaSample) {
        return repository.save(dnaSample);
    }

    @Override
    public List<DnaStats> getStatistics() {

        final GroupOperation group = Aggregation.group(IS_SIMIAN).count().as(TOTAL);

        final Aggregation aggregation =  Aggregation.newAggregation(group);

        return mongoOperations
                .aggregate(aggregation, COLLECTION_NAME, DnaStats.class)
                .getMappedResults();
    }
}