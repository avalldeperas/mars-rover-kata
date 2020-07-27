package com.avalldeperas.marsroverkata.model;

import lombok.Getter;

import javax.lang.model.type.UnknownTypeException;
import java.util.Arrays;

@Getter
public enum Direction {

    NORTH("N", new Integer[]{0,1}),
    SOUTH("S", new Integer[]{0,-1}),
    WEST("W", new Integer[]{-1,0}),
    EAST("E", new Integer[]{1,0});

    private String shortDirection;
    private Integer[] vectorDirection;

    public static final Integer[][] rotateLeftMatrix = new Integer[][]{{0,1},{-1,0}};
    public static final Integer[][] rotateRightMatrix = new Integer[][]{{0,-1},{1,0}};

    Direction(String shortDirectionValue, Integer[] vectorDirectionValue) {
        this.shortDirection = shortDirectionValue;
        this.vectorDirection = vectorDirectionValue;
    }

    public Direction multiplyMatrix(Integer[][] rotationMatrix) {
        Integer[][] result = this.multiplyMatrices(new Integer[][]{this.vectorDirection},rotationMatrix);
        for (Direction direction: Direction.values()) {
            if (Arrays.equals(direction.vectorDirection, result[0])) {
                return direction;
            }
        }
        throw new IllegalArgumentException("Unknown direction: " + (result[0]).toString());
    }

    public static Integer[][] multiplyMatrices(Integer[][] firstMatrix, Integer[][] secondMatrix) {
        Integer[][] result = new Integer[firstMatrix.length][secondMatrix[0].length];

        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[row].length; col++) {
                result[row][col] = multiplyMatricesCell(firstMatrix, secondMatrix, row, col);
            }
        }

        return result;
    }

    public static Integer multiplyMatricesCell(Integer[][] firstMatrix, Integer[][] secondMatrix, int row, int col) {
        Integer cell = 0;
        for (int i = 0; i < secondMatrix.length; i++) {
            cell += firstMatrix[row][i] * secondMatrix[i][col];
        }
        return cell;
    }
}
