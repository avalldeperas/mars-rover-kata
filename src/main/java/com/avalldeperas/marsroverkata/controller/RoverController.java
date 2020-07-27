package com.avalldeperas.marsroverkata.controller;

import com.avalldeperas.marsroverkata.model.Command;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoverController {

    @GetMapping
    public void execute(@RequestParam List<Command> commands) {

    }
}
