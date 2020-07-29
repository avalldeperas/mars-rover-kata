package com.avalldeperas.marsroverkata.model;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class CommandTest {

    @Test
    public void shortCommandGetter() {
        assertThat(Command.FORWARD.getShortCommand()).isEqualTo("f");
    }
}
