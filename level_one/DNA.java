class DNA {

    public boolean isSimian(final String dnaLines[]) {

        final String[][] dnaMatrix = convertToStringMatrix(dnaLines);
        final int matrixSize = dnaMatrix.length;

        final boolean[][] foundSequences = new boolean[matrixSize][matrixSize];

        int simianSequenceCounter = 0;

        for (int i = 0; i < matrixSize; ++i) {
            for (int ii = 0; ii < matrixSize; ++ii) {
                if (searchForSequence(dnaMatrix, i, ii, foundSequences)) {
                    simianSequenceCounter++;
                }
            }
        }

        System.out.println("Number of found sequences: " + simianSequenceCounter);

        return simianSequenceCounter > 1;
    }

    private String[][] convertToStringMatrix(final String[] stringLines) {

        final int matrixSize = stringLines.length;

        final String[][] dnaMatrix = new String[matrixSize][matrixSize];

        for (int i = 0; i < matrixSize; i++) {
            for (int ii = 0; ii < matrixSize; ii++) {
                dnaMatrix[i][ii] = stringLines[i].substring(ii,ii+1);
            }
        }
        return dnaMatrix;
    }

    private boolean searchForSequence(
            String matrix[][],
            int row,
            int col,
            boolean foundSequences[][]) {

        int closeRows[] = new int[] {-1, -1, -1,  0, 0,  1, 1, 1};
        int closeCols[] = new int[] {-1,  0,  1, -1, 1, -1, 0, 1};

        for (int closeCounter = 0; closeCounter < 8; ++closeCounter) {

            int secondRow = row + closeRows[closeCounter];
            int secondCol = col + closeCols[closeCounter];

            if (canNavigateTo(matrix, matrix[row][col], secondRow, secondCol, foundSequences)) {

                int thirdRow = (secondRow - row) + secondRow;
                int thirdCol = (secondCol - col) + secondCol;

                if (canNavigateTo(matrix, matrix[secondRow][secondCol], thirdRow, thirdCol, foundSequences)) {

                    int lastRow = (thirdRow - secondRow) + thirdRow;
                    int lastCol = (thirdCol - secondCol) + thirdCol;

                    if (canNavigateTo(matrix, matrix[thirdRow][thirdCol], lastRow, lastCol, foundSequences)) {
                        foundSequences[row][col] = true;
                        foundSequences[secondRow][secondCol] = true;
                        foundSequences[thirdRow][thirdCol] = true;
                        foundSequences[lastRow][lastCol] = true;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean canNavigateTo(
            String matrix[][],
            String actualLetter,
            int rowTo,
            int colTo,
            boolean foundSequences[][]) {

        return (rowTo >= 0) && (rowTo < matrix.length) &&
                (colTo >= 0) && (colTo < matrix.length) &&
                (matrix[rowTo][colTo].equals(actualLetter) && !foundSequences[rowTo][colTo]);
    }

    public static void main (String[] args) {

        final String[] dnaData= new String[] {
                "CTGAGA",
                "CTATGC",
                "TATTGT",
                "AGAGGG",
                "CCCCTA",
                "TCACTG"
        };

        DNA dna = new DNA();
        System.out.println("The DNA processed is simian? -> " + dna.isSimian(dnaData));
    }
}