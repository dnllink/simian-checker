package br.com.bonaldo.simianchecker.gateways.controllers;

import br.com.bonaldo.simianchecker.domains.DnaStats;
import br.com.bonaldo.simianchecker.gateways.DnaSampleGateway;
import br.com.bonaldo.simianchecker.gateways.controllers.jsons.DnaStatsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(DnaStatsController.class)
public class DnaStatsControllerTest {

    private static final Long HUMAN_COUNT = 100L;
    private static final Long SIMIAN_COUNT = 40L;
    private static final Double RATIO = 0.4;

    private MockMvc mockMvc;

    @Autowired
    private DnaStatsController dnaStatsController;

    @MockBean
    private DnaSampleGateway dnaSampleGateway;

    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(dnaStatsController)
                .build();

        objectMapper = new ObjectMapper();
    }

    @Test
    public void simianDnaCheckShouldReturnStatusCode200OK() throws Exception {
        when(dnaSampleGateway.getStatistics()).thenReturn(buildStats());

        final MvcResult mvcResult = mockMvc.perform(
                get("/stats"))
            .andReturn();

        DnaStatsResponse returnedStats = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), DnaStatsResponse.class);

        verify(dnaSampleGateway).getStatistics();
        assertNotNull(mvcResult);
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals(HUMAN_COUNT, returnedStats.getCountHumanDna());
        assertEquals(SIMIAN_COUNT, returnedStats.getCountMutantDna());
        assertEquals(RATIO, returnedStats.getRatio());
    }

    private List<DnaStats> buildStats() {
        final DnaStats humanStats = new DnaStats(false, HUMAN_COUNT);
        final DnaStats simianStats = new DnaStats(true, SIMIAN_COUNT);
        final List<DnaStats> stats = new ArrayList();
        Collections.addAll(stats, humanStats, simianStats);
        return stats;
    }
}