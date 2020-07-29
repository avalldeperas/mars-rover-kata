package com.avalldeperas.marsroverkata.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class CommandWrapper {
    /**  List of commands for the Rover to execute.  */
    private List<Command> commands = new ArrayList<>();

    public List<Command> getCommandList() {
        return commands;
    }

    public void setCommandList(final List<Command> commandsValue) {
        this.commands = commandsValue;
    }

    public String getCommands() {
        return Arrays.toString(commands.stream()
                .map(Command::getShortCommand)
                .toArray()
        );
    }

    public void setCommands(final String stringCommands) {
        commands.clear();
        for (char ch : stringCommands.toCharArray()) {
            this.commands.add(Command.getCommandByShortCommand(ch));
        }
    }
}
