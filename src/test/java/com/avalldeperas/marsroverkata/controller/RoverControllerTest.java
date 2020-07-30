package com.avalldeperas.marsroverkata.controller;

import com.avalldeperas.marsroverkata.model.Direction;
import com.avalldeperas.marsroverkata.model.Position;
import com.avalldeperas.marsroverkata.model.Rover;
import com.avalldeperas.marsroverkata.service.RoverService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(RoverController.class)
public class RoverControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoverService service;

    private static final String LIST_ROVERS_REQUEST = "/list-rovers";
    private static final String FIND_ROVER_REQUEST = "/rover/{id}";

    @Test
    public void listRovers_basic() throws Exception {
        when(service.findAllRovers()).thenReturn(
                Arrays.asList(
                    Rover.builder().id(1L).name("rover_test") .isRunning(true)
                            .position(new Position(0, 5))
                            .direction(Direction.SOUTH)
                            .build()
                )
        );

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(LIST_ROVERS_REQUEST)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(content().json("[{position:{x:0,y:5}" +
                    ",direction:SOUTH,name:rover_test,running:true}]"))
            .andReturn();
    }

    @Test
    public void findRover_basic() throws Exception {
        when(service.findRover(1L)).thenReturn(
                Optional.of(Rover.builder().id(1L).name("rover_found")
                        .isRunning(false)
                        .position(new Position(1, 2))
                        .direction(Direction.SOUTH)
                        .build())
        );

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                . get(FIND_ROVER_REQUEST, String.valueOf(1L))
                .param("id", String.valueOf(1L))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("{position:{x:1,y:2}" +
                        ",direction:SOUTH,name:rover_found,running:false}"))
                .andReturn();
    }

    @Test
    public void executeCommands_basic() throws Exception {

    }

}
