package br.com.bonaldo.simianchecker.gateways.adapters;

import br.com.bonaldo.simianchecker.domains.DnaSample;
import br.com.bonaldo.simianchecker.gateways.controllers.jsons.DnaSampleRequest;
import br.com.bonaldo.simianchecker.gateways.exceptions.InvalidConversionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class DnaSampleAdapterTest {

    @InjectMocks
    private DnaSampleAdapter dnaSampleAdapter;

    @Test
    public void parseWithValidRequestShouldReturnTheSameDate() throws InvalidConversionException {
        final DnaSampleRequest dnaSampleRequest = buildValidDnaSampleRequest();

        final DnaSample dnaSample = dnaSampleAdapter.parse(dnaSampleRequest);

        assertRequestEqualsToDomain(dnaSampleRequest, dnaSample);
    }

    @Test(expected = InvalidConversionException.class)
    public void parseWithInvalidRequestWithLineDifferentShouldRaiseException() throws InvalidConversionException {
        DnaSampleRequest dnaSampleRequest = buildInvalidDnaSampleRequestWithLineSizeDifference();

        dnaSampleAdapter.parse(dnaSampleRequest);
    }

    @Test(expected = InvalidConversionException.class)
    public void parseWithInvalidRequestWithColumnDifferentShouldRaiseException() throws InvalidConversionException {
        DnaSampleRequest dnaSampleRequest = buildInvalidDnaSampleRequestWithColumnSizeDifference();

        dnaSampleAdapter.parse(dnaSampleRequest);
    }

    @Test(expected = InvalidConversionException.class)
    public void parseWithInvalidRequestWithNonExistentBaseShouldRaiseException() throws InvalidConversionException {
        DnaSampleRequest dnaSampleRequest = buildInvalidDnaSampleRequestWithNonExistentBase();

        dnaSampleAdapter.parse(dnaSampleRequest);
    }

    private void assertRequestEqualsToDomain(DnaSampleRequest dnaSampleRequest, DnaSample dnaSample) {
        final List<String> parsedStrings =
                Arrays.stream(dnaSample.getCells())
                .map(strings -> Arrays.stream(strings).collect(Collectors.joining()))
                .collect(Collectors.toList());

        for (int i = 0; i < dnaSampleRequest.getDna().length; i++) {
            assertEquals(dnaSampleRequest.getDna()[i], parsedStrings.get(i));
        }
    }

    public DnaSampleRequest buildValidDnaSampleRequest() {
        final DnaSampleRequest dnaSampleRequest = new DnaSampleRequest();
        dnaSampleRequest
                .setDna(
                    new String[]
                        {
                            "CTGAGA",
                            "CTATGC",
                            "TATTGT",
                            "AGAGGG",
                            "CCCCTA",
                            "TCACTG"
                        });
        return dnaSampleRequest;
    }

    public DnaSampleRequest buildInvalidDnaSampleRequestWithLineSizeDifference() {
        final DnaSampleRequest dnaSampleRequest = new DnaSampleRequest();
        dnaSampleRequest
                .setDna(
                    new String[]
                        {
                            "CTGAGA",
                            "CTATG",
                            "TATTGT",
                            "AGAGGG",
                            "CCCCTA",
                            "TCACTA"
                        });
        return dnaSampleRequest;
    }

    public DnaSampleRequest buildInvalidDnaSampleRequestWithColumnSizeDifference() {
        final DnaSampleRequest dnaSampleRequest = new DnaSampleRequest();
        dnaSampleRequest
                .setDna(
                    new String[]
                        {
                            "CTGAGA",
                            "CTATGC",
                            "TATTGT",
                            "AGAGGG",
                            "CCCCTA"
                        });
        return dnaSampleRequest;
    }

    public DnaSampleRequest buildInvalidDnaSampleRequestWithNonExistentBase() {
        final DnaSampleRequest dnaSampleRequest = new DnaSampleRequest();
        dnaSampleRequest
                .setDna(
                    new String[]
                        {
                            "CTGAGA",
                            "CTATGC",
                            "TATTGT",
                            "AGAGGG",
                            "CCCCTA",
                            "CCCXTA"
                        });
        return dnaSampleRequest;
    }
}