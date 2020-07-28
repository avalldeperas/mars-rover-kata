package com.avalldeperas.marsroverkata.model;

import lombok.Data;

import java.util.List;

@Data
public class CommandWrapper {

    /**  List of commands for the Rover to execute.  */
    private List<Command> commands;
}
