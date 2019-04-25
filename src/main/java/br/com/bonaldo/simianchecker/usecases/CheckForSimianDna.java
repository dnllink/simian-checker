package br.com.bonaldo.simianchecker.usecases;

import br.com.bonaldo.simianchecker.domains.DnaSample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CheckForSimianDna {

    private static final int[] CLOSE_ROWS = new int[]{-1, -1, -1, 0, 0, 1, 1, 1};
    private static final int[] CLOSE_COLS = new int[]{-1, 0, 1, -1, 1, -1, 0, 1};

    public boolean execute(final DnaSample dnaSample) {

        final String[][] dnaMatrix = dnaSample.getCells();
        final int matrixSize = dnaMatrix.length;

        final boolean[][] foundSequences = new boolean[matrixSize][matrixSize];

        int simianSequenceCounter = 0;

        for (int row = 0; row < matrixSize; ++row) {
            for (int col = 0; col < matrixSize; ++col) {
                if (searchForSequenceOnCloseCells(dnaMatrix, row, col, foundSequences)) {//TODO parar de procurar se achar 2
                    simianSequenceCounter++;
                }
            }
        }

        log.info("Found {} sequences on dna sample", simianSequenceCounter);

        return simianSequenceCounter > 1;
    }

    private boolean searchForSequenceOnCloseCells(
            final String matrixToSearch[][],
            final int row,
            final int col,
            final boolean foundSequences[][]) {

        for (int closeCounter = 0; closeCounter < 8; ++closeCounter) {

            final int closeRow = row + CLOSE_ROWS[closeCounter];
            final int closeCol = col + CLOSE_COLS[closeCounter];

            if (canNavigateTo(matrixToSearch, matrixToSearch[row][col], closeRow, closeCol, foundSequences)) {

                final int secondCloseRow = calculateNextCloseRowCol(row, closeRow);
                final int secondCloseCol = calculateNextCloseRowCol(col, closeCol);

                if (canNavigateTo(matrixToSearch, matrixToSearch[closeRow][closeCol], secondCloseRow, secondCloseCol, foundSequences)) {

                    final int lastCloseRow = calculateNextCloseRowCol (closeRow, secondCloseRow);
                    final int lastCloseCol = calculateNextCloseRowCol (closeCol, secondCloseCol);

                    if (canNavigateTo(matrixToSearch, matrixToSearch[secondCloseRow][secondCloseCol], lastCloseRow, lastCloseCol, foundSequences)) {
                        foundSequences[row][col] = true;
                        foundSequences[closeRow][closeCol] = true;
                        foundSequences[secondCloseRow][secondCloseCol] = true;
                        foundSequences[lastCloseRow][lastCloseCol] = true;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private int calculateNextCloseRowCol(final int previousRowCol, final int actualRowCol) {
        return (actualRowCol - previousRowCol) + actualRowCol;
    }

    private boolean canNavigateTo(
            final String matrix[][],
            final String actualLetter,
            final int rowTo,
            final int colTo,
            final boolean foundSequences[][]) {

        return (rowTo >= 0) && (rowTo < matrix.length) &&
                (colTo >= 0) && (colTo < matrix.length) &&
                (matrix[rowTo][colTo].equals(actualLetter) && !foundSequences[rowTo][colTo]);
    }
}