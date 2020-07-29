package com.avalldeperas.marsroverkata.model;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Direction {

    /**
     * North direction with vector (0,1).
     */
    NORTH("N", new Integer[]{0, 1}),
    /**
     * South direction with vector (0,1).
     */
    SOUTH("S", new Integer[]{0, -1}),
    /**
     * West direction with vector (0,1).
     */
    WEST("W", new Integer[]{-1, 0}),
    /**
     * East direction with vector (0,1).
     */
    EAST("E", new Integer[]{1, 0});

    /**
     * Stores the direction identifiers.
     */
    private final String shortDirection;
    /**
     * Two component vector.
     */
    private final Integer[] vectorDirection;

    /**
     * Matrix for left rotation(90º).
     */
    public static final Integer[][] ROTATE_LEFT_MATRIX = {{0, 1}, {-1, 0}};
    /**
     * Matrix for right rotation(90º).
     */
    public static final Integer[][] ROTATE_RIGHT_MATRIX = {{0, -1}, {1, 0}};

    Direction(final String shortDirectionValue,
              final Integer[] vectorDirectionValue) {
        this.shortDirection = shortDirectionValue;
        this.vectorDirection = vectorDirectionValue;
    }

    /**
     * Method that returns matrix products.
     * @param rotationMatrix the matrix to be multiplied.
     * @return The resulting facing direction.
     */
    public Direction multiplyMatrix(final Integer[][] rotationMatrix) {
        Integer[][] result = multiplyMatrices(
                new Integer[][]{this.vectorDirection},
                rotationMatrix
        );

        return Arrays.stream(Direction.values())
                .filter(direction -> Arrays.equals(
                        direction.vectorDirection, result[0])
                ).findAny()
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown direction: "
                                + Arrays.toString((result[0]))
                ));
    }

    /**
     * Method that multiplies matrices.
     * @param firstMatrix First matrix for the product.
     * @param secondMatrix Second matrix for the product.
     * @return the matrix product.
     */
    public static Integer[][] multiplyMatrices(final Integer[][] firstMatrix,
                                               final Integer[][] secondMatrix) {
        Integer[][] result = new Integer[firstMatrix.length]
                [secondMatrix[0].length];

        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[row].length; col++) {
                result[row][col] = multiplyMatricesCell(firstMatrix,
                        secondMatrix, row, col);
            }
        }

        return result;
    }

    /**
     * Method that multiplies matrix cells.
     * @param firstMatrix First matrix for the product.
     * @param secondMatrix Second matrix for the product.
     * @param row the cell row to be multiplied.
     * @param col the cell column to be multiplied.
     * @return the result of a cell of the matrix products.
     */
    public static Integer multiplyMatricesCell(final Integer[][] firstMatrix,
                                               final Integer[][] secondMatrix,
                                               final int row, final int col) {
        var cell = 0;
        for (int i = 0; i < secondMatrix.length; i++) {
            cell += firstMatrix[row][i] * secondMatrix[i][col];
        }
        return cell;
    }
}
