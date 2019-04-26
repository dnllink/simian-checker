package br.com.bonaldo.simianchecker.usecases;

import br.com.bonaldo.simianchecker.domains.DnaSample;
import br.com.bonaldo.simianchecker.gateways.DnaSampleGateway;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.Hashing;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class SaveUniqueSample {

    private final ObjectMapper objectMapper;
    private final DnaSampleGateway dnaSampleGateway;

    public DnaSample execute(final DnaSample dnaSample) throws JsonProcessingException {

        final String sampleString = objectMapper.writeValueAsString(dnaSample.getCells());
        final String hash = Hashing.sha256().hashString(sampleString, StandardCharsets.UTF_8).toString();

        dnaSample.setCreatedDate(LocalDateTime.now());
        dnaSample.setId(hash);

        log.debug("Saving sample: {}, with hash: {}", sampleString, hash);

        return dnaSampleGateway.save(dnaSample);
    }
}