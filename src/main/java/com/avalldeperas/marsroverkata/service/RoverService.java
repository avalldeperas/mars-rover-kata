package com.avalldeperas.marsroverkata.service;

import com.avalldeperas.marsroverkata.model.CommandWrapper;
import com.avalldeperas.marsroverkata.model.Rover;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class RoverService {

    private Rover rover;

    @PostConstruct
    public void init() {
        log.info(this.getClass().getSimpleName() + " - PostConstruct");
        this.rover = new Rover();
    }


    public String execute(CommandWrapper commands) {
        return rover.execute(commands);
    }
}
