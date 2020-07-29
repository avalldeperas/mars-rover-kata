package com.avalldeperas.marsroverkata.model;

import com.google.gson.Gson;
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
    public void rockCollision_basic() {
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

        Gson gson = new Gson();
        String json = gson.toJson(wrapper);

        log.info(json);
    }
}
