package com.avalldeperas.marsroverkata.model;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.ToString;

import java.util.stream.Stream;

/**
 * List with the possible commands accepted by the rover.
 */
@ToString
public enum Command {

    /** Makes the rover move backward in the direction it is facing.  */
    BACKWARD('b'),
    /** Makes the rover move forward in the direction it is facing.  */
    FORWARD('f'),
    /** Turns the rover 90º to the left.  */
    LEFT('l'),
    /** Turns the rover 90º to the right. */
    RIGHT('r'),
    /** */
    NULL_COMMAND('n'),
    /** */
    UNIDENTIFIED_COMMAND('?');

    /** Stores the command identifiers and takes into account the shortCommand
     * for Json serialization. */
    @JsonValue
    private final char shortCommand;

    Command(final char command) {
        this.shortCommand = command;
    }

    /**
     * Gets the short command.
     * @return the short command.
     */
    public char getShortCommand() {
        return shortCommand;
    }

    /**
     * asd.
     * @param shortCommandValue
     * @return asd.
     */
    public static Command getCommandByShortCommand(
            final char shortCommandValue) {
        return Stream.of(Command.values())
                .filter(command -> command.getShortCommand()
                        == Character.toLowerCase(shortCommandValue))
                .findAny()
                .orElse(Command.UNIDENTIFIED_COMMAND);
    }
}
