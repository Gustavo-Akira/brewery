package com.example.bewery.web.model;

import com.example.bewery.web.model.enums.BeerStyleEnum;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class BaseTest {
    BeerDTO getBeerDto(){
        return BeerDTO.builder()
                .beerName("Beer Name")
                .beerStyle(BeerStyleEnum.ALE)
                .id(UUID.randomUUID())
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .price( new BigDecimal(12.75))
                .upc(12242389582305L)
                .build();
    }
}
