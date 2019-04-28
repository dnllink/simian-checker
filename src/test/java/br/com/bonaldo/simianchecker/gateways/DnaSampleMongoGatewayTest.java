package br.com.bonaldo.simianchecker.gateways;

import br.com.bonaldo.simianchecker.domains.DnaSample;
import br.com.bonaldo.simianchecker.domains.DnaStats;
import br.com.bonaldo.simianchecker.gateways.mongo.DnaSampleMongoGateway;
import br.com.bonaldo.simianchecker.gateways.repositories.DnaSampleRepository;
import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class DnaSampleMongoGatewayTest {

    private static final String COLLECTION_NAME = "samples";

    @InjectMocks
    private DnaSampleMongoGateway dnaSampleMongoGateway;

    @Mock
    private DnaSampleRepository repository;

    @Mock
    private MongoOperations mongoOperations;

    @Test
    public void saveShouldCallRepositoryWithCorrectParameter() {
        final DnaSample dnaSample = new DnaSample();

        dnaSampleMongoGateway.save(dnaSample);

        verify(repository).save(dnaSample);
    }

    @Test
    public void getStatisticsShouldCallMongoOperationsAggregateWithCorrectParameters() {
        when(mongoOperations.aggregate(any(Aggregation.class), eq(COLLECTION_NAME), eq(DnaStats.class)))
                .thenReturn(new AggregationResults<>(new ArrayList<>(), new Document()));

        dnaSampleMongoGateway.getStatistics();

        verify(mongoOperations).aggregate(any(Aggregation.class), eq(COLLECTION_NAME), eq(DnaStats.class));
    }
}