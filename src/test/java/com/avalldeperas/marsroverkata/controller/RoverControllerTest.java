package com.avalldeperas.marsroverkata.controller;

import com.avalldeperas.marsroverkata.model.Command;
import com.avalldeperas.marsroverkata.model.CommandWrapper;
import com.avalldeperas.marsroverkata.model.Direction;
import com.avalldeperas.marsroverkata.model.Position;
import com.avalldeperas.marsroverkata.model.Rover;
import com.avalldeperas.marsroverkata.model.RoverLog;
import com.avalldeperas.marsroverkata.service.RoverService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    private static final String EXECUTE_ROVER_REQUEST = "/execute-rover/{id}";
    private static final String TURN_ON_ROVER_REQUEST = "/turn-on-rover/{id}";
    private static final String TURN_OFF_ROVER_REQUEST = "/turn-off-rover/{id}";
    private static final String DEPLOY_ROVER_REQUEST = "/deploy-rover";

    @Test
    public void listRovers_basic() throws Exception {
        when(service.findAllRovers()).thenReturn(
                Collections.singletonList(
                        Rover.builder().id(1L).name("rover_test").isRunning(true)
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
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("{position:{x:1,y:2}" +
                        ",direction:SOUTH,name:rover_found,running:false}"))
                .andReturn();
    }

    @Test
    public void executeCommands_basic() throws Exception {
        CommandWrapper wrapper = new CommandWrapper();
        wrapper.setCommands("ff");
        when(service.execute(wrapper, 1L)).thenReturn(
            Arrays.asList(
                    RoverLog.builder().position(new Position(0, 1))
                            .action(Command.FORWARD).direction(Direction.NORTH)
                            .message("Execution success.").stage(0)
                            .build(),
                    RoverLog.builder().position(new Position(0, 2))
                            .action(Command.FORWARD).direction(Direction.NORTH)
                            .message("Execution success.").stage(1)
                            .build()
            )
        );

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(EXECUTE_ROVER_REQUEST, String.valueOf(1L))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"commands\":\"ff\"}")

                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String resultAsString = result.getResponse().getContentAsString();

        assertThat(resultAsString).contains("Execution success.",
                                        "Move Forward", "(0,1)N")
                .doesNotContain("Collision", "Backward");
    }

    @Test
    public void executeCommands_RoverNotFound() throws Exception {
        CommandWrapper wrapper = new CommandWrapper();
        wrapper.setCommands("ff");

        when(service.execute(wrapper, 1L))
                .thenThrow(NoSuchElementException.class)
                .thenReturn(Collections.singletonList(new RoverLog()));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(EXECUTE_ROVER_REQUEST, String.valueOf(1L))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"commands\":\"ff\"}")

                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print())
                // error is set inside the service.
                .andExpect(content().string("Error: " + null))
                .andReturn();
    }

    @Test
    public void turnOnRover_basic() throws Exception {
        when(service.turnOnOffRover(2L, true)).thenReturn(
                String.format("Rover %s is now %s!", 2L, "running")
        );

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(TURN_ON_ROVER_REQUEST, 2L)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(
                        String.format("Rover %s is now %s!", 2L, "running")))
                .andReturn();
    }

    @Test
    public void turnOffRover_basic() throws Exception {
        when(service.turnOnOffRover(3L, false)).thenReturn(
                String.format("Rover %s is now %s!", 3L,("stopped"))
        );

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(TURN_OFF_ROVER_REQUEST, 3L)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(
                        String.format("Rover %s is now %s!", 3L,"stopped")))
                .andReturn();
    }

    @Test
    public void deployRover_basic() throws Exception {
        Rover roverToDeploy =  Rover.builder().name("rover_deployed")
                .isRunning(false)
                .position(new Position(0, 0))
                .direction(Direction.NORTH)
                .build();

        when(service.deployRover(roverToDeploy)).thenReturn(
               roverToDeploy
        );

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(DEPLOY_ROVER_REQUEST)
                .content("{\"name\":\"rover_deployed\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);


        MvcResult result = mockMvc.perform(request)
                .andDo(print())
                .andExpect(content().json("{id:null,"
                        + "position:{x:0,y:0},"
                        + "direction:NORTH,"
                        + "name:rover_deployed,"
                        + "running:false}"))
                .andReturn();
    }
}
