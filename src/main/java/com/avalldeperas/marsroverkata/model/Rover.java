package com.avalldeperas.marsroverkata.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rover")
public class Rover {

    /**
     * Rover primary key auto-incremented.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Rover state to check if it is running.
     */
    @Column(name = "running")
    private boolean isRunning;

    /**
     * Current position of the Rover.
     */
    @Transient
    private Position position = new Position();

    /**
     * Gets the x component of the rover position.
     * @return the x component of the rover position.
     */
    @Access(AccessType.PROPERTY)
    @Column(name = "X")
    public Integer getX() {
        return position.getX();
    }

    /**
     * Gets the y component of the rover position.
     * @return the y component of the rover position.
     */
    @Access(AccessType.PROPERTY)
    @Column(name = "Y")
    public Integer getY() {
        return position.getY();
    }

    /**
     * Sets y component by updating the current position.
     * @param y The y component.
     */
    public void setY(final Integer y) {
        position = new Position(position.getX(), y);
    }

    /**
     * Sets x component by updating the current position.
     * @param x The x component.
     */
    public void setX(final Integer x) {
        position = new Position(x, position.getY());
    }

    /**
     * Current direction of the Rover.
     */
    @Column(name = "direction")
    private Direction direction = Direction.NORTH;

    /**
     * Rover's name.
     */
    @Column(name = "name")
    private String name;

    /**
     * Max X size of the grid used for wrapping.
     */
    public static final Integer MAX_X = 10;
    /**
     * Max Y size of the grid used for wrapping.
     */
    public static final Integer MAX_Y = 10;

    /**
     * Method that executes a list of commands inside the Command Wrapper.
     *
     * @param commandWrapper Wrapper of a list of commands.
     * @return A report of each command execution.
     */
    public List<RoverLog> execute(final CommandWrapper commandWrapper) {
        List<RoverLog> logs = new ArrayList<>();
        var stage = 1;
        for (Command command : commandWrapper.getCommandList()) {
            RoverLog currentLog;
            try {
                currentLog = this.execute(command);
                currentLog.setStage(stage);
                logs.add(currentLog);
            } catch (SecurityException | IllegalArgumentException e) {
                currentLog = new RoverLog(command, position, direction,
                        e.getMessage());
                currentLog.setStage(stage);
                logs.add(currentLog);
                break;
            }
            stage++;
        }
        return logs;
    }

    /**
     * Method that executes one given command.
     * @param command The command to be executed.
     * @return A report of the executed command.
     */
    private RoverLog execute(final Command command) {
        Position movement = new Position(direction.getVectorDirection());
        Command execCommand = command != null ? command : Command.NULL_COMMAND;
        switch (execCommand) {
            case BACKWARD:
                movement = movement.multiply(-1);
            case FORWARD:
                Position newPosition = position.add(movement);
                checkCollision(newPosition);
                this.position = newPosition;
                break;
            case LEFT:
                this.direction = direction.multiplyMatrix(
                        Direction.ROTATE_LEFT_MATRIX
                );
                break;
            case RIGHT:
                this.direction = direction.multiplyMatrix(
                        Direction.ROTATE_RIGHT_MATRIX
                );
                break;
            default:
                throw new IllegalArgumentException("Unimplemented command: "
                        + command);
        }
        return new RoverLog(command, position, direction,
                            "Execution success.");
    }

    /**
     * Method that checks rover collisions.
     * @param newPosition New rover position.
     */
    private void checkCollision(final Position newPosition) {
        if (buildRocks().contains(newPosition)) {
            throw new SecurityException("Collision! Rock detected at: "
                    + newPosition);
        }
    }

    /**
     * Method that builds rocks positions.
     * @return A list of rock positions.
     */
    private List<Position> buildRocks() {
        final List<Position> rocksPosition = Arrays.asList(
                new Position(1, 1),
                new Position(2, 3),
                new Position(5, 5),
                new Position(4, 7),
                new Position(8, 2)
        );
        return rocksPosition;
    }

    /**
     * Method that sends a right rotation command.
     * @return the report of the execution.
     */
    public RoverLog rotateRight() {
        return execute(Command.RIGHT);
    }

    /**
     * Method that sends a left rotation command.
     * @return the report of the execution.
     */
    public RoverLog rotateLeft() {
        return execute(Command.LEFT);
    }

    /**
     * Method that sends a forward move command.
     * @return the report of the execution.
     */
    public RoverLog moveForward() {
        return execute(Command.FORWARD);
    }

    /**
     * Method that sends a backward move command.
     * @return the report of the execution.
     */
    public RoverLog moveBackward() {
        return execute(Command.BACKWARD);
    }

}
