package com.avalldeperas.marsroverkata.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class CommandWrapper {
    /**  List of commands for the Rover to execute.  */
    private List<Command> commands = new ArrayList<>();

    /**
     * Gets the command list inside the wrapper.
     * @return The command list.
     */
    public List<Command> getCommandList() {
        return commands;
    }

    /**
     * Sets the command list inside the wrapper.
     * @param commandsValue The command list to be inserted.
     */
    public void setCommandList(final List<Command> commandsValue) {
        this.commands = commandsValue;
    }

    /**
     * Gets the command list as a single concatenated String of short commands.
     * @return The command list in a single string.
     */
    public String getCommands() {
        return Arrays.toString(commands.stream()
                .map(Command::getShortCommand)
                .toArray()
        );
    }

    /**
     * Sets the command list by iterating a given string, containing all
     * short commands concatenated.
     * @param stringCommands The string containing all concatenated commands.
     */
    public void setCommands(final String stringCommands) {
        commands.clear();
        for (char ch : stringCommands.toCharArray()) {
            this.commands.add(Command.getCommandByShortCommand(ch));
        }
    }
}
