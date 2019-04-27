package br.com.bonaldo.simianchecker.gateways.controllers;

import br.com.bonaldo.simianchecker.gateways.DnaSampleGateway;
import br.com.bonaldo.simianchecker.gateways.controllers.jsons.DnaStatsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/stats")
public class DnaStatsController {

    private final DnaSampleGateway dnaSampleGateway;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public DnaStatsResponse getDnaStats() {
        log.info("Received request for dna stats");
        return new DnaStatsResponse(dnaSampleGateway.getStatistics());
    }
}