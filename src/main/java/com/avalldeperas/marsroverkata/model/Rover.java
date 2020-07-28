package com.avalldeperas.marsroverkata.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Entity
@Data
@RequiredArgsConstructor
@Table(name = "rover")
public class Rover {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "running")
    private boolean isRunning;

    @Transient
    private Position position = new Position();

    @Transient
//    @Column(name = "direction")
    private Direction direction = Direction.NORTH;

    @Column(name = "name")
    private String name;

    public final static Integer MAX_X = 10;
    public final static Integer MAX_Y = 10;

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
