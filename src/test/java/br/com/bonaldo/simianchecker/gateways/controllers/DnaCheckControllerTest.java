package br.com.bonaldo.simianchecker.gateways.controllers;

import br.com.bonaldo.simianchecker.domains.DnaSample;
import br.com.bonaldo.simianchecker.gateways.DnaSampleEventGateway;
import br.com.bonaldo.simianchecker.gateways.adapters.DnaSampleAdapter;
import br.com.bonaldo.simianchecker.gateways.controllers.jsons.DnaSampleRequest;
import br.com.bonaldo.simianchecker.gateways.exceptions.InvalidConversionException;
import br.com.bonaldo.simianchecker.usecases.CheckForSimianDna;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@WebMvcTest(DnaCheckController.class)
public class DnaCheckControllerTest {

    private static final String INVALID_DNA_SAMPLE_MESSAGE = "dna.validation.invalid.sample";

    private MockMvc mockMvc;

    @Autowired
    private DnaCheckController dnaCheckController;

    @Autowired
    private MessageSource messageSource;

    @MockBean
    private DnaSampleAdapter dnaSampleAdapter;

    @MockBean
    private CheckForSimianDna checkForSimianDna;

    @MockBean
    private DnaSampleEventGateway dnaSampleEventGateway;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(dnaCheckController)
                .setControllerAdvice(new CustomExceptionHandler(messageSource))
                .build();
    }

    @Test
    public void simianDnaCheckShouldReturnStatusCode200OK() throws Exception, InvalidConversionException {
        final DnaSample parsedDnaSample = new DnaSample();
        when(dnaSampleAdapter.parse(any(DnaSampleRequest.class))).thenReturn(parsedDnaSample);
        when(checkForSimianDna.execute(parsedDnaSample)).thenReturn(true);

        final MvcResult mvcResult = mockMvc.perform(
                post("/simian")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(buildPayload()))
            .andReturn();

        verify(dnaSampleEventGateway).send(parsedDnaSample);
        assertNotNull(mvcResult);
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void humanDnaCheckShouldReturnStatusCode403Forbidden() throws Exception, InvalidConversionException {
        final DnaSample parsedDnaSample = new DnaSample();
        when(dnaSampleAdapter.parse(any(DnaSampleRequest.class))).thenReturn(parsedDnaSample);
        when(checkForSimianDna.execute(parsedDnaSample)).thenReturn(false);

        final MvcResult mvcResult = mockMvc.perform(
                post("/simian")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(buildPayload()))
                .andReturn();

        verify(dnaSampleEventGateway).send(parsedDnaSample);
        assertNotNull(mvcResult);
        assertEquals(HttpStatus.FORBIDDEN.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void dnaCheckWithInvalidSampleShouldReturnStatusCode400BadRequest() throws Exception, InvalidConversionException {
        when(dnaSampleAdapter.parse(any(DnaSampleRequest.class))).thenThrow(new InvalidConversionException(INVALID_DNA_SAMPLE_MESSAGE));

        final MvcResult mvcResult = mockMvc.perform(
                post("/simian")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(buildPayload()))
                .andReturn();

        verifyZeroInteractions(checkForSimianDna);
        verifyZeroInteractions(dnaSampleEventGateway);
        assertNotNull(mvcResult);
        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    private String buildPayload() {
        return "{\"dna\": " +
                "[\"ACTGTG\"," +
                 "\"ACTGTG\"," +
                 "\"ACTGTG\"," +
                 "\"ACTGTG\"," +
                 "\"ACTGTG\"," +
                 "\"ACTGTG\"]}";
    }
}