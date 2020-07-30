package com.avalldeperas.marsroverkata.model;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@Slf4j
public class RoverTest {

    private Rover rover;
    private CommandWrapper commandWrapper, commandWrapperWithNull;
    private Random random;


    @BeforeEach
    public void initialize() {
        rover = new Rover();
        random = new Random();
        commandWrapper = generateRandomDirections();
        commandWrapperWithNull = generateCommandWithNull();
    }

    @Test
    public void rotateRight_basic() {
        RoverLog log = rover.rotateRight();
        assertThat(log.getAction()).isEqualTo(Command.RIGHT);
        assertThat(log.getPosition()).isEqualTo(new Position(0, 0));
        assertThat(log.getDirection()).isEqualTo(Direction.EAST);
        assertThat(log.getMessage()).isEqualTo("Execution success.");
    }

    @Test
    public void rotateLeft_basic() {
        RoverLog log = rover.rotateLeft();
        assertThat(log.getAction()).isEqualTo(Command.LEFT);
        assertThat(log.getPosition()).isEqualTo(new Position(0, 0));
        assertThat(log.getDirection()).isEqualTo(Direction.WEST);
        assertThat(log.getMessage()).isEqualTo("Execution success.");
    }

    @Test
    public void moveForward_basic() {
        RoverLog log = rover.moveForward();
        assertThat(log.getAction()).isEqualTo(Command.FORWARD);
        assertThat(log.getPosition()).isEqualTo(new Position(0, 1));
        assertThat(log.getDirection()).isEqualTo(Direction.NORTH);
        assertThat(log.getMessage()).isEqualTo("Execution success.");
    }

    @Test
    public void moveBackward_basic() {
        RoverLog log = rover.moveBackward();
        assertThat(log.getAction()).isEqualTo(Command.BACKWARD);
        assertThat(log.getPosition()).isEqualTo(new Position(0, 9));
        assertThat(log.getDirection()).isEqualTo(Direction.NORTH);
        assertThat(log.getMessage()).isEqualTo("Execution success.");
    }

    @Test
    public void moveForward_outX() {
        rover.setPosition(new Position(0,5));
        rover.setDirection(Direction.WEST);
        RoverLog log = rover.moveForward();
        assertThat(log.getPosition()).isEqualTo(new Position(9, 5));
        assertThat(log.getDirection()).isEqualTo(Direction.WEST);
    }

    @Test
    public void rockCollision_securityException() {
        rover.moveForward();
        rover.rotateRight();
        assertThatExceptionOfType(SecurityException.class)
            .isThrownBy(() -> rover.moveForward());
    }

    @Test
    public void rockCollision_basic() {
        commandWrapper = new CommandWrapper();
        commandWrapper.setCommands("frf");
        List<RoverLog> logs = rover.execute(commandWrapper);
        assertThat(logs).hasSize(3);
        assertThat(logs.get(2).getMessage())
                .contains("Collision! Rock detected at: ");

        assertThat(rover.getPosition()).isEqualTo(new Position(
                0,1)
        );
        assertThat(rover.getDirection()).isEqualTo(Direction.EAST);
    }

    @Test
    public void displayCommandsJSON() {
        Gson gson = new Gson();
        String json = gson.toJson(commandWrapper.getCommands());

        log.info(json);
    }

    private CommandWrapper generateRandomDirections() {
        Command[] values = Command.values();
        String commandString = "";
        for (int i = 0; i < 10; i++) {
            commandString += values[random.nextInt(values.length - 1)].getShortCommand();
        }
        CommandWrapper commandWrapper = new CommandWrapper();
        commandWrapper.setCommands(commandString);
        return commandWrapper;
    }


    private CommandWrapper generateCommandWithNull() {
        CommandWrapper tempCommandWrapper = new CommandWrapper();
        tempCommandWrapper.setCommandList(
                Arrays.asList(Command.FORWARD,Command.NULL_COMMAND)
        );
        return tempCommandWrapper;
    }

    @Test
    public void testLogTable(){
        CommandWrapper wrapper = new CommandWrapper();
        wrapper.setCommands("ffrfln");
        List<RoverLog> logs = rover.execute(wrapper);
        System.out.println(logs);
    }

}
