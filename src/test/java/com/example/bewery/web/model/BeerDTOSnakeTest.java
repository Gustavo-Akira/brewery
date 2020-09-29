package com.example.bewery.web.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("snake")
@JsonTest
public class BeerDTOSnakeTest extends BaseTest{


    @Test
    void testSnake() throws JsonProcessingException {
        BeerDTO beerDTO = getBeerDto();

        String json = objectMapper.writeValueAsString(beerDTO);

        System.out.println(json);
    }
}
