package br.com.bonaldo.simianchecker.usecases;

import br.com.bonaldo.simianchecker.domains.DnaSample;
import br.com.bonaldo.simianchecker.gateways.DnaSampleGateway;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class SaveUniqueSampleTest {

    @InjectMocks
    private SaveUniqueSample saveUniqueSample;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private DnaSampleGateway dnaSampleGateway;

    @Test
    public void saveUniqueSampleShouldFillHashAndCreatedDateAndCallGateway() throws JsonProcessingException {
        final DnaSample dnaSample = new DnaSample();
        when(objectMapper.writeValueAsString(any())).thenReturn("CELLS_JSON");
        when(dnaSampleGateway.save(any())).thenReturn(dnaSample);
        ArgumentCaptor<DnaSample> captor = ArgumentCaptor.forClass(DnaSample.class);

        saveUniqueSample.execute(dnaSample);

        verify(dnaSampleGateway).save(captor.capture());
        DnaSample savedDnaSample = captor.getValue();

        assertNotNull(savedDnaSample);
        assertNotNull(savedDnaSample.getId());
        assertNotNull(savedDnaSample.getCreatedDate());
    }
}