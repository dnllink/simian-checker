package br.com.bonaldo.simianchecker.gateways.adapters;

import br.com.bonaldo.simianchecker.domains.DnaSample;
import br.com.bonaldo.simianchecker.domains.Nucleobases;
import br.com.bonaldo.simianchecker.gateways.controllers.jsons.DnaSampleRequest;
import br.com.bonaldo.simianchecker.gateways.exceptions.InvalidConversionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class DnaSampleAdapter implements Adapter<DnaSampleRequest, DnaSample> {

    private static final String INVALID_DNA_SAMPLE_MESSAGE = "dna.validation.invalid.sample";
    private static final String INVALID_DNA_SAMPLE_BASE_MESSAGE = "dna.validation.invalid.base";

    @Override
    public DnaSample parse(DnaSampleRequest dnaSampleRequest) throws InvalidConversionException {

        final int matrixSize = dnaSampleRequest.getSampleSize();
        final String[] rawMatrix = dnaSampleRequest.getDna();
        final String[][] dnaMatrix = new String[matrixSize][matrixSize];

        for (int line = 0; line < matrixSize; line++) {
            checkValidLine(matrixSize, rawMatrix[line].length());
            for (int col = 0; col < matrixSize; col++) {
                fromStringToArray(rawMatrix[line], dnaMatrix, line, col);
                validateAcceptedLetter(dnaMatrix[line][col]);
            }
        }

        return new DnaSample(dnaMatrix);
    }

    private void validateAcceptedLetter(final String letter) throws InvalidConversionException {
        if(Nucleobases.isInvalidBase(letter))
            throw new InvalidConversionException(INVALID_DNA_SAMPLE_BASE_MESSAGE);
    }

    private void checkValidLine(final int matrixSize, final int lineSize) throws InvalidConversionException {
        if (matrixSize != lineSize)
            throw new InvalidConversionException(INVALID_DNA_SAMPLE_MESSAGE);
    }

    private void fromStringToArray(final String line, final String[][] matrix, final int lineIndex, final int colIndex) {
        matrix[lineIndex][colIndex] = line.substring(colIndex,colIndex+1);
    }
}