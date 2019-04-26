package br.com.bonaldo.simianchecker.gateways.repositories;

import br.com.bonaldo.simianchecker.domains.DnaSample;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DnaSampleRepository extends MongoRepository<DnaSample, String> {
}