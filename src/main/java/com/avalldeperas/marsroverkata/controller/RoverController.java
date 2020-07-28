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

    private RoverService roverService;

    @Autowired
    public RoverController(RoverService roverService) {
        this.roverService = roverService;
    }


    @GetMapping("/list-rovers")
    public ResponseEntity<List<Rover>> listRovers() {
        return ResponseEntity.ok(roverService.findAllRovers());
    }

    @GetMapping("/rover/{id}")
    public ResponseEntity<Rover> findRover(@PathVariable Long id) {
        return roverService.findRover(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/execute-rover/{id}")
    public String execute(@RequestBody CommandWrapper wrapper, @PathVariable Long id) {
        return roverService.execute(wrapper, id);
    }

    @GetMapping("/turn-on-rover/{id}")
    public String turnOnRover(@PathVariable Long id) {
        return roverService.turnOnOffRover(id, true);
    }

    @GetMapping("/turn-off-rover/{id}")
    public String turnOffRover(@PathVariable Long id) {
        return roverService.turnOnOffRover(id, false);
    }
}
