package com.avalldeperas.marsroverkata.controller;

import com.avalldeperas.marsroverkata.model.CommandWrapper;
import com.avalldeperas.marsroverkata.model.Rover;
import com.avalldeperas.marsroverkata.service.RoverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class RoverController {
    /** Service that handles business logic. */
    private final RoverService service;

    /**
     * Injects the service to the web layer.
     * @param roverService
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
     * @param wrapper
     * @param id
     * @return
     */
    @PostMapping("/execute-rover/{id}")
    public String execute(@RequestBody final CommandWrapper wrapper,
                          @PathVariable final Long id) {
        return service.execute(wrapper, id);
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/turn-on-rover/{id}")
    public String turnOnRover(@PathVariable final Long id) {
        return service.turnOnOffRover(id, true);
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/turn-off-rover/{id}")
    public String turnOffRover(@PathVariable final Long id) {
        return service.turnOnOffRover(id, false);
    }
}
