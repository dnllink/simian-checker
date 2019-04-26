package br.com.bonaldo.simianchecker.gateways.kafka;

import br.com.bonaldo.simianchecker.domains.DnaSample;
import br.com.bonaldo.simianchecker.usecases.SaveUniqueSample;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class DnaSampleKafkaListener {

    private final ObjectMapper objectMapper;
    private final SaveUniqueSample saveUniqueSample;

    @KafkaListener(topics = "${spring.kafka.dna-sample.topic}")
    public void listen(final String payload) throws IOException {
        log.info("Received dna sample to save: {}", payload);
        final DnaSample dnaSample = parsePayload(payload);
        saveUniqueSample.execute(dnaSample);
    }

    private DnaSample parsePayload(final String payload) throws IOException {
        return objectMapper.readValue(payload, DnaSample.class);
    }
}