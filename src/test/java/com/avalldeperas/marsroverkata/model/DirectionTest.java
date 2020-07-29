package com.avalldeperas.marsroverkata.model;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@Slf4j
public class DirectionTest {

    private final Integer[][] firstMatrix = {{1,0}};
    private final Integer[][] secondMatrix = {{0,-1}, {1,0}};

    @Test
    public void multiplyMatricesCell_basic() {
        Integer expectedResult = Direction.multiplyMatricesCell(
                firstMatrix, secondMatrix, 0, 0
        );
        assertThat(expectedResult).isEqualTo(0);
    }

    @Test
    public void multiplyMatrices_basic() {
        Integer[][] productResult = Direction.multiplyMatrices(
                firstMatrix, secondMatrix
        );
        assertThat(productResult).isEqualTo(new Integer[][]{{0,-1}});
    }

    @Test
    public void multiplyMatrix_rotateLeft() {
        Direction directionResult = Direction.NORTH.multiplyMatrix(
                Direction.ROTATE_LEFT_MATRIX
        );
        assertThat(directionResult).isEqualTo(Direction.WEST);
    }

    @Test
    public void multiplyMatrix_rotateRight() {
        Direction directionResult = Direction.EAST.multiplyMatrix(
                Direction.ROTATE_RIGHT_MATRIX
        );
        assertThat(directionResult).isEqualTo(Direction.SOUTH);
    }

    /**
     * This test covers the scenario in which the resulting direction is not
     * defined in the Direction enum, throwing an IllegalArgumentException
     * with a certain message.
     */
    @Test
    public void multiplyMatrix_exception() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> Direction.NORTH.multiplyMatrix(
                        new Integer[][] {{1,-1}, {1,-1}}
                )).withMessageContaining("Unknown direction: ");
    }
}
