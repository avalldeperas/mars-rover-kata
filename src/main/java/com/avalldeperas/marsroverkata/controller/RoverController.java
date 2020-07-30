package com.avalldeperas.marsroverkata.controller;

import com.avalldeperas.marsroverkata.model.CommandWrapper;
import com.avalldeperas.marsroverkata.model.Rover;
import com.avalldeperas.marsroverkata.model.RoverLog;
import com.avalldeperas.marsroverkata.service.RoverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@Slf4j
public class RoverController {
    /** Service that handles business logic. */
    private final RoverService service;

    /**
     * Injects the service to the web layer.
     * @param roverService The service injected to the controller.
     */
    @Autowired
    public RoverController(final RoverService roverService) {
        this.service = roverService;
    }

    /**
     * Receives a request and returns a list of Rovers.
     * @return the response containing a list of Rovers.
     */
    @GetMapping("/list-rovers")
    public ResponseEntity<List<Rover>> listRovers() {
        return ResponseEntity.ok(service.findAllRovers());
    }

    /**
     * Receives a request and returns a Rover by a given id.
     * @param id Rover's id.
     * @return response that may contain the deployed rover.
     */
    @GetMapping("/rover/{id}")
    public ResponseEntity<Rover> findRover(@PathVariable final Long id) {
        return service.findRover(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Executes commands for an specific Rover.
     * @param wrapper List of commands.
     * @param id The Rover's id.
     * @return The log of the command execution.
     */
    @PostMapping("/execute-rover/{id}")
    public String execute(@RequestBody final CommandWrapper wrapper,
                          @PathVariable final Long id) {
        StringBuilder sb;
        try {
            sb = RoverLog.buildHeader();
            sb.append(RoverLog.buildBodyLog(service.execute(wrapper, id)));
        } catch (NoSuchElementException e) {
            sb =  new StringBuilder("Error: " + e.getMessage());
        }
        return sb.toString();
    }

    /**
     * Turns the Rover on.
     * @param id Rover's id that will be turned on.
     * @return the log of the turning on.
     */
    @GetMapping("/turn-on-rover/{id}")
    public String turnOnRover(@PathVariable final Long id) {
        return service.turnOnOffRover(id, true);
    }

    /**
     * Turns the Rover off.
     * @param id Rover's id that will be turned off.
     * @return the log of the turning off.
     */
    @GetMapping("/turn-off-rover/{id}")
    public String turnOffRover(@PathVariable final Long id) {
        return service.turnOnOffRover(id, false);
    }

    /**
     * Deploys a new Rover to Mars.
     * @param rover The Rover to be deployed.
     * @return The Rover deployed.
     */
    @PostMapping("/deploy-rover")
    public Rover deployRover(@RequestBody final Rover rover) {
        return service.deployRover(rover);
    }
}
