package com.avalldeperas.marsroverkata.model;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class Rover {

    private Position position;
    private Direction direction;
    public final static Integer MAX_X = 10;
    public final static Integer MAX_Y = 10;

    public Rover() {
        position = new Position();
        direction = Direction.NORTH;
    }

    public String execute(CommandWrapper commandWrapper) {
        StringBuilder sb = new StringBuilder();
        int stage = 0;
        for (Command command : commandWrapper.getCommands()) {
            try {
                sb.append(stage + "-" + this.execute(command) + "\n");
            } catch (SecurityException e) {
                sb.append(stage + "-" + e.getMessage());
                break;
            }
            stage++;
        }
        return sb.toString();
    }

    private String execute(Command command) {
        Position movement = new Position(direction.getVectorDirection());
        switch(command) {
            case BACKWARD:
                movement = movement.multiply(-1);
            case FORWARD:
                Position newPosition = position.add(movement);
                checkCollision(newPosition);
                this.position = newPosition;
                break;
            case LEFT:
                this.direction = direction.multiplyMatrix(Direction.rotateLeftMatrix);
                break;
            case RIGHT:
                this.direction = direction.multiplyMatrix(Direction.rotateRightMatrix);
                break;
            default:
                throw new IllegalArgumentException("Unknown command: " + command.toString());
        }
        return String.format("%d:%d:%s",position.getX(),position.getY(),direction.getShortDirection());
    }

    private void checkCollision(Position newPosition) {
        if (buildRocks().contains(newPosition)) {
            throw new SecurityException("Rock detected at: " + newPosition);
        }
    }

    private List<Position> buildRocks() {
        return Arrays.asList(
                new Position(1,1),
                new Position(2,3),
                new Position(5,5),
                new Position(4,7),
                new Position(8,2)
        );
    }

    public String rotateRight() {
        return execute(Command.RIGHT);
    }

    public String rotateLeft() {
        return execute(Command.LEFT);
    }

    public String moveForward() {
        return execute(Command.FORWARD);
    }

    public String moveBackward() {
        return execute(Command.BACKWARD);
    }
}
