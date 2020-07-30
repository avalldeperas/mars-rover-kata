package com.avalldeperas.marsroverkata.model;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

/**
 * List with the possible commands accepted by the rover.
 */
public enum Command {

    /** Makes the rover move backward in the direction it is facing.  */
    BACKWARD('b', "Move Backward"),
    /** Makes the rover move forward in the direction it is facing.  */
    FORWARD('f', "Move Forward"),
    /** Turns the rover 90º to the left.  */
    LEFT('l', "Rotate Left"),
    /** Turns the rover 90º to the right. */
    RIGHT('r', "Rotate Right"),
    /** Used to check null commands.  */
    NULL_COMMAND('n', "Null Command"),
    /** Used to check commands not identified by the Rover. */
    UNIDENTIFIED_COMMAND('?', "Unidentified");

    /** Stores the command identifiers and takes into account the shortCommand
     * for Json serialization. */
    @JsonValue
    private final char shortCommand;

    /** String representation of a Command. */
    private final String custom;

    Command(final char command, final String customValue) {
        this.shortCommand = command;
        this.custom = customValue;
    }

    /**
     * Gets the short command.
     * @return the short command.
     */
    public char getShortCommand() {
        return shortCommand;
    }

    /**
     * Gets the Command enum by a given short command.
     * @param shortCommandValue the short command to be translated.
     * @return The Command translated.
     */
    public static Command getCommandByShortCommand(
            final char shortCommandValue) {
        return Stream.of(Command.values())
                .filter(command -> command.getShortCommand()
                        == Character.toLowerCase(shortCommandValue))
                .findAny()
                .orElse(Command.UNIDENTIFIED_COMMAND);
    }

    @Override
    public String toString() {
        return String.format("<%s> %s", shortCommand, custom);
    }
}
