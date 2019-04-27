package br.com.bonaldo.simianchecker.usecases;

import br.com.bonaldo.simianchecker.domains.DnaSample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
public class CheckForSimianDnaTest {

    @InjectMocks
    private CheckForSimianDna checkForSimianDna;

    @Test
    public void checkWithHumamDnaShouldReturnFalse() {
        final DnaSample dnaSample = buildHumanDna();
        boolean isSimian = checkForSimianDna.execute(dnaSample);
        assertFalse(isSimian);
    }

    @Test
    public void checkWithSimianDnaShouldReturnTrue() {
        final DnaSample dnaSample = buildSimianDna();
        boolean isSimian = checkForSimianDna.execute(dnaSample);
        assertTrue(isSimian);
    }

    private DnaSample buildHumanDna() {
        final String[][] sample =
                {
                        {"A", "T", "G", "C", "G", "A"},
                        {"C", "A", "G", "T", "G", "C"},
                        {"T", "T", "A", "T", "T", "T"},
                        {"A", "G", "A", "C", "G", "G"},
                        {"G", "C", "G", "T", "C", "A"},
                        {"T", "C", "A", "C", "T", "G"},
                };
        return new DnaSample(sample);
    }

    private DnaSample buildSimianDna() {
        final String[][] sample =
                {
                        {"A", "T", "G", "C", "G", "A"},
                        {"A", "A", "G", "T", "G", "T"},
                        {"C", "T", "A", "T", "T", "T"},
                        {"C", "G", "A", "T", "G", "G"},
                        {"C", "C", "T", "T", "C", "A"},
                        {"C", "C", "G", "G", "G", "G"},
                };
        return new DnaSample(sample);
    }
}