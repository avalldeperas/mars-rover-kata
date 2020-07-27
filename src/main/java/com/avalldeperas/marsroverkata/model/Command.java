package com.avalldeperas.marsroverkata.model;

import lombok.ToString;

@ToString
public enum Command {

    LEFT("L"), RIGHT("R"), FORWARD("F"), BACKWARD("B");

    private String shortCommand;

    Command(String command) {
        this.shortCommand = command;
    }

    public String getShortCommand() {
        return shortCommand;
    }


}
