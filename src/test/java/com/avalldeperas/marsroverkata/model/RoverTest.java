package com.avalldeperas.marsroverkata.model;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
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
        assertThat(rover.rotateRight()).isEqualTo("0:0:E");
    }

    @Test
    public void rotateLeft_basic() {
        assertThat(rover.rotateLeft()).isEqualTo("0:0:W");
    }

    @Test
    public void moveForward_basic() {
        assertThat(rover.moveForward()).isEqualTo("0:1:N");
    }

    @Test
    public void moveForward_outX() {
        rover.setPosition(new Position(0,5));
        rover.setDirection(Direction.WEST);
        assertThat(rover.moveForward()).isEqualTo("9:5:W");
    }

    @Test
    public void moveBackward_basic() {
        assertThat(rover.moveBackward()).isEqualTo("0:9:N");
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
        assertThat(rover.execute(commandWrapper))
                .contains("Rock detected at: ");

        assertThat(rover.getPosition()).isEqualTo(new Position(
                0,1)
        );
        assertThat(rover.getDirection()).isEqualTo(Direction.EAST);
    }

    @Test
    public void execute_exception() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> rover.execute(commandWrapperWithNull))
                .withMessageContaining("Unknown command: ");
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
        String execute = rover.execute(wrapper);
        System.out.println(execute);
    }

}
