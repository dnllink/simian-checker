package br.com.bonaldo.simianchecker.gateways.kafka;

import br.com.bonaldo.simianchecker.domains.DnaSample;
import br.com.bonaldo.simianchecker.gateways.DnaSampleEventGateway;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DnaSampleKafkaGateway implements DnaSampleEventGateway {

    @Value("${spring.kafka.dna-sample.topic}")
    private String topic;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void send(final DnaSample dnaSample) {
        try {
            final String json = objectMapper.writeValueAsString(dnaSample);
            kafkaTemplate.send(topic, json);
        } catch (final JsonProcessingException e) {
            log.error("Failed to generate payload for: {}, with exception: {}", dnaSample, e.getMessage());
        }
    }
}