package com.avalldeperas.marsroverkata.service;

import com.avalldeperas.marsroverkata.data.RoverRepository;
import com.avalldeperas.marsroverkata.model.Command;
import com.avalldeperas.marsroverkata.model.CommandWrapper;
import com.avalldeperas.marsroverkata.model.Direction;
import com.avalldeperas.marsroverkata.model.Position;
import com.avalldeperas.marsroverkata.model.Rover;
import com.avalldeperas.marsroverkata.model.RoverLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT) // fixes mockito PotentialStubbingProblem
public class RoverServiceTest {

    @InjectMocks
    private RoverService service;

    @Mock
    private RoverRepository repository;
    private Rover defaultRover;

    @BeforeEach
    public void initialize() {
        this.defaultRover = buildDefaultRover();
    }

    @Test
    public void findAll_basic() {
        when(repository.findAll()).thenReturn(
                Arrays.asList(
                        Rover.builder().id(1L).name("rover_test1").isRunning(true)
                                .position(new Position(0, 5))
                                .direction(Direction.SOUTH)
                                .build(),
                        Rover.builder().id(2L).name("rover_test2").isRunning(false)
                                .position(new Position(1, 3))
                                .direction(Direction.EAST)
                                .build()
                )
        );

        List<Rover> allRovers = service.findAllRovers();

        assertThat(allRovers).isNotNull().hasSize(2);

        // rover1
        assertThat(allRovers.get(0).getId()).isEqualTo(1L);
        assertThat(allRovers.get(0).getDirection()).isEqualTo(Direction.SOUTH);
        assertThat(allRovers.get(0).isRunning()).isTrue();
        assertThat(allRovers.get(0).getName()).isEqualTo("rover_test1");
        assertThat(allRovers.get(0).getPosition()).isEqualTo(new Position(0, 5));

        // rover2
        assertThat(allRovers.get(1).getId()).isEqualTo(2L);
        assertThat(allRovers.get(1).getDirection()).isEqualTo(Direction.EAST);
        assertThat(allRovers.get(1).getName()).contains("rover_test2");
        assertThat(allRovers.get(1).getPosition()).isEqualTo(new Position(1, 3));
        assertThat(allRovers.get(1).isRunning()).isFalse();
    }

    @Test
    public void turnOnOffRover_roverNotFoundException() {
        when(repository.findById(999L)).thenReturn(
                Optional.empty()
        );

        assertThat(service.turnOnOffRover(999L, true)).isEqualTo(
                "Rover with id " + 999L + " is not deployed yet on Mars."
        );

        assertThat(service.turnOnOffRover(999L, false)).isEqualTo(
                "Rover with id " + 999L + " is not deployed yet on Mars."
        );
    }

    @Test
    public void turnOnOffRover_basicTurnOn() {
        defaultRover.setRunning(false);
        when(repository.findById(1L)).thenReturn(
                Optional.of(defaultRover)
        );

        when(repository.save(defaultRover)).thenReturn(
                defaultRover
        );

        assertThat(service.turnOnOffRover(1L, true)).isEqualTo(
                String.format("Rover %s is now %s!", 1L,
                        (" running"))
        );
    }

    @Test
    public void turnOnOffRover_basicTurnOff() {
        defaultRover.setRunning(true);
        when(repository.findById(1L)).thenReturn(
                Optional.of(defaultRover)
        );

        when(repository.save(defaultRover)).thenReturn(
                defaultRover
        );

        assertThat(service.turnOnOffRover(1L, false)).isEqualTo(
                String.format("Rover %s is now %s!", 1L,
                        (" stopped"))
        );
    }

    @Test
    public void turnOnOffRover_alreadyTurnedOn() {
        defaultRover.setRunning(true);

        when(repository.findById(1L)).thenReturn(
                Optional.of(defaultRover)
        );

        assertThat(service.turnOnOffRover(1L, true)).isEqualTo(
                String.format("Rover %s is already turned %s.",
                        1L, ("on"))
        );
    }

    @Test
    public void turnOnOffRover_alreadyTurnedOff() {
        defaultRover.setRunning(false);

        when(repository.findById(1L)).thenReturn(
                Optional.of(defaultRover)
        );

        assertThat(service.turnOnOffRover(1L, false)).isEqualTo(
                String.format("Rover %s is already turned %s.",
                        1L, ("off"))
        );
    }

    @Test
    public void findRover_basic() {
        when(repository.findById(1L)).thenReturn(
            Optional.of(defaultRover)
        );

        Optional<Rover> foundRover = service.findRover(1L);
        assertThat(foundRover).isNotNull().isEqualTo(Optional.of(defaultRover));

        assertThat(foundRover.get().getId()).isEqualTo(1L);
        assertThat(foundRover.get().getName()).isEqualTo("rover_test");
        // ... etc...
    }

    @Test
    public void deployRover_basic() {
        when(repository.save(defaultRover)).thenReturn(
                defaultRover
        );

        Rover roverDeployed = service.deployRover(defaultRover);

        assertThat(roverDeployed).isNotNull() .isEqualTo(defaultRover);
        assertThat(roverDeployed.getPosition().getX()).isBetween(0,9);
        assertThat(roverDeployed.getPosition().getY()).isBetween(0,9);
    }

    @Test
    public void executeRover_basic() {
        when(repository.findById(1L)).thenReturn(
                Optional.of(defaultRover)
        );

        CommandWrapper wrapper = new CommandWrapper();
        wrapper.setCommands("fr");

        List<RoverLog> logs = service.execute(wrapper, 1L);

        assertThat(logs.get(0).getDirection()).isEqualTo(Direction.NORTH);
        assertThat(logs.get(0).getAction()).isEqualTo(Command.FORWARD);
        assertThat(logs.get(1).getAction()).isEqualTo(Command.RIGHT);
        assertThat(logs.get(1).getDirection()).isEqualTo(Direction.EAST);
        assertThat(logs.get(1).getPosition()).isEqualTo(new Position(0,1));
        assertThat(logs.get(0).getStage()).isEqualTo(1);
    }

    @Test
    public void executeRover_exception() {
        when(repository.findById(1L)).thenReturn(
                Optional.empty()
        );

        CommandWrapper wrapper = new CommandWrapper();
        wrapper.setCommands("fr");

        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> service.execute(wrapper, 1L))
                .withMessage("Rover not found - id " + 1L);
    }

    private Rover buildDefaultRover() {
        return Rover.builder().id(1L).name("rover_test").isRunning(false)
                .position(new Position(0, 0))
                .direction(Direction.NORTH)
                .build();
    }
}
