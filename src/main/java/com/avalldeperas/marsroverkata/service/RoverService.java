package com.avalldeperas.marsroverkata.service;

import com.avalldeperas.marsroverkata.data.RoverRepository;
import com.avalldeperas.marsroverkata.model.CommandWrapper;
import com.avalldeperas.marsroverkata.model.Rover;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RoverService {

    private Rover rover;
    private RoverRepository repository;

    @Autowired
    public RoverService(RoverRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void init() {
        log.info(this.getClass().getSimpleName() + " - PostConstruct");
        this.rover = new Rover();
    }

    public String execute(CommandWrapper commands, Long id) {
        Optional<Rover> byId = repository.findById(id);
//        if()
        return rover.execute(commands);
    }

    public List<Rover> findAllRovers() {
        return repository.findAll();
    }

    public String turnOnOffRover(Long id, boolean turnOn) {
        Optional<Rover> roverOpt = repository.findById(id);
        if(roverOpt.isEmpty())
            return "Rover with id " + id + " is not deployed yet on Mars.";

        Rover roverFound = roverOpt.get();
        if(roverFound.isRunning() && turnOn || !roverFound.isRunning() && !turnOn)
            return String.format("Rover %s is already turned %s.", roverFound.getId(), (turnOn ? "on" : "off"));

        roverFound.setRunning(turnOn);
        Rover savedRover = repository.save(roverFound);
        return String.format("Rover %s is now %s!", savedRover.getId(),(turnOn ? " running" : " stopped"));
    }

    public Optional<Rover> findRover(Long id) {
        return repository.findById(id);
    }

}
