package com.example.bewery.web.model;

import com.example.bewery.bootstrap.BeerLoader;
import com.example.bewery.web.model.enums.BeerStyleEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class BaseTest {
    @Autowired
    ObjectMapper objectMapper;

    BeerDTO getBeerDto(){
        return BeerDTO.builder()
                .beerName("Beer Name")
                .beerStyle(BeerStyleEnum.ALE)
                .id(UUID.randomUUID())
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .price( new BigDecimal(12.75))
                .upc(BeerLoader.BEER_1_UPC)
                .build();
    }
}
