package com.avalldeperas.marsroverkata.model;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@Slf4j
public class RoverTest {

    private Rover rover;

    @BeforeEach
    public void initialize() {
        rover = new Rover();
    }

    @Test
    public void rotateRightTest() {
        assertThat(rover.rotateRight()).isEqualTo("0:0:E");
    }

    @Test
    public void rotateLeftTest() {
        assertThat(rover.rotateLeft()).isEqualTo("0:0:W");
    }

    @Test
    public void moveForwardTest() {
        assertThat(rover.moveForward()).isEqualTo("0:1:N");
    }

    @Test
    public void moveBackwardTest() {
        assertThat(rover.moveBackward()).isEqualTo("0:9:N");
    }

    @Test
    public void rockCollisionTest() {
        rover.moveForward();
        rover.rotateRight();
        assertThatExceptionOfType(SecurityException.class)
            .isThrownBy(() -> rover.moveForward());
    }

    @Test
    public void commandJSONTest() {
        List<Command> commands = Arrays.asList(
                Command.LEFT,
                Command.LEFT,
                Command.FORWARD,
                Command.RIGHT,
                Command.BACKWARD,
                Command.BACKWARD,
                Command.LEFT,
                Command.FORWARD
        );

        CommandWrapper wrapper = new CommandWrapper();
        wrapper.setCommands(commands);

        String execute = rover.execute(wrapper);
        log.info(execute);
    }
}
