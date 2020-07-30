package com.avalldeperas.marsroverkata.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
public class RoverLog {
    /** Current stage of the command execution. */
    private int stage;
    /** Command executed. */
    @NonNull private Command action;
    /** Current position of the Rover. */
    @NonNull private Position position;
    /** Current direction of the Rover. */
    @NonNull private Direction direction;
    /** Resulting message of the execution. */
    @NonNull private String message;
    /** Log's header. */
    private static StringBuilder header;

    /**
     * Formats the output log.
     * @return The formatted log.
     */
    @Override
    public String toString() {
        return String.format(
                "%6d %5s %18s %4s %7s %4s \t%s\n",
                stage, "|", action, "|", formatPosition(), "|", message
        );
    }

    /**
     * Formats Rover's position by adding its direction.
     * @return The formatted position.
     */
    private String formatPosition() {
        return String.format("%s%s", position, direction.getShortDirection());
    }

    /**
     * Builds the log header.
     * @return The log header as a StringBuilder.
     */
    public static StringBuilder buildHeader() {
        header = new StringBuilder();
        header.append("=======================================\n");
        header.append("=== MARS ROVER KATA - EXECUTION LOG ===\n");
        header.append("=======================================\n");
        header.append("--------------------------------------------"
                + "-----------------------------------------------\n");
        header.append(String.format(
                "%8s %3s %8s %14s %9s %2s %13s\n",
                "Stage", "|", "Action", "|", "Position", "|", "Description"
        ));
        header.append("-----------------------------------------------"
                        + "--------------------------------------------\n");
        return header;
    }

    /**
     * Builds the body log of the commands execution.
     * @param logs List of each command execution log.
     * @return The list of logs converted as a StringBuilder.
     */
    public static StringBuilder buildBodyLog(final List<RoverLog> logs) {
        StringBuilder sb = new StringBuilder();
        logs.forEach(sb::append);
        return sb;
    }
}
