package br.com.bonaldo.simianchecker.gateways.controllers;

import br.com.bonaldo.simianchecker.domains.DnaSample;
import br.com.bonaldo.simianchecker.gateways.DnaSampleEventGateway;
import br.com.bonaldo.simianchecker.gateways.adapters.DnaSampleAdapter;
import br.com.bonaldo.simianchecker.gateways.controllers.jsons.DnaSampleRequest;


import br.com.bonaldo.simianchecker.gateways.exceptions.InvalidConversionException;
import br.com.bonaldo.simianchecker.usecases.CheckForSimianDna;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/simian")
public class DnaCheckController {

    private final DnaSampleAdapter dnaSampleAdapter;
    private final CheckForSimianDna checkForSimianDna;
    private final DnaSampleEventGateway dnaSampleEventGateway;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity checkDna(@RequestBody final DnaSampleRequest dnaSampleRequest) throws InvalidConversionException {

        log.info("Received request for dna check");
        final ResponseEntity responseEntity;

        final DnaSample dnaSample = dnaSampleAdapter.parse(dnaSampleRequest);

        final boolean isSimian = checkForSimianDna.execute(dnaSample);
        dnaSample.setSimian(isSimian);

        dnaSampleEventGateway.send(dnaSample);

        if (isSimian)
            responseEntity = ResponseEntity.ok().build();
        else
            responseEntity = ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        return responseEntity;
    }
}