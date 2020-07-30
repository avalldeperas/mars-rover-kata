package com.avalldeperas.marsroverkata.controller;

import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.junit.jupiter.api.Test;


/**
 * This Integration Test (IT) launches up the entire Spring Boot application,
 * present in the main class with the annotation @SpringBootApplication.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoverControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads() throws JSONException {
        String response = restTemplate.getForObject(
                "/list-rovers", String.class);

        System.out.println(response);
        JSONAssert.assertEquals(
                "[" +
                        "{id:1,position:{x:0,y:0}," +
                        "direction:NORTH,name:the_rover_1" +
                        ",running:true}," +
                        "{id:2,position" +
                        ":{x:0,y:0},direction:WEST," +
                        "name:the_rover_2,running:false}," +
                        "{id:3,position:{x:0,y:0}," +
                        "direction:EAST,name:" +
                        "the_rover_3,running:true}" +
                        "]",
                response,
                false
        );
    }
}
