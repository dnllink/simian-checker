package br.com.bonaldo.simianchecker.gateways.adapters;

import br.com.bonaldo.simianchecker.domains.DnaSample;
import br.com.bonaldo.simianchecker.gateways.controllers.jsons.DnaSampleRequest;
import br.com.bonaldo.simianchecker.gateways.exceptions.InvalidConversionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DnaSampleAdapter implements Adapter<DnaSampleRequest, DnaSample> {

    private static final String INVALID_DNA_SAMPLE_MESSAGE = "dna.validation.invalid.sample";

    @Override
    public DnaSample parse(DnaSampleRequest dnaSampleRequest) throws InvalidConversionException {

        final int matrixSize = dnaSampleRequest.getSampleSize();
        final String[] rawMatrix = dnaSampleRequest.getDna();
        final String[][] dnaMatrix = new String[matrixSize][matrixSize];

        try {
            for (int line = 0; line < matrixSize; line++) {
                for (int col = 0; col < matrixSize; col++) {
                    checkValidLine(matrixSize, rawMatrix[line].length());
                    fromStringToArray(rawMatrix[line], dnaMatrix, line, col);
                }
            }
        } catch (StringIndexOutOfBoundsException siobe) {
            log.error(siobe.getMessage());
            throw new InvalidConversionException(INVALID_DNA_SAMPLE_MESSAGE);
        }

        return new DnaSample(dnaMatrix);
    }

    private void checkValidLine(final int matrixSize, final int lineSize) throws InvalidConversionException {
        if (matrixSize != lineSize)
            throw new InvalidConversionException(INVALID_DNA_SAMPLE_MESSAGE);
    }

    private void fromStringToArray(final String line, final String[][] matrix, final int lineIndex, final int colIndex) {
        matrix[lineIndex][colIndex] = line.substring(colIndex,colIndex+1);
    }
}