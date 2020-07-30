package com.avalldeperas.marsroverkata.service;

import com.avalldeperas.marsroverkata.data.RoverRepository;
import com.avalldeperas.marsroverkata.model.CommandWrapper;
import com.avalldeperas.marsroverkata.model.Position;
import com.avalldeperas.marsroverkata.model.Rover;
import com.avalldeperas.marsroverkata.model.RoverLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class RoverService {
    /** Repository from the data layer to access DB. */
    private final RoverRepository repository;

    /**
     * Constructor that injects the repository into the service layer.
     * @param roverRepository Repository to be injected.
     */
    @Autowired
    public RoverService(final RoverRepository roverRepository) {
        this.repository = roverRepository;
    }

    /**
     * Method that sends a list of commands to an specific Rover.
     * @param commands A list of commands.
     * @param id The Rover's id.
     * @return the log of the command list' execution.
     */
    public List<RoverLog> execute(final CommandWrapper commands,
                                  final Long id) {
        Optional<Rover> roverFound = repository.findById(id);
        if (roverFound.isEmpty()) {
            throw new NoSuchElementException("Rover not found - id " + id);
        }

        List<RoverLog> log = roverFound.get().execute(commands);
        repository.save(roverFound.get());
        return log;
    }

    /**
     * Method that calls the repository to return all rovers from DB.
     * @return A list of Rovers.
     */
    public List<Rover> findAllRovers() {
        return repository.findAll();
    }

    /**
     * Method that turns a specific Rover on or off.
     * @param id The Rover's id.
     * @param turnOn true: on; false: off.
     * @return The log of the rover turning on or off.
     */
    public String turnOnOffRover(final Long id, final boolean turnOn) {
        Optional<Rover> roverOpt = repository.findById(id);
        if (roverOpt.isEmpty()) {
            return "Rover with id " + id + " is not deployed yet on Mars.";
        }

        Rover roverFound = roverOpt.get();
        if (roverFound.isRunning() && turnOn
                || !roverFound.isRunning() && !turnOn) {
            return String.format("Rover %s is already turned %s.",
                    roverFound.getId(), (turnOn ? "on" : "off"));
        }

        roverFound.setRunning(turnOn);
        Rover savedRover = repository.save(roverFound);
        return String.format("Rover %s is now %s!", savedRover.getId(),
                (turnOn ? " running" : " stopped"));
    }

    /**
     * Calls the repository to return a Rover by its id.
     * @param id The Rover's id.
     * @return An Optional that may contain the requested Rover.
     */
    public Optional<Rover> findRover(final Long id) {
        return repository.findById(id);
    }

    /**
     * Deploys a new Rover by creating an entry in the repository
     * representing a Rover. Regularizes the position of the new Rover.
     * @param newRover The new rover to be deployed.
     * @return The rover deployed gathered from the database.
     */
    public Rover deployRover(final Rover newRover) {
        newRover.setPosition(newRover.getPosition().add(
                new Position(0, 0))
        );
        return repository.save(newRover);
    }
}
