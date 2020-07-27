package com.avalldeperas.marsroverkata.controller;

import com.avalldeperas.marsroverkata.model.CommandWrapper;
import com.avalldeperas.marsroverkata.service.RoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RoverController {

    private RoverService roverService;

    @Autowired
    public RoverController(RoverService roverService) {
        this.roverService = roverService;
    }

    @PostMapping("/execute-rover")
//    @ExceptionHandler(SecurityException.class)
    public String execute(@RequestBody CommandWrapper wrapper) {
        return roverService.execute(wrapper);
    }

    @GetMapping("hello-world")
    public String hello() {
        return "hello";
    }
}
