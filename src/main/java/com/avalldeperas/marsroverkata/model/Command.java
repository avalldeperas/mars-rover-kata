package com.avalldeperas.marsroverkata.model;

import lombok.ToString;

/**
 * List with the possible commands accepted by the rover.
 */
@ToString
public enum Command {

    /** Makes the rover move backward in the direction it is facing.  */
    BACKWARD("b"),
    /** Makes the rover move forward in the direction it is facing.  */
    FORWARD("f"),
    /** Turns the rover 90º to the left.  */
    LEFT("l"),
    /** Turns the rover 90º to the right. */
    RIGHT("r");

    /** Stores the command identifiers. */
    private final String shortCommand;

    Command(final String command) {
        this.shortCommand = command;
    }

    /**
     * Gets the command identifiers.
     * @return the command identifier.
     */
    public String getShortCommand() {
        return shortCommand;
    }


}
