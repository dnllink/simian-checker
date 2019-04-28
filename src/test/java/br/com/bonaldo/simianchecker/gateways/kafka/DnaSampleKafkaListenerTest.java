package br.com.bonaldo.simianchecker.gateways.kafka;

import br.com.bonaldo.simianchecker.domains.DnaSample;
import br.com.bonaldo.simianchecker.usecases.SaveUniqueSample;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class DnaSampleKafkaListenerTest {

    private static final String PAYLOAD = "Payload";

    @InjectMocks
    private DnaSampleKafkaListener dnaSampleKafkaListener;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private SaveUniqueSample saveUniqueSample;

    @Test
    public void listenShouldParsePayloadAndCallUsecase() throws IOException {
        final DnaSample parsedDnaSample = new DnaSample();
        when(objectMapper.readValue(PAYLOAD, DnaSample.class)).thenReturn(parsedDnaSample);

        dnaSampleKafkaListener.listen(PAYLOAD);

        verify(saveUniqueSample).execute(parsedDnaSample);
    }
}