package com.example.bewery.services.inventory;

import com.example.bewery.bootstrap.BeerLoader;
import com.example.bewery.service.inventory.BeerInventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest
public class BeerInventoryServiceRestTemplateImplTest {
    @Autowired
    BeerInventoryService beerInventoryService;

    @BeforeEach
    void setUp(){

    }

    @Test
    void getOnHandInventory(){
        Integer qoh = beerInventoryService.getOnHandInventory(BeerLoader.BEER_1_UUID);

        System.out.println(qoh);
    }
}
