package com.avalldeperas.marsroverkata.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CommandSerializer extends StdSerializer<Command> {

    public CommandSerializer() {
        this(null);
    }

    public CommandSerializer(final Class<Command> t) {
        super(t);
    }

    /**
     *
     * @param value
     * @param jgen
     * @param provider
     * @throws IOException
     */
    @Override
    public void serialize(final Command value
                        , final JsonGenerator jgen
                        , final SerializerProvider provider)
            throws IOException {

        jgen.writeStartObject();
        jgen.writeStringField("command", value.getShortCommand());
        jgen.writeEndObject();
    }
}
