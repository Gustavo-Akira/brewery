package com.example.bewery.web.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import static org.junit.jupiter.api.Assertions.*;

@JsonTest
class BeerDTOTest extends  BaseTest{
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testSerializeDto() throws JsonProcessingException {
        BeerDTO beerDTO = getBeerDto();
        String jsonString = objectMapper.writeValueAsString(beerDTO);
        System.out.println(jsonString);
    }

    @Test
    void testDeserialize() throws JsonProcessingException {
        String json = "{\"id\":\"639c00ff-9993-4ec1-af12-8277c85f93bb\",\"beerName\":\"BeerName\",\"beerStyle\":\"ALE\",\"upc\":123123123123,\"price\":12.99,\"createdDate\":\"2019-06-02T16:35:58.321001-04:00\",\"lastUpdatedDate\":\"2019-06-02T16:35:58.321872-04:00\"}";

        BeerDTO dto = objectMapper.readValue(json, BeerDTO.class);

        System.out.println(dto);

    }
}