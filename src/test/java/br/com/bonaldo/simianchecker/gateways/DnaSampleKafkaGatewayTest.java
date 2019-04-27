package br.com.bonaldo.simianchecker.gateways;

import br.com.bonaldo.simianchecker.domains.DnaSample;
import br.com.bonaldo.simianchecker.gateways.kafka.DnaSampleKafkaGateway;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class DnaSampleKafkaGatewayTest {

    private static final String TOPIC = "topic.to.send";
    private static final String PAYLOAD = "SAMPLE_JSON";

    @InjectMocks
    private DnaSampleKafkaGateway dnaSampleKafkaGateway;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @Test
    public void sendShouldSendMessageWithKafkaTemplateWithCorrectParameters() throws JsonProcessingException {
        final DnaSample dnaSample = new DnaSample();
        ReflectionTestUtils.setField(dnaSampleKafkaGateway, "topic", TOPIC);
        when(objectMapper.writeValueAsString(dnaSample)).thenReturn(PAYLOAD);

        dnaSampleKafkaGateway.send(dnaSample);

        verify(kafkaTemplate).send(TOPIC, PAYLOAD);
    }

    @Test
    public void sendWithExceptionDuringJsonParseShouldNotSendMessageWithKafkaTemplate() throws JsonProcessingException {
        final DnaSample dnaSample = new DnaSample();
        when(objectMapper.writeValueAsString(dnaSample)).thenThrow(JsonProcessingException.class);

        dnaSampleKafkaGateway.send(dnaSample);

        verifyZeroInteractions(kafkaTemplate);
    }
}