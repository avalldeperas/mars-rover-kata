package com.avalldeperas.marsroverkata.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Position {
    /** X component of the position. */
    private Integer x;
    /** Y component of the position. */
    private Integer y;

    /**
     * Builds a position by x, y components.
     * @param xValue X component of the position.
     * @param yValue Y component of the position.
     */
    public Position(final Integer xValue, final Integer yValue) {
        this.x = xValue;
        this.y = yValue;
    }

    /**
     * Builds a position by a given Integer array.
     * @param position
     */
    public Position(final Integer[] position) {
        this.x = position[0];
        this.y = position[1];
    }

    /**
     * Builds a default position at (0,0).
     */
    public Position() {
        this(new Integer[]{0, 0});
    }

    /**
     * Method that adds displacement to the position.
     * @param displacement The displacement to the position.
     * @return the resulting new position.
     */
    public Position add(final Position displacement) {
        Position tempPosition = new Position(new Integer[]{this.x, this.y});

        tempPosition.x += displacement.x;
        tempPosition.y += displacement.y;

        tempPosition.x = tempPosition.x % Rover.MAX_X;
        tempPosition.y = tempPosition.y % Rover.MAX_Y;

        if (tempPosition.x < 0) {
            tempPosition.x += Rover.MAX_X;
        }
        if (tempPosition.y < 0) {
            tempPosition.y += Rover.MAX_Y;
        }

        return tempPosition;
    }

    /**
     * Method for the scalar vector product.
     * @param value scalar value.
     * @return the resulting scalar vector product.
     */
    public Position multiply(final Integer value) {
        Position tempPosition = new Position(new Integer[]{this.x, this.y});
        tempPosition.x *= value;
        tempPosition.y *= value;
        return tempPosition;
    }

    /**
     * Formats the current position of the Rover.
     * @return The formatted position.
     */
    public String toString() {
        return String.format("(%s,%s)", x, y);
    }
}
