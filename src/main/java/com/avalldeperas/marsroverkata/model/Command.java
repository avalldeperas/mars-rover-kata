package com.avalldeperas.marsroverkata.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.ToString;

/**
 * List with the possible commands accepted by the rover.
 */
@JsonSerialize(JsonSerialize.class)
@ToString
public enum Command {

    /** Makes the rover move backward in the direction it is facing.  */
    BACKWARD("b"),
    /** Makes the rover move forward in the direction it is facing.  */
    FORWARD("f"),
    /** Turns the rover 90º to the left.  */
    LEFT("l"),
    /** Turns the rover 90º to the right. */
    RIGHT("r"),
    /** */
    NULL_COMMAND("n");

    /** Stores the command identifiers. */
    private final String shortCommand;

    Command(final String command) {
        this.shortCommand = command;
    }

    /**
     * Gets the short command.
     * @return the short command.
     */
    public String getShortCommand() {
        return shortCommand;
    }
}
