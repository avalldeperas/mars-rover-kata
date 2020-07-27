package com.avalldeperas.marsroverkata.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class Position {

    private Integer x;
    private Integer y;

    public Position(Integer xValue, Integer yValue) {
        this.x = xValue;
        this.y = yValue;
    }

    public Position(Integer[] position) {
        this.x = position[0];
        this.y = position[1];
    }

    public Position() {
        this(new Integer[]{0,0});
    }

    public Position add(Position positionValue) {

        Position tempPosition = new Position(new Integer[]{this.x,this.y});

        tempPosition.x += positionValue.x;
        tempPosition.y += positionValue.y;

        tempPosition.x = tempPosition.x % Rover.MAX_X;
        tempPosition.y = tempPosition.y % Rover.MAX_Y;

        if (tempPosition.x<0) tempPosition.x += Rover.MAX_X;
        if (tempPosition.y<0) tempPosition.y += Rover.MAX_Y;

        return tempPosition;
    }

    public Position multiply(Integer value) {
        Position tempPosition = new Position(new Integer[]{this.x,this.y});
        tempPosition.x *= value;
        tempPosition.y *= value;
        return tempPosition;
    }


}
